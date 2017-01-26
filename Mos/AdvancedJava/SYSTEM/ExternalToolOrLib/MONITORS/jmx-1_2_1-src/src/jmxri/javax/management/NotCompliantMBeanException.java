/*
 * @(#)file      NotCompliantMBeanException.java
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
 * Exception which occurs when trying to register an  object in the MBean server that is not a JMX compliant MBean. 
 *
 * @since-jdkbundle 1.5
 */
public class NotCompliantMBeanException  extends OperationsException {


    /* Serial version */
    private static final long serialVersionUID = 5175579583207963577L;

    /**
     * Default constructor.
     */
    public NotCompliantMBeanException()  {      
	super();
    } 

    /**
     * Constructor that allows a specific error message to be specified.
     *
     * @param message the detail message.
     */
    public NotCompliantMBeanException(String message)  {      
	super(message);
    } 
    
 }
