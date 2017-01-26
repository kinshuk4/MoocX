/*
 * @(#)file      CounterMonitor.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   1.66
 * @(#)lastedit      03/07/15
 *
 * Copyright 2000-2003 Sun Microsystems, Inc.  All rights reserved.
 * This software is the proprietary information of Sun Microsystems, Inc.
 * Use is subject to license terms.
 *
 * Copyright 2000-2003 Sun Microsystems, Inc.  Tous droits réservés.
 * Ce logiciel est proprieté de Sun Microsystems, Inc.
 * Distribué par des licences qui en restreignent l'utilisation.
 */

package javax.management.monitor;

// java imports
//
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

// jmx imports
//
import javax.management.ObjectName;
import javax.management.MBeanNotificationInfo;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.ReflectionException;

/**
 * Defines a monitor MBean designed to observe the values of a counter attribute.
 * <P>
 * A counter monitor sends a {@link MonitorNotification#THRESHOLD_VALUE_EXCEEDED threshold notification}
 * when the value of the counter reaches or exceeds a threshold known as the comparison level.
 * The notify flag must be set to <CODE>true</CODE>.
 * <BR>In addition, an offset mechanism enables particular counting intervals to be detected.
 * <P>
 * If the offset value is not zero, whenever the threshold is triggered by the counter value
 * reaching a comparison level, that comparison level is incremented by the offset value.
 * This is regarded as taking place instantaneously, that is before the count is incremented.
 * Thus, for each level, the threshold triggers an event notification every time the count increases
 * by an interval equal to the offset value.
 * <P>
 * If the counter can wrap around its maximum value, the modulus needs to be specified.
 * The modulus is the value at which the counter is reset to zero.
 * <P>
 * If the counter difference mode is used, the value of the derived gauge is calculated
 * as the difference between the observed counter values for two successive observations.
 * If this difference is negative, the value of the derived gauge is incremented by the value of the modulus.
 * <BR>The derived gauge value (V[t]) is calculated using the following method:
w * <UL>
 * <LI>if (counter[t] - counter[t-GP]) is positive then V[t] = counter[t] - counter[t-GP]
 * <LI>if (counter[t] - counter[t-GP]) is negative then V[t] = counter[t] - counter[t-GP] + MODULUS
 * </UL>
 * This implementation of the counter monitor requires the observed attribute to be of the type integer
 * (<CODE>Byte</CODE>, <CODE>Integer</CODE>, <CODE>Short</CODE>, <CODE>Long</CODE>).
 *
 * @version     1.66     07/15/03
 * @author      Sun Microsystems, Inc
 *
 * @since-jdkbundle 1.5
 */
public class CounterMonitor extends Monitor implements CounterMonitorMBean {


    /*
     * ------------------------------------------
     *  PRIVATE VARIABLES
     * ------------------------------------------
     */

    /**
     * Counter thresholds.
     * <BR>Each element in this array corresponds to an observed object in the list.
     */
    private Number threshold[] = new Number[capacityIncrement];

    /**
     * Counter modulus.
     * <BR>The default value is a null Integer object.
     */
    private Number modulus = new Integer(0);

    /**
     * Counter offset.
     * <BR>The default value is a null Integer object.
     */
    private Number offset = new Integer(0);

    /**
     * Flag indicating if the counter monitor notifies when exceeding the threshold.
     * <BR>The default value is set to <CODE>false</CODE>.
     */
    private boolean notify = false;

    /**
     * Flag indicating if the counter difference mode is used.
     * If the counter difference mode is used, the derived gauge is the difference between two consecutive observed values.
     * Otherwise, the derived gauge is directly the value of the observed attribute.
     * <BR>The default value is set to <CODE>false</CODE>.
     */
    private boolean differenceMode = false;

    /**
     * Initial counter threshold.
     * This value is used to initialize the threshold when a new object is added
     * to the list and reset the threshold to its initial value each time the
     * counter resets. 
     */
    private transient Number initThreshold = new Integer(0);

    /**
     * Derived gauges.
     * <BR>Each element in this array corresponds to an observed object in the list.
     */
    private transient Number derivedGauge[] = new Number[capacityIncrement];

    /**
     * Derived gauge timestamp.
     * <BR>Each element in this array corresponds to an observed object in the list.
     */
    private transient long derivedGaugeTimestamp[] = new long[capacityIncrement];

    /**
     * Scan counter value captured by the previous observation.
     * <BR>Each element in this array corresponds to an observed object in the list.
     */
    private transient Number previousScanCounter[] = new Number[capacityIncrement];

    /**
     * Flag indicating if the modulus has been exceeded by the threshold.
     * This flag is used to reset the threshold once we are sure that the counter has been resetted.
     * <BR>Each element in this array corresponds to an observed object in the list.
     */
    private transient boolean modulusExceeded[] = new boolean[capacityIncrement];

