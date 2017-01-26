/*
 * @(#)file      RelationException.java
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

import javax.management.JMException;

/**
 * This class is the superclass of any exception which can be raised during
 * relation management.
 *
 * @since-jdkbundle 1.5
 */
public class RelationException extends JMException {

    /* Serial version */
    private static final long serialVersionUID = 5434016005679159613L;

    /**
     * Default constructor, no message put in exception.
     */
    public RelationException() {
	super();
    }

    /**
     * Constructor with given message put in exception.
     *
     * @param message the detail message.
     */
    public RelationException(String message) {
	super(message);
    }
}
