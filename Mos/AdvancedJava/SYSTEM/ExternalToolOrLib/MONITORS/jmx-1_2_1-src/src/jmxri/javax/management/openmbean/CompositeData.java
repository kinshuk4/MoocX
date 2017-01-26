/*
 * @(#)file      CompositeData.java
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
import java.util.Collection;
import java.util.Map;

// jmx import
//


/**
 * The <tt>CompositeData</tt> interface specifies the behaviour of a specific type of complex <i>open data</i> objects
 * which represent <i>composite data</i> structures.
 *
 * @version     3.13  03/07/15
 * @author      Sun Microsystems, Inc.
 *
 * @since-jdkbundle 1.5
 * @since JMX 1.1
 */
public interface CompositeData {


    /**
     * Returns the <i>composite type </i> of this <i>composite data</i> instance.
     *
     * @return the type of this CompositeData.
     */
    public CompositeType getCompositeType();

    /**
     * Returns the value of the item whose name is <tt>key</tt>.
     *
     * @param key the name of the item.
     *
     * @return the value associated with this key.
     *
     * @throws IllegalArgumentException  if <tt>key</tt> is a null or empty String.
     *
     * @throws InvalidKeyException  if <tt>key</tt> is not an existing item name for this <tt>CompositeData</tt> instance.
     */
    public Object get(String key) ;

    /**
     * Returns an array of the values of the items whose names are specified by <tt>keys</tt>, in the same order as <tt>keys</tt>.
     *
     * @param keys the names of the items.
     *
     * @return the values corresponding to the keys.
     *
     * @throws IllegalArgumentException  if an element in <tt>keys</tt> is a null or empty String.
     *
     * @throws InvalidKeyException  if an element in <tt>keys</tt> is not an existing item name for this <tt>CompositeData</tt> instance.
     */
    public Object[] getAll(String[] keys) ;

    /**
     * Returns <tt>true</tt> if and only if this <tt>CompositeData</tt> instance contains 
     * an item whose name is <tt>key</tt>. 
     * If <tt>key</tt> is a null or empty String, this method simply returns false.
     *
     * @param key the key to be tested.
     *
     * @return true if this <tt>CompositeData</tt> contains the key.
     */
    public boolean containsKey(String key) ;

    /**
     * Returns <tt>true</tt> if and only if this <tt>CompositeData</tt> instance contains an item 
     * whose value is <tt>value</tt>.
     *
     * @param value the value to be tested.
     *
     * @return true if this <tt>CompositeData</tt> contains the value.
     */
    public boolean containsValue(Object value) ;

    /**
     * Returns an unmodifiable Collection view of the item values contained in this <tt>CompositeData</tt> instance.
     * The returned collection's iterator will return the values in the ascending lexicographic order of the corresponding 
     * item names. 
     *
     * @return the values.
     */
    public Collection values() ;

    /**
     * Compares the specified <var>obj</var> parameter with this <code>CompositeData</code> instance for equality. 
     * <p>
     * Returns <tt>true</tt> if and only if all of the following statements are true:
     * <ul>
     * <li><var>obj</var> is non null,</li>
     * <li><var>obj</var> also implements the <code>CompositeData</code> interface,</li>
     * <li>their composite types are equal</li>
     * <li>their contents (ie item values) are equal.</li>
     * </ul>
     * This ensures that this <tt>equals</tt> method works properly for <var>obj</var> parameters which are
     * different implementations of the <code>CompositeData</code> interface.
     * <br>&nbsp;
     * @param  obj  the object to be compared for equality with this <code>CompositeData</code> instance;
     * 
     * @return  <code>true</code> if the specified object is equal to this <code>CompositeData</code> instance.
     */
    public boolean equals(Object obj) ;

    /**
     * Returns the hash code value for this <code>CompositeData</code> instance. 
     * <p>
     * The hash code of a <code>CompositeData</code> instance is the sum of the hash codes
     * of all elements of information used in <code>equals</code> comparisons 
     * (ie: its <i>composite type</i> and all the item values). 
     * <p>
     * This ensures that <code> t1.equals(t2) </code> implies that <code> t1.hashCode()==t2.hashCode() </code> 
     * for any two <code>CompositeData</code> instances <code>t1</code> and <code>t2</code>, 
     * as required by the general contract of the method
     * {@link <a href="http://java.sun.com/j2se/1.3/docs/api/java/lang/Object.html#hashCode()"> 
     * <code>Object.hashCode</code> </a>}.
     * <p>
     *
     * @return  the hash code value for this <code>CompositeData</code> instance
     */
    public int hashCode() ;

    /**
     * Returns a string representation of this <code>CompositeData</code> instance. 
     * <p>
     * The string representation consists of the name of the implementing class, 
     * the string representation of the composite type of this instance, and the string representation of the contents
     * (ie list the itemName=itemValue mappings).
     * 
     * @return  a string representation of this <code>CompositeData</code> instance
     */
    public String toString() ;

} 