    /**
     * Derived gauge captured when the modulus has been exceeded by the threshold.
     * This value is used to check if the counter has been resetted (in order to reset the threshold).
     * <BR>Each element in this array corresponds to an observed object in the list.
     */
    private transient Number derivedGaugeExceeded[] = new Number[capacityIncrement];

    /**
     * This flag is used to notify only once between two granularity periods.
     * <BR>Each element in this array corresponds to an observed object in the list.
     */
    private transient boolean eventAlreadyNotified[] = new boolean[capacityIncrement];

    /**
     * This attribute is used to keep the derived gauge type.
     * <BR>Each element in this array corresponds to an observed object in the list.
     */
    private transient int type[] = new int[capacityIncrement];

    // Flags needed to keep trace of the derived gauge type.
    // Integer types only are allowed.
    //
    private static final int INTEGER    = 0;
    private static final int BYTE       = 1;
    private static final int SHORT      = 2;
    private static final int LONG       = 3;

    // New flags defining possible counter monitor errors.
    // Flag denoting that a notification has occured after changing the threshold.
    // This flag is used to check that the threshold, offset and modulus types are the same as the counter.
    //
    private static final int THRESHOLD_ERROR_NOTIFIED   = 16;

    /**
     * Timer.
     */
    private transient Timer timer = null;


    // TRACES & DEBUG
    //---------------

    String makeDebugTag() {
        return "CounterMonitor";
    }


    /*
     * ------------------------------------------
     *  CONSTRUCTORS
     * ------------------------------------------
     */

    /**
     * Default constructor.
     */
    public CounterMonitor() {
      dbgTag = makeDebugTag();
    }

    /*
     * ------------------------------------------
     *  PUBLIC METHODS
     * ------------------------------------------
     */

    /**
     * Allows the counter monitor MBean to perform any operations it needs before being unregistered
     * by the MBean server.
     * <P>
     * Resets the threshold values.
     *
     * @exception java.lang.Exception
     */
    public void preDeregister() throws java.lang.Exception {

        // Stop the CounterMonitor.
        //
        super.preDeregister();

        if (isTraceOn()) {
            trace("preDeregister", "reset the threshold values");
        }
        
        // Reset values for serialization.
        //
        for (int i = 0; i < elementCount; i++) {
            threshold[i] = initThreshold;
        }
    }

    /**
     * Starts the counter monitor.
     */
    public void start() {
        if (isTraceOn()) {
            trace("start", "start the counter monitor");
        }

	synchronized(this) {
	    if (isActive()) {
		if (isTraceOn()) {
		    trace("start", "the counter monitor is already activated");
		}

		return;
	    }

            isActive = true;

            // Reset values.
            //
            for (int i = 0; i < elementCount; i++) {
                threshold[i] = initThreshold;
                modulusExceeded[i] = false;
                eventAlreadyNotified[i] = false;
                previousScanCounter[i] = null;
            }

            // Start the timer.
            //
            timer = new Timer();
            timer.schedule(new CounterAlarmClock(this), getGranularityPeriod(), getGranularityPeriod());
            }
        }

    /**
     * Stops the counter monitor.
     *
     * This method is not synchronized, because if it were there could
     * be a deadlock with a thread that attempted to get the lock on
     * the monitor before being interrupted or noticing that it had
     * been interrupted.
     */
    public void stop() {
        if (isTraceOn()) {
            trace("stop", "stop the counter monitor");
        }

	synchronized(this) {
            if (isTraceOn()) {
                trace("stop", "the counter monitor is not started");

		return;
            }

            isActive = false;

            // Stop the timer.
            //
            if (timer != null) {
                timer.cancel();
                timer = null;
            }
            }
        }

    // GETTERS AND SETTERS
    //--------------------
    
    /**
     * Sets the granularity period (in milliseconds).
     * <BR>The default value of the granularity period is 10 seconds.
     *
     * @param period The granularity period value.
     * @exception java.lang.IllegalArgumentException The granularity
     * period is less than or equal to zero.
     *
     * @see Monitor#setGranularityPeriod(long)
     */
    public void setGranularityPeriod(long period) throws java.lang.IllegalArgumentException { 
        synchronized(this) {
        super.setGranularityPeriod(period);
        
        // Reschedule timer task if timer is already running
        //
	    if (isActive()) {
          timer.cancel();
          timer = new Timer();
          timer.schedule(new CounterAlarmClock(this), getGranularityPeriod(), getGranularityPeriod());
        }
    }
    }

