/*
 * @(#)file      ValueExp.java
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
 * Represents values that can be passed as arguments to
 * relational expressions. Strings, numbers, attributes are valid values
 * and should be represented by implementations of <CODE>ValueExp</CODE>.
 *
 * @since-jdkbundle 1.5
 */
public interface ValueExp  extends java.io.Serializable { 

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
	BadAttributeValueExpException, InvalidApplicationException ;

    /**
     * Sets the MBean server on which the query is to be performed.
     *
     * @param s The MBean server on which the query is to be performed.
     *
     * @deprecated This method is not needed because a
     * <code>ValueExp</code> can access the MBean server in which it
     * is being evaluated by using {@link QueryEval#getMBeanServer()}.
     */
    public  void setMBeanServer(MBeanServer s) ;
 }
