/*
 * @(#)file      StringMonitor.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   4.35
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
 * Defines a monitor MBean designed to observe the values of a string 
 * attribute.
 * <P>
 * A string monitor sends notifications as follows:
 * <UL>
 * <LI> if the attribute value matches the string to compare value,
 *      a {@link MonitorNotification#STRING_TO_COMPARE_VALUE_MATCHED match notification} is sent.
 *      The notify match flag must be set to <CODE>true</CODE>.
 *      <BR>Subsequent matchings of the string to compare values do not 
 *      cause further notifications unless
 *      the attribute value differs from the string to compare value.
 * <LI> if the attribute value differs from the string to compare value,
 *      a {@link MonitorNotification#STRING_TO_COMPARE_VALUE_DIFFERED differ notification} is sent.
 *      The notify differ flag must be set to <CODE>true</CODE>.
 *      <BR>Subsequent differences from the string to compare value do 
 *      not cause further notifications unless
 *      the attribute value matches the string to compare value.
 * </UL>
 *
 * @version     4.35     07/15/03
 * @author      Sun Microsystems, Inc
 *
 * @since-jdkbundle 1.5
 */
public class StringMonitor extends Monitor implements StringMonitorMBean {


    // TRACES & DEBUG
    //---------------

    String makeDebugTag() {
        return "StringMonitor";
    }

    /*
     * ------------------------------------------
     *  PRIVATE VARIABLES
     * ------------------------------------------
     */

    private final static String[] types  = { 
	    MonitorNotification.RUNTIME_ERROR,
	    MonitorNotification.OBSERVED_OBJECT_ERROR,
	    MonitorNotification.OBSERVED_ATTRIBUTE_ERROR,
	    MonitorNotification.OBSERVED_ATTRIBUTE_TYPE_ERROR,
	    MonitorNotification.STRING_TO_COMPARE_VALUE_MATCHED,
	    MonitorNotification.STRING_TO_COMPARE_VALUE_DIFFERED};
    private final static MBeanNotificationInfo[] notifsInfo = { 
	    new MBeanNotificationInfo(types, 
		"javax.management.monitor.MonitorNotification",
		"Notifications sent by the StringMonitor MBean")};

    /**
     * String to compare with the observed attribute.
     * <BR>The default value is an empty character sequence.
     */
    private String stringToCompare = "";

    /**
     * Flag indicating if the string monitor notifies when matching 
     * the string to compare.
     * <BR>The default value is set to <CODE>false</CODE>.
     */
    private boolean notifyMatch = false;

    /**
     * Flag indicating if the string monitor notifies when differing 
     * from the string to compare.
     * <BR>The default value is set to <CODE>false</CODE>.
     */
    private boolean notifyDiffer = false;

    /**
     * Derived gauges.
     * <BR>Each element in this array corresponds to an observed object in the list.
     */
    private transient String derivedGauge[] = new String[capacityIncrement];

    /**
     * Derived gauge timestamps.
     * <BR>Each element in this array corresponds to an observed object in the list.
     */
    private transient long derivedGaugeTimestamp[] = new long[capacityIncrement];

    /**
     * This attribute is used to handle the matching/differing mechanism.
     * <BR>Each element in this array corresponds to an observed object in the list.
     */
    private transient int status[] = new int[capacityIncrement];

    // Flags needed to implement the matching/differing mechanism.
    //
    private static final int MATCHING                   = 0;
    private static final int DIFFERING                  = 1;
    private static final int MATCHING_OR_DIFFERING      = 2;

    /**
     * Timer.
     */
    private transient Timer timer = null;


    /*
     * ------------------------------------------
     *  CONSTRUCTORS
     * ------------------------------------------
     */

    /**
     * Default constructor.
     */
    public StringMonitor() {
      dbgTag = makeDebugTag();
    }

    /*
     * ------------------------------------------
     *  PUBLIC METHODS
     * ------------------------------------------
     */

