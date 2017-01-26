/* 
 * @(#)file      InterceptorNotPresentException.java 
 * @(#)author    Sun Microsystems, Inc. 
 * @(#)version   1.9 
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
 * <p>An {@link MBeanServerInterceptor} was not found when expected.</p>
 *
 * @see ForwardingMBeanServerInterceptor#remove
 *
 * @since-jdkbundle 1.5
 */
public class InterceptorNotPresentException extends JMException {

    private static final long serialVersionUID = 1501906676880656752L;

    public InterceptorNotPresentException() {
    }

    public InterceptorNotPresentException(String detail) {
	super(detail);
    }
}
