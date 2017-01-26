/*
 * @(#)file      BadAttributeValueExpException.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   4.15
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
 * Thrown when an invalid MBean attribute is passed to a query
 * constructing method.  This exception is used internally by JMX
 * during the evaluation of a query.  User code does not usually
 * see it.
 *
 * @since-jdkbundle 1.5
 */
public class BadAttributeValueExpException extends Exception   { 


    /* Serial version */
    private static final long serialVersionUID = -3105272988410493376L;

    /**
     * @serial The attribute value that originated this exception
     */
    private Object val;

    /**
     * Constructs an <CODE>BadAttributeValueExpException</CODE> with the specified Object.
     *
     * @param val the inappropriate value.
     */
    public BadAttributeValueExpException (Object val) { 
	this.val = val;
    } 
   
 
    /**
     * Returns the string representing the object.
     */
    public String toString()  { 
	return "BadAttributeValueException: " + val;
    } 

 }
