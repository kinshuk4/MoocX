/*
 * @(#)file      OpenMBeanAttributeInfo.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   3.15
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


// jmx import
//


/**
 * <p>Describes an attribute of an open MBean.</p>
 *
 * <p>This interface declares the same methods as the class {@link
 * javax.management.MBeanAttributeInfo}.  A class implementing this
 * interface (typically {@link OpenMBeanAttributeInfoSupport}) should
 * extend {@link javax.management.MBeanAttributeInfo}.</p>
 *
 * @version     3.15  03/07/15
 * @author      Sun Microsystems, Inc.
 *
 * @since-jdkbundle 1.5
 * @since JMX 1.1
 */
public interface OpenMBeanAttributeInfo extends OpenMBeanParameterInfo {


    // Re-declares the methods that are in class MBeanAttributeInfo of JMX 1.0
    // (these will be removed when MBeanAttributeInfo is made a parent interface of this interface)

    /**
     * Returns <tt>true</tt> if the attribute described by this <tt>OpenMBeanAttributeInfo</tt> instance is readable,
     * <tt>false</tt> otherwise.
     *
     * @return true if the attribute is readable.
     */
    public boolean isReadable() ;

    /**
     * Returns <tt>true</tt> if the attribute described by this <tt>OpenMBeanAttributeInfo</tt> instance is writable,
     * <tt>false</tt> otherwise.
     *
     * @return true if the attribute is writable.
     */
    public boolean isWritable() ;

    /**
     * Returns <tt>true</tt> if the attribute described by this <tt>OpenMBeanAttributeInfo</tt> instance
     * is accessed through a <tt>is<i>XXX</i></tt> getter (applies only to <tt>boolean</tt> and <tt>Boolean</tt> values),
     * <tt>false</tt> otherwise.
     *
     * @return true if the attribute is accessed through <tt>is<i>XXX</i></tt>.
     */
    public boolean isIs() ;


    // commodity methods
    //

    /**
     * Compares the specified <var>obj</var> parameter with this <code>OpenMBeanAttributeInfo</code> instance for equality. 
     * <p>
     * Returns <tt>true</tt> if and only if all of the following statements are true:
     * <ul>
     * <li><var>obj</var> is non null,</li>
     * <li><var>obj</var> also implements the <code>OpenMBeanAttributeInfo</code> interface,</li>
     * <li>their names are equal</li>
     * <li>their open types are equal</li>
     * <li>their access properties (isReadable, isWritable and isIs) are equal</li>
     * <li>their default, min, max and legal values are equal.</li>
     * </ul>
     * This ensures that this <tt>equals</tt> method works properly for <var>obj</var> parameters which are
     * different implementations of the <code>OpenMBeanAttributeInfo</code> interface.
     * <br>&nbsp;
     * @param  obj  the object to be compared for equality with this <code>OpenMBeanAttributeInfo</code> instance;
     * 
     * @return  <code>true</code> if the specified object is equal to this <code>OpenMBeanAttributeInfo</code> instance.
     */
    public boolean equals(Object obj);

    /**
     * Returns the hash code value for this <code>OpenMBeanAttributeInfo</code> instance. 
     * <p>
     * The hash code of an <code>OpenMBeanAttributeInfo</code> instance is the sum of the hash codes
     * of all elements of information used in <code>equals</code> comparisons 
     * (ie: its name, its <i>open type</i>, and its default, min, max and legal values). 
     * <p>
     * This ensures that <code> t1.equals(t2) </code> implies that <code> t1.hashCode()==t2.hashCode() </code> 
     * for any two <code>OpenMBeanAttributeInfo</code> instances <code>t1</code> and <code>t2</code>, 
     * as required by the general contract of the method
     * {@link <a href="http://java.sun.com/j2se/1.3/docs/api/java/lang/Object.html#hashCode()"> 
     * <code>Object.hashCode</code> </a>}.
     * <p>
     *
     * @return  the hash code value for this <code>OpenMBeanAttributeInfo</code> instance
     */
    public int hashCode();

    /**
     * Returns a string representation of this <code>OpenMBeanAttributeInfo</code> instance. 
     * <p>
     * The string representation consists of the name of this class (ie <code>javax.management.openmbean.OpenMBeanAttributeInfo</code>), 
     * the string representation of the name and open type of the described attribute, 
     * and the string representation of its default, min, max and legal values.
     * 
     * @return  a string representation of this <code>OpenMBeanAttributeInfo</code> instance
     */
    public String toString();


    // methods specific to open MBeans are inherited from 
    // OpenMBeanParameterInfo
    //

}
