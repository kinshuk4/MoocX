/* 
 * @(#)file      TraceTags.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   1.12
 * @(#)lastedit      03/07/15
 * 
 * Copyright 2003 Sun Microsystems, Inc. All rights reserved.
 * This software is the proprietary information of Sun Microsystems, Inc.
 * Use is subject to license terms.
 *
 * Copyright 2003 Sun Microsystems, Inc. Tous droits réservés.
 * Ce logiciel est proprieté de Sun Microsystems, Inc.
 * Distribué par des licences qui en restreignent l'utilisation. 
 */ 

package com.sun.jmx.trace;

/**
 * Defines common trace constants.
 *
 * @since-jdkbundle 1.5
 * @since JMX RI 1.2
 */
public interface TraceTags {

    /**
     * Log level used to notify of error conditions.
     */
    public static final int LEVEL_ERROR = 0;

    /**
     * Information level defined for trace level.
     * The information will be provided to help development of JMX applications.
     */
    public static final int LEVEL_TRACE = 1;

    /**
     * Information level defined for debug level.
     * The information will be provided to help diagnosis. Selecting this level
     * will result in selecting the LEVEL_TRACE too.
     */
    public static final int LEVEL_DEBUG = 2;

    /**
     * Information type defined for MBean Server information.
     */
    public static final int INFO_MBEANSERVER = 1;

    /**
     * Information type defined for MLet service information.
     */
    public static final int INFO_MLET = 2;

    /**
     * Information type defined for Monitor information.
     */
    public static final int INFO_MONITOR = 4;

    /**
     * Information type defined for Timer information.
     */
    public static final int INFO_TIMER = 8;

    /**
     * Information type defined for all other classes.
     */  
    public static final int INFO_MISC = 16;

    /**
     * Information type defined for Event Management information.
     */
    public static final int INFO_NOTIFICATION = 32;

    /**
     * Information type defined for Relation Service.
     */
    public static final int INFO_RELATION = 64;

    /**
     * Information type defined for for Model MBean.
     */
    public static final int INFO_MODELMBEAN = 128;
}
