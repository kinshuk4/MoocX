/*
 * @(#)file      SunJmxMBeanServer.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   1.24
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

package com.sun.jmx.mbeanserver;

import javax.management.MBeanServer;
import javax.management.MBeanServerDelegate;

import com.sun.jmx.interceptor.MBeanServerInterceptor;

/**
 * Extends the MBeanServer and MBeanServerInterceptor interface to 
 * provide methods for getting the MetaData and MBeanServerInstantiator 
 * objects associated with an MBeanServer.
 *
 * @since-jdkbundle 1.5
 * @since JMX RI 1.2
 */
public interface SunJmxMBeanServer 
    extends MBeanServerInterceptor, MBeanServer {

    /**
     * Return the MBeanInstantiator associated to this MBeanServer.
     * @exception UnsupportedOperationException if 
     *            {@link MBeanServerInterceptor}s
     *            are not enabled on this object.
     * @see #interceptorsEnabled
     */
    public MBeanInstantiator getMBeanInstantiator();


    /**
     * Return the MetaData associated to this MBeanServer.
     */
    public MetaData getMetaData();

    /**
     * Tell whether {@link MBeanServerInterceptor}s are enabled on this
     * object.
     * @return <code>true</code> if {@link MBeanServerInterceptor}s are
     *         enabled.
     * @see #getMBeanServerInterceptor
     * @see #setMBeanServerInterceptor
     * @see #getMBeanInstantiator
     * @see com.sun.jmx.mbeanserver.JmxMBeanServerBuilder
     **/
    public boolean interceptorsEnabled();

    /**
     * Return the MBeanServerInterceptor.
     * @exception UnsupportedOperationException if 
     *            {@link MBeanServerInterceptor}s
     *            are not enabled on this object.
     * @see #interceptorsEnabled
     **/
    public MBeanServerInterceptor getMBeanServerInterceptor();

    /**
     * Set the MBeanServerInterceptor.
     * @exception UnsupportedOperationException if 
     *            {@link MBeanServerInterceptor}s
     *            are not enabled on this object.
     * @see #interceptorsEnabled
     **/
    public void setMBeanServerInterceptor(MBeanServerInterceptor interceptor);

    /**
     * <p>Return the MBeanServerDelegate representing the MBeanServer.
     * Notifications can be sent from the MBean server delegate using
     * the method {@link MBeanServerDelegate#sendNotification}
     * in the returned object.</p>
     *
     * @since JMX RI 1.2
     */
    public MBeanServerDelegate getMBeanServerDelegate();

}
