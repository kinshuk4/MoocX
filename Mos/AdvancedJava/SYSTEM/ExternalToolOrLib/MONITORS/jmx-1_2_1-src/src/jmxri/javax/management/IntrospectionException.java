/*
 * @(#)file      IntrospectionException.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   4.14
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


/**
 * An exception occurred during the introspection of an MBean.
 *
 * @since-jdkbundle 1.5
 */
public class IntrospectionException extends OperationsException   { 

    /* Serial version */
    private static final long serialVersionUID = 1054516935875481725L;

    /**
     * Default constructor.
     */
    public IntrospectionException() {
	super();
    }
    
    /**
     * Constructor that allows a specific error message to be specified.
     *
     * @param message the detail message.
     */
    public IntrospectionException(String message) {
	super(message);
    }
}
