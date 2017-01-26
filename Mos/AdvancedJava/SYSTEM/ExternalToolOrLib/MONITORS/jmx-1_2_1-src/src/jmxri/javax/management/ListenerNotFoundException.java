/*
 * @(#)file      ListenerNotFoundException.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   4.13
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
 * The specified MBean listener does not exist in the repository.
 *
 * @since-jdkbundle 1.5
 */
public class ListenerNotFoundException extends OperationsException   { 
    
    /* Serial version */
    private static final long serialVersionUID = -7242605822448519061L;

    /**
     * Default constructor.
     */
    public ListenerNotFoundException() {
	super();
    }
    
    /**
     * Constructor that allows a specific error message to be specified.
     *
     * @param message the detail message.
     */
    public ListenerNotFoundException(String message) {
	super(message);
    }
    
}