    /**
     * Gets the derived gauge of the specified object, if this object is
     * contained in the set of observed MBeans, or <code>null</code> otherwise.
     *
     * @param object the name of the object whose derived gauge is to
     * be returned.
     *
     * @return The derived gauge of the specified object.
     *
     * @since JMX 1.2
     */
    public Number getDerivedGauge(ObjectName object) { 
        synchronized(this) {
        int index = indexOf(object);
        if (index != -1) 
            return derivedGauge[index];
        else 
            return null;
    } 
    }
    
    /**
     * Gets the derived gauge timestamp of the specified object, if
     * this object is contained in the set of observed MBeans, or
     * <code>null</code> otherwise.
     *
     * @param object the name of the object whose derived gauge
     * timestamp is to be returned.
     *
     * @return The derived gauge timestamp of the specified object.
     *
     * @since JMX 1.2
     */
    public long getDerivedGaugeTimeStamp(ObjectName object) { 
        synchronized(this) {
        int index = indexOf(object);
        if (index != -1) 
            return derivedGaugeTimestamp[index];
        else 
            return 0;
    } 
    }

    /**
     * Gets the current threshold value of the specified object, if
     * this object is contained in the set of observed MBeans, or
     * <code>null</code> otherwise.
     *
     * @param object the name of the object whose threshold is to be
     * returned.
     *
     * @return The threshold value of the specified object.
     *
     * @see #setThreshold
     *
     * @since JMX 1.2
     */
    public Number getThreshold(ObjectName object) { 
        synchronized(this) {
        int index = indexOf(object);
        if (index != -1) 
            return threshold[index];
        else 
            return null;
    } 
    }

    /**
     * Gets the initial threshold value common to all observed objects.
     *
     * @return The initial threshold.
     *
     * @see #setInitThreshold
     *
     * @since JMX 1.2
     */
    public Number getInitThreshold() { 
        return initThreshold;
    } 

    /**
     * Sets the initial threshold value common to all observed objects.
     * <BR>The current threshold of every object in the set of observed MBeans is updated consequently.
     *
     * @param value The initial threshold value.
     * @exception java.lang.IllegalArgumentException The specified threshold is null or the threshold value is less than zero.
     *
     * @see #getInitThreshold
     *
     * @since JMX 1.2
     */
    public void setInitThreshold(Number value) throws java.lang.IllegalArgumentException {
        
        if (value == null) {
            throw new java.lang.IllegalArgumentException("The threshold cannot be null.");
        }
        if (value.longValue() < 0L) {
            throw new java.lang.IllegalArgumentException("The threshold must be greater than or equal to zero.");
        }

        synchronized(this) {
        initThreshold = value;
        for (int i = 0; i < elementCount; i++) {
            threshold[i] = value;
	    resetAlreadyNotified(i, THRESHOLD_ERROR_NOTIFIED);
        
            // Reset values.
            //
            modulusExceeded[i] = false;
            eventAlreadyNotified[i] = false;
        }
    } 
    }

    /**
     * Returns the derived gauge of the first object in the set of observed MBeans.
     *
     * @return The derived gauge.
     * @deprecated As of JMX 1.2, replaced by {@link #getDerivedGauge(ObjectName)}
     */
    public Number getDerivedGauge() {
        return derivedGauge[0];
    }

    /**
     * Gets the derived gauge timestamp of the first object in the set of observed MBeans.
     *
     * @return The derived gauge timestamp.
     * @deprecated As of JMX 1.2, replaced by {@link #getDerivedGaugeTimeStamp(ObjectName)}
     */
    public long getDerivedGaugeTimeStamp() {
        return derivedGaugeTimestamp[0];
    }

    /**
     * Gets the threshold value of the first object in the set of observed MBeans.
     *
     * @return The threshold value.
     *
     * @see #setThreshold(Number)
     *
     * @deprecated As of JMX 1.2, replaced by {@link #getThreshold(ObjectName)}
     */
    public Number getThreshold() {
        return threshold[0];
    }

    /**
     * Sets the initial threshold value.
     *
     * @param value The initial threshold value.
     * @exception java.lang.IllegalArgumentException The specified threshold is null or the threshold value is less than zero.
     *
     * @see #getThreshold()
     *
     * @deprecated As of JMX 1.2, replaced by {@link #setInitThreshold}
     */
    public void setThreshold(Number value) throws java.lang.IllegalArgumentException {
	synchronized(this) {
      setInitThreshold(value);
    }
    }

    /**
     * Gets the offset value common to all observed MBeans.
     *
     * @return The offset value.
     *
     * @see #setOffset
     */
    public Number getOffset() {
        return offset;
    }

