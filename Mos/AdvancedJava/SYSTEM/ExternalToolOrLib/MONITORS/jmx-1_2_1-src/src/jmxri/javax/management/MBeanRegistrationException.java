/*
 * @(#)file      MBeanRegistrationException.java
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
 * Wraps exceptions thrown by the preRegister(), preDeregister() methods
 * of the <CODE>MBeanRegistration</CODE> interface.
 *
 * @since-jdkbundle 1.5
 */
public class MBeanRegistrationException extends MBeanException   { 
    
    /* Serial version */
    private static final long serialVersionUID = 4482382455277067805L;

    /**
     * Creates an <CODE>MBeanRegistrationException</CODE> that wraps
     * the actual <CODE>java.lang.Exception</CODE>.
     *
     * @param e the wrapped exception.
     */       
    public MBeanRegistrationException(java.lang.Exception e) { 
	super(e) ;
    } 

    /**
     * Creates an <CODE>MBeanRegistrationException</CODE> that wraps
     * the actual <CODE>java.lang.Exception</CODE> with a detailed
     * message.
     *
     * @param e the wrapped exception.
     * @param message the detail message.
     */
    public MBeanRegistrationException(java.lang.Exception e, String message) { 
	super(e, message) ;
    }    
}
