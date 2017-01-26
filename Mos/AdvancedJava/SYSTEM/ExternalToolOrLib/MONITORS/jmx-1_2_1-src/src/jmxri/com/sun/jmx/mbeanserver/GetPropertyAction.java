/* 
 * @(#)file      GetPropertyAction.java 
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

import java.security.PrivilegedAction;

/**
 * Utility class to be used by the method <tt>AccessControler.doPrivileged</tt>
 * to get a system property.
 *
 * @since-jdkbundle 1.5
 */
public class GetPropertyAction implements PrivilegedAction {
    private final String key;

    public GetPropertyAction(String key) {
	this.key = key;
    }

    public Object run() {
	return System.getProperty(key);
    }
}