    /**
     * Starts the string monitor.
     */
    public void start() {

        if (isTraceOn()) {
            trace("start", "start the string monitor");
        }

        synchronized(this) {
	    if (isActive) {
		if (isTraceOn()) {
		    trace("start", "the string monitor is already activated");
		}

		return;
	    }

            isActive = true;

            // Reset values.
            //
            for (int i = 0; i < elementCount; i++) {
                status[i] = MATCHING_OR_DIFFERING;
            }

            // Start the AlarmClock.
            //
            timer = new Timer();
            timer.schedule(new StringAlarmClock(this), getGranularityPeriod(), getGranularityPeriod());
            }
        }

    /**
     * Stops the string monitor.
     */
    /* This method is not synchronized, because if it were there could
       be a deadlock with a thread that attempted to get the lock on
       the monitor before being interrupted or noticing that it had
       been interrupted.  */
    public void stop() {

        if (isTraceOn()) {
            trace("stop", "stop the string monitor");
        }

        synchronized(this) {
	    if (!isActive) {
		if (isTraceOn()) {
		    trace("stop", "the string monitor is already deactivated");
		}

		return;
	    }

            isActive = false;

            // Stop the AlarmClock.
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
	    if (isActive)
        {
          timer.cancel();
          timer = new Timer();
          timer.schedule(new StringAlarmClock(this), getGranularityPeriod(), getGranularityPeriod());
        }
    }
    }

