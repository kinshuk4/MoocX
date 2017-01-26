/*
 * @(#)file      OpenDataException.java
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
import javax.management.JMException;


/**
 * This checked exception is thrown when an <i>open type</i>, an <i>open data</i>  or an <i>open MBean metadata info</i> instance 
 * could not be constructed because one or more validity constraints were not met.
 *
 * @version     3.13  03/07/15
 * @author      Sun Microsystems, Inc.
 *
 * @since-jdkbundle 1.5
 * @since JMX 1.1
 */
public class OpenDataException 
    extends JMException
    implements Serializable {

    private static final long serialVersionUID = 8346311255433349870L;

    /**
     * An OpenDataException with no detail message.
     */
    public OpenDataException() {
	super();
    }

    /**
     * An OpenDataException with a detail message.
     *
     * @param msg the detail message.
     */
    public OpenDataException(String msg) {
	super(msg);
    }

}
