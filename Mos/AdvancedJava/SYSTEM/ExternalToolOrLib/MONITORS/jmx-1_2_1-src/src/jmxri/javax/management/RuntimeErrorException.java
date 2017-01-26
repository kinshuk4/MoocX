/*
 * @(#)file      RuntimeErrorException.java
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

package javax.management; 



/**
 * When a <CODE>java.lang.Error</CODE> occurs in the agent it should be caught and
 * re-thrown as a <CODE>RuntimeErrorException</CODE>.
 *
 * @since-jdkbundle 1.5
 */
public class RuntimeErrorException extends JMRuntimeException   { 
    
    /* Serial version */
    private static final long serialVersionUID = 704338937753949796L;

    /**
     * @serial The encapsulated {@link Error}
     */
    private java.lang.Error error ;

    /**
     * Default constructor.
     *
     * @param e the wrapped error.
     */
    public RuntimeErrorException(java.lang.Error e) {
      super();
      error = e ;
    }
    
    /**
     * Constructor that allows a specific error message to be specified.
     *
     * @param e the wrapped error.
     * @param message the detail message.
     */
    public RuntimeErrorException(java.lang.Error e, String message) {
       super(message);
       error = e ;
    }
    
    /**
     * Returns the actual {@link Error} thrown.
     *
     * @return the wrapped {@link Error}.
     */
    public java.lang.Error getTargetError()  {
	return error ;
    }

    /**
     * Returns the actual {@link Error} thrown.
     *
     * @return the wrapped {@link Error}.
     */
    public Throwable getCause() {
	return error;
    }
}
