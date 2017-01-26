/*
 * @(#)file      InvalidKeyException.java
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
 * This runtime exception is thrown to indicate that a method parameter which was expected to be 
 * an item name of a <i>composite data</i> or a row index of a <i>tabular data</i> is not valid.
 *
 * @version     3.13  03/07/15
 * @author      Sun Microsystems, Inc.
 *
 * @since-jdkbundle 1.5
 * @since JMX 1.1
 */
public class InvalidKeyException 
    extends IllegalArgumentException
    implements Serializable {

    private static final long serialVersionUID = 4224269443946322062L;

    /**
     * An InvalidKeyException with no detail message.
     */
    public InvalidKeyException() {
	super();
    }

    /**
     * An InvalidKeyException with a detail message.
     *
     * @param msg the detail message.
     */
    public InvalidKeyException(String msg) {
	super(msg);
    }

}
