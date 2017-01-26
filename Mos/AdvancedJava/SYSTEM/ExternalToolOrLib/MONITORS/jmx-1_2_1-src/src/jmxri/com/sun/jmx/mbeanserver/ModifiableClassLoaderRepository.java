/* 
 * @(#)file      ModifiableClassLoaderRepository.java 
 * @(#)author    Sun Microsystems, Inc. 
 * @(#)version   1.11 
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


// JMX import
import javax.management.ObjectName;
import javax.management.loading.ClassLoaderRepository;

/**
 * This interface keeps the list of Class Loaders registered in the 
 * MBean Server.
 * It provides the necessary methods to load classes using the 
 * registered Class Loaders, and to add/remove class loaders from the
 * list.
 *
 * @since-jdkbundle 1.5
 * @since JMX RI 1.2
 */
public interface ModifiableClassLoaderRepository 
    extends ClassLoaderRepository {

    /**
     * Add an anonymous ClassLoader to the repository.
     **/
    public void addClassLoader(ClassLoader loader);

    /**
     * Remove the specified ClassLoader to the repository.
     * The class loader may or may not be anonymous.
     **/
    public void removeClassLoader(ClassLoader loader);

    /**
     * Add a named ClassLoader to the repository.
     **/
    public void addClassLoader(ObjectName name, ClassLoader loader);

    /**
     * Remove a named ClassLoader from the repository.
     **/
    public void removeClassLoader(ObjectName name);

    /**
     * Get a named ClassLoader from the repository.
     **/
    public ClassLoader getClassLoader(ObjectName name);
}
