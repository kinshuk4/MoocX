/* 
 * @(#)file      InterceptorCycleException.java 
 * @(#)author    Sun Microsystems, Inc. 
 * @(#)version   1.8 
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

package com.sun.jmx.interceptor;

import javax.management.JMException;

/**
 * <p>Thrown when changing a chain of interceptors would produce a
 * cycle.</p>
 *
 * @since-jdkbundle 1.5
 */
public class InterceptorCycleException extends JMException {
    
    private static final long serialVersionUID = 8816909915020586894L;

    public InterceptorCycleException() {
    }

    public InterceptorCycleException(String detail) {
	super(detail);
    }
}