    /**
     * Sets the offset value common to all observed MBeans.
     *
     * @param value The offset value.
     * @exception java.lang.IllegalArgumentException The specified
     * offset is null or the offset value is less than zero.
     *
     * @see #getOffset
     */
    public void setOffset(Number value) throws java.lang.IllegalArgumentException {

        if (value == null) {
            throw new java.lang.IllegalArgumentException("The offset cannot be null.");
        }
        if (value.longValue() < 0L) {
            throw new java.lang.IllegalArgumentException("The offset must be greater than or equal to zero.");
        }

        synchronized(this) {
        offset = value;
        for (int i = 0; i < elementCount; i++) {
	    resetAlreadyNotified(i, THRESHOLD_ERROR_NOTIFIED);
        }
    }
    }

    /**
     * Gets the modulus value common to all observed MBeans.
     *
     * @see #setModulus
     *
     * @return The modulus value.
     */
    public Number getModulus() {
        return modulus;
    }

    /**
     * Sets the modulus value common to all observed MBeans.
     *
     * @param value The modulus value.
     * @exception java.lang.IllegalArgumentException The specified
     * modulus is null or the modulus value is less than zero.
     *
     * @see #getModulus
     */
    public void setModulus(Number value) throws java.lang.IllegalArgumentException {

        if (value == null) {
            throw new java.lang.IllegalArgumentException("The modulus cannot be null.");
        }
        if (value.longValue() < 0L) {
            throw new java.lang.IllegalArgumentException("The modulus must be greater than or equal to zero.");
        }

        synchronized(this) {
        modulus = value;
        for (int i = 0; i < elementCount; i++) {
	    resetAlreadyNotified(i, THRESHOLD_ERROR_NOTIFIED);
        
            // Reset values.
            //
            modulusExceeded[i] = false;
        }
    }
    }

    /**
     * Gets the notification's on/off switch value common to all
     * observed MBeans.
     *
     * @return <CODE>true</CODE> if the counter monitor notifies when
     * exceeding the threshold, <CODE>false</CODE> otherwise.
     *
     * @see #setNotify
     */
    public boolean getNotify() {
        return notify;
    }

    /**
     * Sets the notification's on/off switch value common to all
     * observed MBeans.
     *
     * @param value The notification's on/off switch value.
     *
     * @see #getNotify
     */
    public void setNotify(boolean value) {
	synchronized(this) {
        notify = value;
    }
    }

    /**
     * Gets the difference mode flag value common to all observed MBeans.
     *
     * @return <CODE>true</CODE> if the difference mode is used,
     * <CODE>false</CODE> otherwise.
     *
     * @see #setDifferenceMode
     */
    public boolean getDifferenceMode() {
        return differenceMode;
    }

    /**
     * Sets the difference mode flag value common to all observed MBeans.
     *
     * @param value The difference mode flag value.
     *
     * @see #getDifferenceMode
     */
    public void setDifferenceMode(boolean value) {
        synchronized(this) {
        differenceMode = value;
        
        for (int i = 0; i < elementCount; i++) {
            // Reset values.
            //
            threshold[i] = initThreshold;
            modulusExceeded[i] = false;
            eventAlreadyNotified[i] = false;
            previousScanCounter[i] = null;
        }
    }
    }


    /**
     * Returns a <CODE>NotificationInfo</CODE> object containing the name of the Java class of the notification
     * and the notification types sent by the counter monitor.
     */
    public MBeanNotificationInfo[] getNotificationInfo() {
        String[] types  = { MonitorNotification.RUNTIME_ERROR,
                            MonitorNotification.OBSERVED_OBJECT_ERROR,
                            MonitorNotification.OBSERVED_ATTRIBUTE_ERROR,
                            MonitorNotification.OBSERVED_ATTRIBUTE_TYPE_ERROR,
                            MonitorNotification.THRESHOLD_ERROR,
                            MonitorNotification.THRESHOLD_VALUE_EXCEEDED};
        MBeanNotificationInfo[] notifsInfo = { new MBeanNotificationInfo(types, "javax.management.monitor.MonitorNotification",
                                                                         "Notifications sent by the CounterMonitor MBean")};
        return notifsInfo;
    }

    /*
     * ------------------------------------------
     *  PRIVATE METHODS
     * ------------------------------------------
     */

