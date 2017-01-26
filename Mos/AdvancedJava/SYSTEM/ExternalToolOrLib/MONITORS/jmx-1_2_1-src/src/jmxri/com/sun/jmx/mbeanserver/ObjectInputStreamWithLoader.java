/*
 * @(#)file      ObjectInputStreamWithLoader.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   4.17
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

package com.sun.jmx.mbeanserver;

// Java import
import java.io.ObjectInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.StreamCorruptedException;
import java.io.ObjectStreamClass;

import javax.management.* ; 
 



/**
 * The class deserializes an object in the context of a specific class loader.
 *
 * @since-jdkbundle 1.5
 */
class ObjectInputStreamWithLoader extends ObjectInputStream {

    
    private ClassLoader loader;


    /**
     * @exception IOException Signals that an I/O exception of some sort has occurred.
     * @exception StreamCorruptedException The object stream is corrupt.
     */
    public  ObjectInputStreamWithLoader(InputStream in, ClassLoader theLoader) 
	throws IOException{
	super(in);
	this.loader= theLoader;
    }
    
    protected Class resolveClass(ObjectStreamClass aClass) 
	throws IOException, ClassNotFoundException {      
	if ( loader == null ) {
	    return super.resolveClass(aClass) ;
      }
	else {
	    String name= aClass.getName();
	    // Query the class loader ...    	
	    Class result= loader.loadClass(name);
	    return result;
	}
    }
}

