/*
 * @(#)file      StringMonitorMBean.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   4.19
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

// jmx imports
//
import javax.management.ObjectName;

/**
 * Exposes the remote management interface of the string monitor MBean.
 *
 * @version     4.19     07/15/03
 * @author      Sun Microsystems, Inc
 *
 * @since-jdkbundle 1.5
 */
public interface StringMonitorMBean extends MonitorMBean { 
    
    // GETTERS AND SETTERS
    //--------------------    
        
    /**
     * Gets the derived gauge.
     *
     * @return The derived gauge.
     * @deprecated As of JMX 1.2, replaced by {@link #getDerivedGauge(ObjectName)}
     */
    public String getDerivedGauge();
    
    /**
     * Gets the derived gauge timestamp.
     *
     * @return The derived gauge timestamp.
     * @deprecated As of JMX 1.2, replaced by {@link #getDerivedGaugeTimeStamp(ObjectName)}
     */
    public long getDerivedGaugeTimeStamp();  
    
    /**
     * Gets the derived gauge for the specified MBean.
     *
     * @param object the MBean for which the derived gauge is to be returned
     * @return The derived gauge for the specified MBean if this MBean is in the
     *         set of observed MBeans, or <code>null</code> otherwise.
     *
     * @since JMX 1.2
     */
    public String getDerivedGauge(ObjectName object);
    
    /**
     * Gets the derived gauge timestamp for the specified MBean.
     *
     * @param object the MBean for which the derived gauge timestamp is to be returned
     * @return The derived gauge timestamp for the specified MBean if this MBean
     *         is in the set of observed MBeans, or <code>null</code> otherwise.
     *
     * @since JMX 1.2
     */
    public long getDerivedGaugeTimeStamp(ObjectName object);
    
    /**
     * Gets the string to compare with the observed attribute.
     *
     * @return The string value.
     *
     * @see #setStringToCompare
     */
    public String getStringToCompare(); 

    /**
     * Sets the string to compare with the observed attribute.
     *
     * @param value The string value.
     * @exception java.lang.IllegalArgumentException The specified
     * string to compare is null.
     *
     * @see #getStringToCompare
     */
    public void setStringToCompare(String value) throws java.lang.IllegalArgumentException; 
    
    /**
     * Gets the matching notification's on/off switch value.
     *
     * @return <CODE>true</CODE> if the string monitor notifies when
     * matching, <CODE>false</CODE> otherwise.
     *
     * @see #setNotifyMatch
     */
    public boolean getNotifyMatch(); 

    /**
     * Sets the matching notification's on/off switch value.
     *
     * @param value The matching notification's on/off switch value.
     *
     * @see #getNotifyMatch
     */
    public void setNotifyMatch(boolean value); 

    /**
     * Gets the differing notification's on/off switch value.
     *
     * @return <CODE>true</CODE> if the string monitor notifies when
     * differing, <CODE>false</CODE> otherwise.
     *
     * @see #setNotifyDiffer
     */
    public boolean getNotifyDiffer(); 

    /**
     * Sets the differing notification's on/off switch value.
     *
     * @param value The differing notification's on/off switch value.
     *
     * @see #getNotifyDiffer
     */
    public void setNotifyDiffer(boolean value); 
}
