/*
 * @(#)file      KeyAlreadyExistsException.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   3.13
 * @(#)lastedit      03/07/15
 *
 * Copyright 2000-2003 Sun Microsystems, Inc.  All rights reserved.
 * This software is the proprietary information of Sun Microsystems, Inc.
 * Use is subject to license terms.
 * 
 * Copyright 2000-2003 Sun Microsystems, Inc.  Tous droits réservés.
 * Ce logiciel est proprieté de Sun Microsystems, Inc.
 * Distribué par des licences qui en restreignent l'utilisation. 
 *
 */


package javax.management.openmbean;


// java import
//
import java.io.Serializable;


// jmx import
//


/**
 * This runtime exception is thrown to indicate that the index of a row to be added to a <i>tabular data</i> instance
 * is already used to refer to another row in this <i>tabular data</i> instance.
 *
 * @version     3.13  03/07/15
 * @author      Sun Microsystems, Inc.
 *
 * @since-jdkbundle 1.5
 * @since JMX 1.1
 */
public class KeyAlreadyExistsException 
    extends IllegalArgumentException
    implements Serializable {

    private static final long serialVersionUID = 1845183636745282866L;

    /**
     * A KeyAlreadyExistsException with no detail message.
     */
    public KeyAlreadyExistsException() {
	super();
    }

    /**
     * A KeyAlreadyExistsException with a detail message.
     *
     * @param msg the detail message.
     */
    public KeyAlreadyExistsException(String msg) {
	super(msg);
    }

}
