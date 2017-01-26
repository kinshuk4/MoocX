/*
 * @(#)file      BadStringOperationException.java
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
 * Thrown when an invalid string operation is passed
 * to a method for constructing a query.
 *
 * @since-jdkbundle 1.5
 */
public class BadStringOperationException extends Exception   { 


    /* Serial version */
    private static final long serialVersionUID = 7802201238441662100L;

    /**
     * @serial The description of the operation that originated this exception
     */
    private String op;

    /**
     * Constructs a <CODE>BadStringOperationException</CODE> with the specified detail
     * message.
     *
     * @param message the detail message.
     */
    public BadStringOperationException(String message) { 
	this.op = message;
    } 
 
   
    /**
     * Returns the string representing the object.
     */
    public String toString()  { 
	return "BadStringOperationException: " + op;
    } 
      
 }
