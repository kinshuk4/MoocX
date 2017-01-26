/*
 * @(#)file      MBeanException.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   4.18
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
 * Represents "user defined" exceptions thrown by MBean methods
 * in the agent. It "wraps" the actual "user defined" exception thrown.
 * This exception will be built by the MBeanServer when a call to an
 * MBean method results in an unknown exception.
 *
 * @since-jdkbundle 1.5
 */
public class MBeanException extends JMException   { 
    
    
    /* Serial version */
    private static final long serialVersionUID = 4066342430588744142L;

    /**
     * @serial Encapsulated {@link Exception}
     */
    private java.lang.Exception exception ;
   

    /**
     * Creates an <CODE>MBeanException</CODE> that wraps the actual <CODE>java.lang.Exception</CODE>.
     *
     * @param e the wrapped exception.
     */
    public MBeanException(java.lang.Exception e) { 
	super() ;
	exception = e ;
    } 

    /**
     * Creates an <CODE>MBeanException</CODE> that wraps the actual <CODE>java.lang.Exception</CODE> with
     * a detail message.
     *
     * @param e the wrapped exception.
     * @param message the detail message.
     */
    public MBeanException(java.lang.Exception e, String message) { 
	super(message) ;
	exception = e ;
    } 
    

    /**
     * Return the actual {@link Exception} thrown.
     *
     * @return the wrapped exception.
     */
    public Exception getTargetException()  { 
	return exception;
    } 

    /**
     * Return the actual {@link Exception} thrown.
     *
     * @return the wrapped exception.
     */
    public Throwable getCause() {
	return exception;
    }
}
