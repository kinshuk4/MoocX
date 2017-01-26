/*
 * @(#)file      ReflectionException.java
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
 *
 */

package javax.management; 



/**
 * Represents exceptions thrown in the MBean server when using the
 * java.lang.reflect classes to invoke methods on MBeans. It "wraps" the
 * actual java.lang.Exception thrown.
 *
 * @since-jdkbundle 1.5
 */
public class ReflectionException extends JMException   { 

    /* Serial version */
    private static final long serialVersionUID = 9170809325636915553L;

    /**
     * @serial The wrapped {@link Exception}
     */
    private java.lang.Exception exception ;


    /**
     * Creates a <CODE>ReflectionException</CODE> that wraps the actual <CODE>java.lang.Exception</CODE>.
     *
     * @param e the wrapped exception.
     */   
    public ReflectionException(java.lang.Exception e) { 
	super() ;
	exception = e ; 
    } 

    /**
     * Creates a <CODE>ReflectionException</CODE> that wraps the actual <CODE>java.lang.Exception</CODE> with
     * a detail message.
     *
     * @param e the wrapped exception.
     * @param message the detail message.
     */
    public ReflectionException(java.lang.Exception e, String message) { 
	super(message) ;
	exception = e ; 
    } 

    /**
     * Returns the actual {@link Exception} thrown.
     *
     * @return the wrapped {@link Exception}.
     */
    public java.lang.Exception getTargetException()  { 
	return exception ;
    } 

    /**
     * Returns the actual {@link Exception} thrown.
     *
     * @return the wrapped {@link Exception}.
     */
    public Throwable getCause() {
	return exception;
    }
}
