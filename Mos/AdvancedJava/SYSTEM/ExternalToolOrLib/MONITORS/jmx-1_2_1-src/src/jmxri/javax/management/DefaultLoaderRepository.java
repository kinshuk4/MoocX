/*
 * @(#)file      DefaultLoaderRepository.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   1.29
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

package javax.management;

import javax.management.loading.ClassLoaderRepository;
import com.sun.jmx.trace.Trace;

/**
 * <p>Keeps the list of Class Loaders registered in the MBean Server.
 * It provides the necessary methods to load classes using the registered 
 * Class Loaders.</p>
 *
 * <p>This deprecated class is maintained for compatibility.  In
 * previous versions of JMX, there was one
 * <code>DefaultLoaderRepository</code> shared by all MBean servers.
 * As of JMX 1.2, that functionality is approximated by using {@link
 * MBeanServerFactory#findMBeanServer} to find all known MBean
 * servers, and consulting the {@link ClassLoaderRepository} of each
 * one.  It is strongly recommended that code referencing
 * <code>DefaultLoaderRepository</code> be rewritten.</p>
 *
 * @deprecated Use 
 * {@link javax.management.MBeanServer#getClassLoaderRepository()}} 
 * instead.
 *
 * @since-jdkbundle 1.5
 */
public class DefaultLoaderRepository {
    /**
     * Go through the list of class loaders and try to load the requested class.
     * The method will stop as soon as the class is found. If the class
     * is not found the method will throw a <CODE>ClassNotFoundException</CODE>
     * exception.
     *
     * @param className The name of the class to be loaded.
     *
     * @return the loaded class.
     *
     * @exception ClassNotFoundException The specified class could not be found.
     */
    public static Class loadClass(String className) 
	throws ClassNotFoundException {
	return javax.management.loading.DefaultLoaderRepository.loadClass(className);
    }


    /**
     * Go through the list of class loaders but exclude the given class loader, then try to load 
     * the requested class.
     * The method will stop as soon as the class is found. If the class
     * is not found the method will throw a <CODE>ClassNotFoundException</CODE>
     * exception.
     *
     * @param className The name of the class to be loaded.
     * @param loader The class loader to be excluded.
     *
     * @return the loaded class.
     *
     * @exception ClassNotFoundException The specified class could not be found.
     */
    public static Class loadClassWithout(ClassLoader loader,String className)
	throws ClassNotFoundException {	
	return javax.management.loading.DefaultLoaderRepository.loadClassWithout(loader, className);
    }
       
 }
