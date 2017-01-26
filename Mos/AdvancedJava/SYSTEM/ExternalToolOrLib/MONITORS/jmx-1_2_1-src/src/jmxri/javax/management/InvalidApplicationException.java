/*
 * @(#)file      InvalidApplicationException.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   4.16
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
 * Thrown when an attempt is made to apply either of the following: A
 * subquery expression to an MBean or a qualified attribute expression
 * to an MBean of the wrong class.  This exception is used internally
 * by JMX during the evaluation of a query.  User code does not
 * usually see it.
 *
 * @since-jdkbundle 1.5
 */
public class InvalidApplicationException extends Exception   { 
   

    /* Serial version */
    private static final long serialVersionUID = -3048022274675537269L;
  
    /**
     * @serial The object representing the class of the MBean
     */
    private Object val;


    /**
     * Constructs an <CODE>InvalidApplicationException</CODE> with the specified <CODE>Object</CODE>.
     *
     * @param val the detail message of this exception.
     */
    public InvalidApplicationException(Object val) { 
	this.val = val;
    }
}
