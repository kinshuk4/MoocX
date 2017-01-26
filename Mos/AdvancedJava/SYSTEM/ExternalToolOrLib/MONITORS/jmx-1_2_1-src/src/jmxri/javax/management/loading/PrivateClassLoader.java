/* 
 * @(#)file      PrivateClassLoader.java 
 * @(#)author    Sun Microsystems, Inc. 
 * @(#)version   1.8 
 * @(#)lastedit      03/07/15 
 * 
 * Copyright 2003 Sun Microsystems, Inc. All rights reserved.
 * This software is the proprietary information of Sun Microsystems, Inc.
 * Use is subject to license terms.
 *
 * Copyright 2003 Sun Microsystems, Inc. Tous droits réservés.
 * Ce logiciel est proprieté de Sun Microsystems, Inc.
 * Distribué par des licences qui en restreignent l'utilisation. 
 */ 

package javax.management.loading;

import javax.management.MBeanServer; // for Javadoc
import javax.management.ObjectName;  // for Javadoc

/**
 * Marker interface indicating that a ClassLoader should not be added
 * to the {@link ClassLoaderRepository}.  When a ClassLoader is
 * registered as an MBean in the MBean server, it is added to the
 * MBean server's ClassLoaderRepository unless it implements this
 * interface.
 *
 * @since-jdkbundle 1.5
 * @since JMX 1.2
 */
public interface PrivateClassLoader {}
