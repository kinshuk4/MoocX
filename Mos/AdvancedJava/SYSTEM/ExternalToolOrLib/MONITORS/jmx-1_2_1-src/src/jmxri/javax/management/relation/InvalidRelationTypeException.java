/*
 * @(#)file      InvalidRelationTypeException.java
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
 * Invalid relation type.
 * This exception is raised when, in a relation type, there is already a
 * relation type with that name, or the same name has been used for two
 * different role infos, or no role info provided, or one null role info
 * provided.
 *
 * @since-jdkbundle 1.5
 */
public class InvalidRelationTypeException extends RelationException {

    /* Serial version */
    private static final long serialVersionUID = 3007446608299169961L;

    /**
     * Default constructor, no message put in exception.
     */
    public InvalidRelationTypeException() {
	super();
    }

    /**
     * Constructor with given message put in exception.
     *
     * @param message the detail message.
     */
    public InvalidRelationTypeException(String message) {
	super(message);
    }
}
