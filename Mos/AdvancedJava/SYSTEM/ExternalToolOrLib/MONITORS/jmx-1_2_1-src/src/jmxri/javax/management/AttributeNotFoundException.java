/*
 * @(#)file      AttributeNotFoundException.java
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
 * The specified attribute does not exist or cannot be retrieved.
 *
 * @since-jdkbundle 1.5
 */
public class AttributeNotFoundException extends OperationsException {

    /* Serial version */
    private static final long serialVersionUID = 6511584241791106926L;

    /**
     * Default constructor.
     */
    public AttributeNotFoundException() {
	super();
    }

    /**
     * Constructor that allows a specific error message to be specified.
     *
     * @param message detail message.
     */
    public AttributeNotFoundException(String message) {
	super(message);
    }

}