    /**
     * Updates the derived gauge and the derived gauge timestamp attributes
     * of the observed object at the specified index.
     *
     * @param scanCounter The value of the observed attribute.
     * @param index The index of the observed object.
     * @return <CODE>true</CODE> if the derived gauge value is valid, <CODE>false</CODE> otherwise.
     * The derived gauge value is invalid when the differenceMode flag is set to <CODE>true</CODE> and it is
     * the first notification (so we haven't 2 consecutive values to update the derived gauge).
     */
    private boolean updateDerivedGauge(Object scanCounter, int index) {
      
        boolean is_derived_gauge_valid;
        
        derivedGaugeTimestamp[index] = System.currentTimeMillis();
        
        synchronized(this) {
        // The counter difference mode is used.
        //
        if (differenceMode == true) {
            
            // The previous scan counter has been initialized.
            //
            if (previousScanCounter[index] != null) {
                setDerivedGaugeWithDifference((Number)scanCounter, null, index);
                
                // If derived gauge is negative it means that the counter has wrapped around and
                // the value of the threshold needs to be reset to its initial value.
                //
                if (derivedGauge[index].longValue() < 0L) {
                    if (modulus.longValue() > 0L) {
                        setDerivedGaugeWithDifference((Number)scanCounter, (Number)modulus, index);
                    }
                    threshold[index] = initThreshold;
                    eventAlreadyNotified[index] = false;
                }
                is_derived_gauge_valid = true;
            }
            // The previous scan counter has not been initialized.
            // We cannot update the derived gauge...
            //
            else {
                is_derived_gauge_valid = false;
            }
            previousScanCounter[index] = (Number)scanCounter;
        }
        // The counter difference mode is not used.
        //
        else {
            derivedGauge[index] = (Number)scanCounter;
            is_derived_gauge_valid = true;
        }
        return is_derived_gauge_valid;
    }
    }

    /**
     * Updates the notification attribute of the observed object at the
     * specified index and notifies the listeners only once if the notify flag
     * is set to <CODE>true</CODE>. 
     * @param index The index of the observed object.
     */
    private void updateNotifications(int index) {
	boolean sendNotif = false;
	String notifType = null;
	long timeStamp = 0;
	String msg = null;
	Object derGauge = null;
	Object trigger = null;
        
	synchronized(this) {
        // Send notification if notify is true.
        //
        if (eventAlreadyNotified[index] == false) {
            if (derivedGauge[index].longValue() >=  threshold[index].longValue()) {
                if (notify == true) {
			sendNotif = true;
			notifType = MonitorNotification.THRESHOLD_VALUE_EXCEEDED;
			timeStamp = derivedGaugeTimestamp[index];
			msg = "";
			derGauge = derivedGauge[index];
			trigger = threshold[index];
			//                     sendNotification(MonitorNotification.THRESHOLD_VALUE_EXCEEDED, derivedGaugeTimestamp[index], 
			//                                      "", derivedGauge[index], threshold[index], index);
                }
                eventAlreadyNotified[index] = true;
            }
        } else {
            if (isTraceOn()) {
                trace("updateNotifications", "the notification:" +
                      "\n\tNotification observed object = " + getObservedObject(index) +
                      "\n\tNotification observed attribute = " + getObservedAttribute() +
                      "\n\tNotification derived gauge = " + derivedGauge[index] +
                      "has already been sent");
            }
        }
    }

	if (sendNotif) {
	    sendNotification(notifType, timeStamp, msg, derGauge, trigger, index);
	}
    }

    /**
     * Updates the threshold attribute of the observed object at the
     * specified index.
     * @param index The index of the observed object.
     */
    private void updateThreshold(int index) {
	synchronized(this) {
        // Calculate the new threshold value if the threshold has been exceeded and
        // if the offset value is greater than zero.
        //
        if (derivedGauge[index].longValue() >=  threshold[index].longValue()) {
            
            if (offset.longValue() > 0L) {
            
                // Increment the threshold until its value is greater
                // than the one for the current derived gauge.
                //
                long threshold_value = threshold[index].longValue();
                while (derivedGauge[index].longValue() >= threshold_value) {
                    threshold_value = threshold_value + offset.longValue();
                }
            
                // Set threshold attribute.
                //
                switch(type[index]) {
                case INTEGER:
                    threshold[index] = new Integer((int)threshold_value);
                    break;
                case BYTE:
                    threshold[index] = new Byte((byte)threshold_value);
                    break;
                case SHORT:
                    threshold[index] = new Short((short)threshold_value);
                    break;
                case LONG:
                    threshold[index] = new Long((long)threshold_value);
                    break;            
                default:
                    // Should never occured...
                    if (isDebugOn()) {
                        debug("updateThreshold", "the threshold type is invalid");
                    }
                    break;
                }
            
                // If the counter can wrap around when it reaches its maximum
                // and we are not dealing with counter differences then we need
                // to reset the threshold to its initial value too.
                //
                if (differenceMode == false) {
                    if (modulus.longValue() > 0L) {
                        if (threshold[index].longValue() > modulus.longValue()) {
                            modulusExceeded[index] = true;
                            derivedGaugeExceeded[index] = derivedGauge[index];
                        }
                    }
                }	 
            
                // Threshold value has been modified so we can notify again.
                //
                eventAlreadyNotified[index] = false;
            }
            else {
                modulusExceeded[index] = true;
                derivedGaugeExceeded[index] = derivedGauge[index];
            }
        }
    }
    }
    
