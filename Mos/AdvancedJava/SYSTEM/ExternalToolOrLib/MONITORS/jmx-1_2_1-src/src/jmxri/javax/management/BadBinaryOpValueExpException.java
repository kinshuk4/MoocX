/*
 * @(#)file      BadBinaryOpValueExpException.java
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
 * Thrown when an invalid expression is passed to a method for
 * constructing a query.  This exception is used internally by JMX
 * during the evaluation of a query.  User code does not usually see
 * it.
 *
 * @since-jdkbundle 1.5
 */
public class BadBinaryOpValueExpException extends Exception   { 

    
    /* Serial version */
    private static final long serialVersionUID = 5068475589449021227L;

    /**
     * @serial the {@link ValueExp} that originated this exception
     */
    private ValueExp exp;


    /**
     * Constructs a <CODE>BadBinaryOpValueExpException</CODE> with the specified <CODE>ValueExp</CODE>.
     *
     * @param exp the expression whose value was inappropriate.
     */
    public BadBinaryOpValueExpException(ValueExp exp) { 	
	this.exp = exp;
    } 
    

    /**
     * Returns the <CODE>ValueExp</CODE> that originated the exception.
     *
     * @return the problematic {@link ValueExp}.
     */   
    public ValueExp getExp()  { 
	return exp;
    } 

    /**
     * Returns the string representing the object.
     */    
    public String toString()  { 
	return "BadBinaryOpValueExpException: " + exp;
    }

 }
