/*
 * @(#)file      JMException.java
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
 * Exceptions thrown by JMX implementations.
 * It does not include the runtime exceptions.
 *
 * @since-jdkbundle 1.5
 */
public class JMException extends java.lang.Exception   { 
    
    /* Serial version */
    private static final long serialVersionUID = 350520924977331825L;

    /**
     * Default constructor.
     */
    public JMException() {
	super();
    }
    
    /**
     * Constructor that allows a specific error message to be specified.
     *
     * @param msg the detail message.
     */
    public JMException(String msg) {
	super(msg);
    }
    
}