    /**
     * Gets the derived gauge of the specified object, if this object is
     * contained in the set of observed MBeans, or <code>null</code> otherwise.
     *
     * @param object the name of the MBean whose derived gauge is required.
     *
     * @return The derived gauge of the specified object.
     *
     * @since JMX 1.2
     */
    public String getDerivedGauge(ObjectName object) { 
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
     * @param object the name of the MBean whose derived gauge
     * timestamp is required.
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
     * Returns the derived gauge of the first object in the set of observed MBeans.
     *
     * @return The derived gauge.
     * @deprecated As of JMX 1.2, replaced by {@link #getDerivedGauge(ObjectName)}
     */
    public String getDerivedGauge() {
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
     * Gets the string to compare with the observed attribute common to all observed MBeans.
     *
     * @return The string value.
     *
     * @see #setStringToCompare
     */
    public String getStringToCompare() {
        return stringToCompare;
    }

    /**
     * Sets the string to compare with the observed attribute common to all observed MBeans.
     *
     * @param value The string value.
     * @exception java.lang.IllegalArgumentException The specified 
     *        string to compare is null.
     *
     * @see #getStringToCompare
     */
    public void setStringToCompare(String value) 
	throws java.lang.IllegalArgumentException {

        if (value == null) {
	    throw new java.lang.IllegalArgumentException("The string to compare cannot be null.");
        }

        synchronized(this) {
        stringToCompare = value;

        // Reset values.
        //
        for (int i = 0; i < elementCount; i++) {
          status[i] = MATCHING_OR_DIFFERING;
        }
    }
    }

    /**
     * Gets the matching notification's on/off switch value common to all observed MBeans.
     *
     * @return <CODE>true</CODE> if the string monitor notifies when 
     *    matching the string to compare, <CODE>false</CODE> otherwise.
     *
     * @see #setNotifyMatch
     */
    public boolean getNotifyMatch() {
        return notifyMatch;
    }

    /**
     * Sets the matching notification's on/off switch value common to all observed MBeans.
     *
     * @param value The matching notification's on/off switch value.
     *
     * @see #getNotifyMatch
     */
    public void setNotifyMatch(boolean value) {
	synchronized(this) {
        notifyMatch = value;
    }
    }

    /**
     * Gets the differing notification's on/off switch value common to all observed MBeans.
     *
     * @return <CODE>true</CODE> if the string monitor notifies when 
     *    differing from the string to compare, <CODE>false</CODE> otherwise.
     *
     * @see #setNotifyDiffer
     */
    public boolean getNotifyDiffer() {
        return notifyDiffer;
    }

    /**
     * Sets the differing notification's on/off switch value common to all observed MBeans.
     *
     * @param value The differing notification's on/off switch value.
     *
     * @see #getNotifyDiffer
     */
    public void setNotifyDiffer(boolean value) {
	synchronized(this) {
        notifyDiffer = value;
    }
    }

    /**
     * Returns a <CODE>NotificationInfo</CODE> object containing the 
     * name of the Java class of the notification
     * and the notification types sent by the string monitor.
     */
    public MBeanNotificationInfo[] getNotificationInfo() {
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
     * @param scanString The value of the observed attribute.
     * @param index The index of the observed object.
     */
    private void updateDerivedGauge(Object scanString, int index) {
        synchronized(this) {
        derivedGaugeTimestamp[index] = (new Date()).getTime();
        derivedGauge[index] = (String)scanString;
    }
    }

    /**
     * Updates the notification attributes of the observed object at the
     * specified index and notifies the listeners only once if the
     * notifyMatch/notifyDiffer flag is set to <CODE>true</CODE>.
     * @param index The index of the observed object.
     */
    private void updateNotifications(int index) {
	boolean sendNotify = false;
	String type = null;
	long timeStamp = 0;
	String msg = null;
	Object derGauge = null;
	Object trigger = null;
                
	synchronized(this) {
        // Send matching notification if notifyMatch is true.
        // Send differing notification if notifyDiffer is true.
        //
        if (status[index] == MATCHING_OR_DIFFERING) {
            if (derivedGauge[index].equals(stringToCompare)) {	      
                if (notifyMatch == true) {
			// 		    sendNotification(MonitorNotification.STRING_TO_COMPARE_VALUE_MATCHED, derivedGaugeTimestamp[index], 
			// 				     "", derivedGauge[index], stringToCompare, index);
			sendNotify = true;
			type = MonitorNotification.STRING_TO_COMPARE_VALUE_MATCHED;
			timeStamp = derivedGaugeTimestamp[index];
			msg = "";
			derGauge = derivedGauge[index];
			trigger = stringToCompare;
                }
		    
                status[index] = DIFFERING;
		} else {	      
                if (notifyDiffer == true) {
			sendNotify = true;
			// 		    sendNotification(MonitorNotification.STRING_TO_COMPARE_VALUE_DIFFERED, derivedGaugeTimestamp[index], 
			// 				     "", derivedGauge[index], stringToCompare, index);
			type = MonitorNotification.STRING_TO_COMPARE_VALUE_DIFFERED;                    timeStamp = derivedGaugeTimestamp[index];
			msg = "";
			derGauge = derivedGauge[index];
			trigger = stringToCompare;
                }
                status[index] = MATCHING;
            }
	    } else {
            if (status[index] == MATCHING) {
                if (derivedGauge[index].equals(stringToCompare)) {	      
                    if (notifyMatch == true) {
			    sendNotify = true;
			    type = MonitorNotification.STRING_TO_COMPARE_VALUE_MATCHED;
			    timeStamp = derivedGaugeTimestamp[index];
			    msg = "";
			    derGauge = derivedGauge[index];
			    trigger = stringToCompare;
			    
			    //    sendNotification(MonitorNotification.STRING_TO_COMPARE_VALUE_MATCHED, derivedGaugeTimestamp[index], 
			    //                                          "", derivedGauge[index], stringToCompare, index);
                    }
                    status[index] = DIFFERING;
                }
		} else if (status[index] == DIFFERING) {
                if (!derivedGauge[index].equals(stringToCompare)) {	      
                    if (notifyDiffer == true) {
			    sendNotify = true;
			    type = MonitorNotification.STRING_TO_COMPARE_VALUE_DIFFERED;
			    timeStamp = derivedGaugeTimestamp[index];
			    msg = "";
			    derGauge = derivedGauge[index];
			    trigger = stringToCompare;
                    }
                    status[index] = MATCHING;
                }
            }
        }
    }

	if (sendNotify) {
	    sendNotification(type, timeStamp, msg, derGauge, trigger, index);
	}
    }

    /*
     * ------------------------------------------
     *  PACKAGE METHODS
     * ------------------------------------------
     */

    /**
     * This method is called by the string monitor each time
     * the granularity period has been exceeded.
     * @param index The index of the observed object.
     */
    void notifyAlarmClock(int index) {
	boolean sendNotify = false;
	String type = null;
	long timeStamp = 0;
	String msg = null;
	Object derGauge = null;
	Object trigger = null;

        Object  scan_string = null;
        String  notif_type = null;

	synchronized(this) {
        try {
		if (isActive) {
                
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
                    scan_string = server.getAttribute(getObservedObject(index),
                                                      getObservedAttribute());
                    if (scan_string == null)
                      return;
                } catch (NullPointerException np_ex) {
                    if ((alreadyNotifieds[index] & RUNTIME_ERROR_NOTIFIED) != RESET_FLAGS_ALREADY_NOTIFIED) {
                        return;
                    } else {
                        notif_type = MonitorNotification.RUNTIME_ERROR;
                        setAlreadyNotified(index, RUNTIME_ERROR_NOTIFIED);
                        throw new MonitorSettingException("The string monitor must be registered in the MBean server.");
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

                // Check that the observed attribute is of type "String".
                //
                if (!(scan_string instanceof String)) {
                    if ((alreadyNotifieds[index] & OBSERVED_ATTRIBUTE_TYPE_ERROR_NOTIFIED) != RESET_FLAGS_ALREADY_NOTIFIED) {
                        return;
                    } else {
                        notif_type = MonitorNotification.OBSERVED_ATTRIBUTE_TYPE_ERROR;
                        setAlreadyNotified(index, OBSERVED_ATTRIBUTE_TYPE_ERROR_NOTIFIED);
                        throw new MonitorSettingException("The observed attribute type must be a string type.");
                    }
                }

                // Clear all already notified flags.
                //
		resetAllAlreadyNotified(index);

                // Update the derived gauge attributes.
                //
                updateDerivedGauge(scan_string, index);
                
                // Notify the listeners.
                //
                updateNotifications(index);
            }
	    } catch (MonitorSettingException ms_ex) {
            
            // Create and send the monitor error notification.
            //
		sendNotify = true;
		type = notif_type;
		timeStamp = derivedGaugeTimestamp[index];
		msg = ms_ex.getMessage();
                derGauge = derivedGauge[index];
		trigger = null;
            
 //            sendNotification(notif_type, derivedGaugeTimestamp[index], ms_ex.getMessage(), 
//                              derivedGauge[index], null, index);
            
            // Reset values.
            //
            status[index] = MATCHING_OR_DIFFERING;
        } 
    }

	if (sendNotify) {
	    sendNotification(type, timeStamp, msg, derGauge, trigger, index);
	}
    }

    /**
     * This method is called when adding a new observed object in the vector.
     * It updates all the string specific arrays.
     * @param index The index of the observed object.
     */
    void insertSpecificElementAt(int index) {
	synchronized(this) {
        // Update derivedGauge, derivedGaugeTimestamp, and status arrays.
        //
        insertStringElementAt(derivedGauge, "", index);
        insertlongElementAt(derivedGaugeTimestamp, (new Date()).getTime(), index);
        insertintElementAt(status, MATCHING_OR_DIFFERING, index);
    }
    }
    
    /**
     * This method is called when removing an observed object from the vector.
     * It updates all the string specific arrays.
     * @param index The index of the observed object.
     */
    void removeSpecificElementAt(int index) {
	synchronized(this) {
        // Update derivedGauge, derivedGaugeTimestamp, and status arrays.
        //
        removeStringElementAt(derivedGauge, index);
        removelongElementAt(derivedGaugeTimestamp, index);
        removeintElementAt(status, index);
    }
    }

  /**
   * StringAlarmClock inner class:
   * This class provides a simple implementation of an alarm clock MBean.
   * The aim of this MBean is to set up an alarm which wakes up the string monitor every granularity period.
   */
  
  private static class StringAlarmClock extends TimerTask {

    StringMonitor listener = null;
    
    /*
     * ------------------------------------------
     *  CONSTRUCTORS
     * ------------------------------------------
     */
    
    public StringAlarmClock(StringMonitor listener) {
      this.listener = listener;
    }
    
    /*
     * ------------------------------------------
     *  PUBLIC METHODS
     * ------------------------------------------
     */
    
    /**
     * This method is called by the StringAlarmClock thread when it is started.
     */
    public void run() {
      if (listener.isActive) {
        for (int i = 0; i < listener.elementCount; i++) {
          listener.notifyAlarmClock(i);
        }
      }
    }
  }
}
