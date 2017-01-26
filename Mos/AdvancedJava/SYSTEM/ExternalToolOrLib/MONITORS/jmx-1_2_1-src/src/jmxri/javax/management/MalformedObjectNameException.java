/*
 * @(#)file      MalformedObjectNameException.java
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
 * The format of the string does not correspond to a valid ObjectName.
 *
 * @since-jdkbundle 1.5
 */
public class MalformedObjectNameException extends OperationsException   { 

    /* Serial version */
    private static final long serialVersionUID = -572689714442915824L;

    /**
     * Default constructor.
     */
    public MalformedObjectNameException() {
	super();
    }
    
    /**
     * Constructor that allows a specific error message to be specified.
     *
     * @param message the detail message.
     */
    public MalformedObjectNameException(String message) {
	super(message);
    }
}