    /**
     * Tests if the threshold, offset and modulus of the specified index are of the same type as the counter.
     * Integer types only are allowed.
     *
     * Note:
     *  If the optionnal offset or modulus have not been initialized, 
     *  their default value is an Integer object with a value equal to zero.
     *
     * @param index The index of the observed object.
     * @return <CODE>true</CODE> if type is the same, <CODE>false</CODE> otherwise.
     */
    private boolean isThresholdTypeValid(int index) {
        
        boolean is_same_type = false;
        
        synchronized(this) {
        switch(type[index]) {
        case INTEGER:
            if ((threshold[index] instanceof Integer) && 
                ((offset.equals(new Integer(0))) || (offset instanceof Integer)) && 
                ((modulus.equals(new Integer(0))) || (modulus instanceof Integer)))
                is_same_type = true;
            break;
        case BYTE:
            if ((threshold[index] instanceof Byte) && 
                ((offset.equals(new Integer(0))) || (offset instanceof Byte)) && 
                ((modulus.equals(new Integer(0))) || (modulus instanceof Byte)))
                is_same_type = true;
            break;
        case SHORT:
            if ((threshold[index] instanceof Short) && 
                ((offset.equals(new Integer(0))) || (offset instanceof Short)) && 
                ((modulus.equals(new Integer(0))) || (modulus instanceof Short)))
                is_same_type = true;
            break;
        case LONG:
            if ((threshold[index] instanceof Long) && 
                ((offset.equals(new Integer(0))) || (offset instanceof Long)) && 
                ((modulus.equals(new Integer(0))) || (modulus instanceof Long)))
                is_same_type = true;
            break;            
        default:
            // Should never occured...
            if (isDebugOn()) {
                debug("isThresholdTypeValid", "the threshold type is invalid");
            }
            break;
        }
	}

        return is_same_type;
    }  
        
    /**
     * Sets the derived gauge of the specified index when the differenceMode flag is set to <CODE>true</CODE>.
     * Integer types only are allowed.
     *
     * @param scanCounter The value of the observed attribute.
     * @param mod The counter modulus value.
     * @param index The index of the observed object.
     */
    private void setDerivedGaugeWithDifference(Number scanCounter, Number mod, int index) { 
	synchronized(this) {
        switch (type[index]) {
        case INTEGER:
            if (mod == null)
                derivedGauge[index] = new Integer(((Integer)scanCounter).intValue() - 
                                                  ((Integer)previousScanCounter[index]).intValue());
            else
                derivedGauge[index] = new Integer(((Integer)scanCounter).intValue() - 
                                                  ((Integer)previousScanCounter[index]).intValue() +
                                                  ((Integer)modulus).intValue());
            break;
        case BYTE:
            if (mod == null)
                derivedGauge[index] = new Byte((byte)(((Byte)scanCounter).byteValue() - 
                                                      ((Byte)previousScanCounter[index]).byteValue()));
            else
                derivedGauge[index] = new Byte((byte)(((Byte)scanCounter).byteValue() - 
                                                      ((Byte)previousScanCounter[index]).byteValue() +
                                                      ((Byte)modulus).byteValue()));
            break;
        case SHORT:
            if (mod == null)
                derivedGauge[index] = new Short((short)(((Short)scanCounter).shortValue() - 
                                                        ((Short)previousScanCounter[index]).shortValue()));
            else
                derivedGauge[index] = new Short((short)(((Short)scanCounter).shortValue() - 
                                                        ((Short)previousScanCounter[index]).shortValue() +
                                                        ((Short)modulus).shortValue()));
            break;
        case LONG:
            if (mod == null)
                derivedGauge[index] = new Long(((Long)scanCounter).longValue() - 
                                               ((Long)previousScanCounter[index]).longValue());
            else
                derivedGauge[index] = new Long(((Long)scanCounter).longValue() - 
                                               ((Long)previousScanCounter[index]).longValue() +
                                               ((Long)modulus).longValue());
            break;
        default:
            // Should never occured...
            if (isDebugOn()) {
                debug("setDerivedGaugeWithDifference", "the threshold type is invalid");
            }
            break;
        }
    } 
    }

    /*
     * ------------------------------------------
     *  PACKAGE METHODS
     * ------------------------------------------
     */

