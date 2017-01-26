/* 
 * @(#)file      SecureClassLoaderRepository.java 
 * @(#)author    Sun Microsystems, Inc. 
 * @(#)version   1.6 
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
package com.sun.jmx.mbeanserver;

import javax.management.loading.ClassLoaderRepository;

/** 
 * Fix security hole in ClassLoaderRepository. This class wraps
 * the actual ClassLoaderRepository implementation so that
 * only the methods from {@link javax.management.loading.ClassLoaderRepository}
 * can be accessed (read-only).
 *
 * @since-jdkbundle 1.5
 */
public final class SecureClassLoaderRepository 
    implements ClassLoaderRepository {

    private final ClassLoaderRepository clr;
    /**
     * Creates a new secure ClassLoaderRepository wrapping an
     * unsecure implementation.
     * @param clr Unsecure {@link ClassLoaderRepository} implementation
     *            to wrap.
     **/
    public SecureClassLoaderRepository(ClassLoaderRepository clr) {
	this.clr=clr;
    }
    public final Class loadClass(String className) 
	throws ClassNotFoundException {
	return clr.loadClass(className);
    }
    public final Class loadClassWithout(ClassLoader loader,
				  String className) 
	throws ClassNotFoundException {
	return clr.loadClassWithout(loader,className);
    }
    public final Class loadClassBefore(ClassLoader loader,
				 String className) 
	throws ClassNotFoundException {
	return clr.loadClassBefore(loader,className);
    }
}
