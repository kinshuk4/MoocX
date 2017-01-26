/* 
 * @(#)file      Introspector.java 
 * @(#)author    Sun Microsystems, Inc. 
 * @(#)version   1.3 
 * @(#)lastedit      03/07/15 
 * 
 * Copyright 2003 Sun Microsystems, Inc. All rights reserved.
 * This software is the proprietary information of Sun Microsystems, Inc.
 * Use is subject to license terms.
 *
 * Copyright 2003 Sun Microsystems, Inc. Tous droits r‰serv‰s.
 * Ce logiciel est propriet‰ de Sun Microsystems, Inc.
 * Distribu‰ par des licences qui en restreignent l'utilisation. 
 */ 

package com.sun.management.jmx;


// Java import
import java.lang.reflect.*;
import java.util.Iterator;

// RI Import
import javax.management.MBeanInfo;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanConstructorInfo;
import javax.management.NotCompliantMBeanException;
import javax.management.MBeanParameterInfo;


/**
 * This class is internal and should not be used.
 *
 * @deprecated This class is internal and should not be used.
 *
 * @since-jdkbundle 1.5
 */
public class Introspector {
    /*
     * ------------------------------------------
     *  PUBLIC METHODS
     * ------------------------------------------
     */
    
    
    /**
     * Basic method for testing if a given class is a JMX compliant MBean
     *
     * @param c The class to be tested
     *
     * @exception NotCompliantMBeanException The specified class is not a JMX compliant MBean
     *
     * @deprecated This class is internal and should not be used.
     */    
    public static synchronized MBeanInfo testCompliance(Class c) 
	throws NotCompliantMBeanException {
	return com.sun.jmx.mbeanserver.Introspector.testCompliance(c);
    }

    
    /**
     * Basic method for testing if a given class is a dynamic MBean
     *
     * @param c The class to be tested
     * 
     * @return The MBean interface implemented by the MBean or null if the MBean 
     * is a Dynamic MBean.
     *
     * @deprecated This class is internal and should not be used.
     */
    public static synchronized Class getMBeanInterface(Class c) {
	return com.sun.jmx.mbeanserver.Introspector.getMBeanInterface(c);
    }
    
}