    /**
     * This method is called by the counter monitor each time
     * the granularity period has been exceeded.
     * @param index The index of the observed object.
     */
    void notifyAlarmClock(int index) {
	boolean sendNotify = false;
	String notifType = null;
	long timeStamp = 0;
	String msg = null;
	Object derGauge = null;
	Object trigger = null;

        Object  scan_counter = null;
        String  notif_type = null;
        
	synchronized(this) {       
        try {
            if (isActive()) {
                // Check if the observed object and observed attribute are valid.
                //

                // Check that neither the observed object nor the observed attribute are null.
                // If the observed object or observed attribute is null, this means that
                // the monitor started before a complete initialization and nothing is done.
                //
                if ((getObservedObject(index) == null) || (getObservedAttribute() == null)) {
                    return;
                }
                
                // Check that the observed object is registered in the MBean server and
                // that the observed attribute belongs to the observed object.
                //
                try {
                    scan_counter = server.getAttribute(getObservedObject(index),
                                                       getObservedAttribute()); 
                    if (scan_counter == null)
			return;
                } catch (NullPointerException np_ex) {
                    if ((alreadyNotifieds[index] & RUNTIME_ERROR_NOTIFIED) != RESET_FLAGS_ALREADY_NOTIFIED) {
                        return;
                    } else {
                        notif_type = MonitorNotification.RUNTIME_ERROR;
			setAlreadyNotified(index, RUNTIME_ERROR_NOTIFIED);
                        throw new MonitorSettingException("The counter monitor must be registered in the MBean server.");
                    }
                } catch (InstanceNotFoundException inf_ex) {
                    if ((alreadyNotifieds[index] & OBSERVED_OBJECT_ERROR_NOTIFIED) != RESET_FLAGS_ALREADY_NOTIFIED) {
                        return;
                    } else {
                        notif_type = MonitorNotification.OBSERVED_OBJECT_ERROR;
                        setAlreadyNotified(index, OBSERVED_OBJECT_ERROR_NOTIFIED);
                        throw new MonitorSettingException("The observed object must be registered in the MBean server.");
                    }
                } catch (AttributeNotFoundException anf_ex) {
                    if ((alreadyNotifieds[index] & OBSERVED_ATTRIBUTE_ERROR_NOTIFIED) != RESET_FLAGS_ALREADY_NOTIFIED) {
                        return;
                    } else {
                        notif_type = MonitorNotification.OBSERVED_ATTRIBUTE_ERROR;
                        setAlreadyNotified(index, OBSERVED_ATTRIBUTE_ERROR_NOTIFIED);
                        throw new MonitorSettingException("The observed attribute must be accessible in the observed object.");
                    }
                } catch (MBeanException mb_ex) {
                    if ((alreadyNotifieds[index] & RUNTIME_ERROR_NOTIFIED) != RESET_FLAGS_ALREADY_NOTIFIED) {
                        return;
                    } else {
                        notif_type = MonitorNotification.RUNTIME_ERROR;
                        setAlreadyNotified(index, RUNTIME_ERROR_NOTIFIED);
                        throw new MonitorSettingException(mb_ex.getMessage());
                    }
                } catch (ReflectionException ref_ex) {
                    if ((alreadyNotifieds[index] & OBSERVED_ATTRIBUTE_ERROR_NOTIFIED) != RESET_FLAGS_ALREADY_NOTIFIED) {
                        return;
                    } else {
                        notif_type = MonitorNotification.OBSERVED_ATTRIBUTE_ERROR;
                        setAlreadyNotified(index, OBSERVED_ATTRIBUTE_ERROR_NOTIFIED);
                        throw new MonitorSettingException(ref_ex.getMessage());
                    }
                }

                // Check that the observed attribute is of type "Integer".
                //
                if (scan_counter instanceof Integer) {
                    type[index] = INTEGER;
                } else if (scan_counter instanceof Byte) {
                    type[index] = BYTE;
                } else if (scan_counter instanceof Short) {
                    type[index] = SHORT;
                } else if (scan_counter instanceof Long) {
                    type[index] = LONG;
                } else {
                    if ((alreadyNotifieds[index] & OBSERVED_ATTRIBUTE_TYPE_ERROR_NOTIFIED) != RESET_FLAGS_ALREADY_NOTIFIED) {
                        return;
                    } else {
                        notif_type = MonitorNotification.OBSERVED_ATTRIBUTE_TYPE_ERROR;
                        setAlreadyNotified(index, OBSERVED_ATTRIBUTE_TYPE_ERROR_NOTIFIED);
                        throw new MonitorSettingException("The observed attribute type must be an integer type.");
                    }
                }

                // Check that threshold, offset and modulus are of the same type as the counter.
                //
                if (!isThresholdTypeValid(index)) {
                    if ((alreadyNotifieds[index] & THRESHOLD_ERROR_NOTIFIED) != RESET_FLAGS_ALREADY_NOTIFIED) {
                        return;
                    } else {
                        notif_type = MonitorNotification.THRESHOLD_ERROR;
                        setAlreadyNotified(index, THRESHOLD_ERROR_NOTIFIED);
                        throw new MonitorSettingException("The threshold, offset and modulus must be of the same type as the counter.");
                    }
                }

                // Clear all already notified flags.
                //
		resetAllAlreadyNotified(index);
                                
                // Check if counter has wrapped around.
                //
                if (modulusExceeded[index]) {
                    if (derivedGauge[index].longValue() < derivedGaugeExceeded[index].longValue()) {
                        threshold[index] = initThreshold;
                        modulusExceeded[index] = false;
                        eventAlreadyNotified[index] = false;
                    }
                }
                
                // Update the derived gauge attributes and check the validity of the new value.
                // The derived gauge value is invalid when the differenceMode flag is set to true and it is
                // the first notification (so we haven't 2 consecutive values to update the derived gauge).
                //
                boolean is_derived_gauge_valid = updateDerivedGauge(scan_counter, index);
                
                // Notify the listeners and update the threshold if the updated derived gauge value is valid.
                // 
                if (is_derived_gauge_valid) {
                    updateNotifications(index);
                    updateThreshold(index);
                }
            }
	    } catch (MonitorSettingException ms_ex) {
            
            // Create and send the monitor error notification.
            //
		sendNotify = true;
		notifType = notif_type;
		timeStamp = derivedGaugeTimestamp[index];
		msg = ms_ex.getMessage();
		derGauge = derivedGauge[index];
		trigger = null;
            
// 		sendNotification(notif_type, derivedGaugeTimestamp[index],  ms_ex.getMessage(), 
// 				 derivedGauge[index], null, index);
		
            // Reset values.
            //
            modulusExceeded[index] = false;
            eventAlreadyNotified[index] = false;
            previousScanCounter[index] = null;
        } 
    }
                    
	if (sendNotify) {
	    sendNotification(notifType, timeStamp, msg, derGauge, trigger, index);
	}
    }
                    
