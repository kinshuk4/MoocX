/*
 * @(#)file      StringValueExp.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   4.17
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
 * Represents strings that are arguments to relational constraints.
 * A <CODE>StringValueExp</CODE> may be used anywhere a <CODE>ValueExp</CODE> is required.
 *
 * @since-jdkbundle 1.5
 */
public class StringValueExp implements ValueExp   {   
    
    /* Serial version */
    private static final long serialVersionUID = -3256390509806284044L;

    /**
     * @serial The string literal
     */
    private String val;

    /**
     * Basic constructor.
     */    
    public StringValueExp() { 
    } 

    /**
     * Creates a new <CODE>StringValueExp</CODE> representing the
     * given string.
     *
     * @param val the string that will be the value of this expression
     */
    public StringValueExp(String val) { 
	this.val = val;
    } 

    /**
     * Returns the string represented by the
     * <CODE>StringValueExp</CODE> instance.
     *
     * @return the string.
     */   
    public String getValue()  { 
	return val;
    } 

    /**
     * Returns the string representing the object.
     */
    public String toString()  { 
	return "'" + val + "'";
    } 
   

    /**
     * Sets the MBean server on which the query is to be performed.
     *
     * @param s The MBean server on which the query is to be performed.
     */
    public void setMBeanServer(MBeanServer s)  { }        

    /**
     * Applies the ValueExp on a MBean.
     *
     * @param name The name of the MBean on which the ValueExp will be applied.
     *
     * @return  The <CODE>ValueExp</CODE>.
     *
     * @exception BadStringOperationException
     * @exception BadBinaryOpValueExpException
     * @exception BadAttributeValueExpException 
     * @exception InvalidApplicationException
     */   
    public ValueExp apply(ObjectName name) throws BadStringOperationException, BadBinaryOpValueExpException,
	BadAttributeValueExpException, InvalidApplicationException  { 
	return this;
    } 
 }
