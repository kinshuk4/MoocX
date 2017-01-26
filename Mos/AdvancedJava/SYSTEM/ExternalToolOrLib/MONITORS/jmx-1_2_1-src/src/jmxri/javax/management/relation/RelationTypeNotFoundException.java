/*
 * @(#)file      RelationTypeNotFoundException.java
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
 * This exception is raised when there is no relation type with given name in
 * Relation Service.
 *
 * @since-jdkbundle 1.5
 */
public class RelationTypeNotFoundException extends RelationException {

    /* Serial version */
    private static final long serialVersionUID = 1274155316284300752L;

    /**
     * Default constructor, no message put in exception.
     */
    public RelationTypeNotFoundException() {
	super();
    }

    /**
     * Constructor with given message put in exception.
     *
     * @param message the detail message.
     */
    public RelationTypeNotFoundException(String message) {
	super(message);
    }
}