    /**
     * This method is called when adding a new observed object in the vector.
     * It updates all the counter specific arrays.
     * @param index The index of the observed object.
     */
    void insertSpecificElementAt(int index) {
	synchronized(this) {
        // Update threshold, derivedGauge, derivedGaugeTimestamp,  previousScanCounter, 
        // modulusExceeded, derivedGaugeExceeded, eventAlreadyNotified and type arrays.
        //
        Number nb = new Integer(0);
        insertNumberElementAt(threshold, nb, index);
        insertNumberElementAt(derivedGauge, nb, index);
        insertNumberElementAt(previousScanCounter, null, index);
        insertNumberElementAt(derivedGaugeExceeded, null, index);
        insertlongElementAt(derivedGaugeTimestamp, (new Date()).getTime(), index);
        insertbooleanElementAt(modulusExceeded, false, index);
        insertbooleanElementAt(eventAlreadyNotified, false, index);
        insertintElementAt(type, INTEGER, index);
    }
    }
    
    /**
     * This method is called when removing an observed object from the vector.
     * It updates all the counter specific arrays.
     * @param index The index of the observed object.
     */
    void removeSpecificElementAt(int index) {
	synchronized(this) {
        // Update threshold, derivedGauge, derivedGaugeTimestamp,  previousScanCounter, 
        // modulusExceeded, derivedGaugeExceeded, eventAlreadyNotified and type arrays.
        //
        removeNumberElementAt(threshold, index);
        removeNumberElementAt(derivedGauge, index);
        removeNumberElementAt(previousScanCounter, index);
        removeNumberElementAt(derivedGaugeExceeded, index);
        removelongElementAt(derivedGaugeTimestamp, index);
        removebooleanElementAt(modulusExceeded, index);
        removebooleanElementAt(eventAlreadyNotified, index);
        removeintElementAt(type, index);
    }
    }


  /**
   * CounterAlarmClock inner class:
   * This class provides a simple implementation of an alarm clock MBean.
   * The aim of this MBean is to set up an alarm which wakes up the counter monitor every granularity period.
  */

  private static class CounterAlarmClock extends TimerTask {
    
    CounterMonitor listener = null;
    
    /*
     * ------------------------------------------
     *  CONSTRUCTORS
     * ------------------------------------------
     */

    public CounterAlarmClock(CounterMonitor listener) {
        this.listener = listener;
    }

    /*
     * ------------------------------------------
     *  PUBLIC METHODS
     * ------------------------------------------
     */

    /**
     * This method is called by the CounterAlarmClock thread when it is started.
     */
    public void run() {
      if (listener.isActive()) {
        for (int i = 0; i < listener.elementCount; i++) {
          listener.notifyAlarmClock(i);
        }
      }
    }
  }
}
