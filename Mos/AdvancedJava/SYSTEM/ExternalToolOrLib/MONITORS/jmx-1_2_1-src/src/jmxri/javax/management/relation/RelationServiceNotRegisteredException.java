/*
 * @(#)file      RelationServiceNotRegisteredException.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   1.13
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

package javax.management.relation;

/**
 * This exception is raised when an access is done to the Relation Service and
 * that one is not registered.
 *
 * @since-jdkbundle 1.5
 */
public class RelationServiceNotRegisteredException extends RelationException {

    /* Serial version */
    private static final long serialVersionUID = 8454744887157122910L;

    /**
     * Default constructor, no message put in exception.
     */
    public RelationServiceNotRegisteredException() {
	super();
    }

    /**
     * Constructor with given message put in exception.
     *
     * @param message the detail message.
     */
    public RelationServiceNotRegisteredException(String message) {
	super(message);
    }
}
