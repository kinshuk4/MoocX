/*
 * @(#)file      Introspector.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   1.56
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
import java.lang.reflect.*;
import java.util.Enumeration;
import java.util.ArrayList;

// RI Import
import javax.management.MBeanInfo;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanConstructorInfo;
import javax.management.NotCompliantMBeanException;
import javax.management.MBeanParameterInfo;


/**
 * This class contains the methods for performing all the tests needed to verify
 * that a class represents a JMX compliant MBean.
 *
 * @since-jdkbundle 1.5
 */
public class Introspector {
    

    /*
     * ------------------------------------------
     *  PRIVATE VARIABLES
     * ------------------------------------------
     */
    
    private static java.util.Vector attributes;
    private static java.util.Vector operations;
    
    private static String className = null;
    private static Class baseClass = null;
    private static final String attributeDescription = "Attribute exposed for management";
    private static final String operationDescription = "Operation exposed for management";
    private static final String constructorDescription = "Public constructor of the MBean";
    private static final String mbeanInfoDescription = "Information on the management interface of the MBean";
    

     /*
     * ------------------------------------------
     *  PRIVATE CONSTRUCTORS
     * ------------------------------------------
     */

    // private constructor defined to "hide" the default public constructor
    private Introspector() {

	// ------------------------------ 
	// ------------------------------
	
    }
    
    /*
     * ------------------------------------------
     *  PUBLIC METHODS
     * ------------------------------------------
     */

    /**
     * Tell whether a MBean of the given class is a Dynamic MBean.
     * This method does nothing more than returning
     * <pre>
     * javax.management.DynamicMBean.class.isAssignableFrom(c)
     * </pre>
     * This method does not check for any JMX MBean compliance:
     * <ul><li>If <code>true</code> is returned, then instances of 
     *     <code>c</code> are DynamicMBean.</li>
     *     <li>If <code>false</code> is returned, then no further
     *     assumption can be made on instances of <code>c</code>.
     *     In particular, instances of <code>c</code> may, or may not
     *     be JMX standard MBeans.</li>
     * </ul>
     * @param c The class of the MBean under examination.
     * @return <code>true</code> if instances of <code>c</code> are
     *         Dynamic MBeans, <code>false</code> otherwise. 
     *
     * @since JMX RI 1.2
     **/
    public static final boolean isDynamic(final Class c) {
	// Check if the MBean implements the DynamicMBean interface
	return javax.management.DynamicMBean.class.isAssignableFrom(c);
    }

    /**
     * Basic method for testing that a MBean of a given class can be
     * instantiated by the MBean server.<p>
     * This method checks that:
     * <ul><li>The given class is a concrete class.</li>
     *     <li>The given class exposes at least one public constructor.</li>
     * </ul>
     * If these conditions are not met, throws a NotCompliantMBeanException.
     * @param c The class of the MBean we want to create.
     * @exception NotCompliantMBeanException if the MBean class makes it
     *            impossible to instantiate the MBean from within the
     *            MBeanServer.
     *
     * @since JMX RI 1.2
     **/
    public static void testCreation(Class c) 
	throws NotCompliantMBeanException {
	// Check if the class is a concrete class
	final int mods = c.getModifiers(); 
	if (Modifier.isAbstract(mods) || Modifier.isInterface(mods)) {
	    throw new NotCompliantMBeanException("The MBean must have a concrete class in order to be created remotely.");
	}

	// Check if the MBean has a public constructor 
	final Constructor[] consList = c.getConstructors();     
	if (consList.length == 0) {
	    throw new NotCompliantMBeanException("The MBean must have a public constructor in order to be created remotely");
	}
    }
    
    /**
     * Basic method for testing if a given class is a JMX compliant MBean.
     *
     * @param c The class to be tested
     *
     * @return <code>null</code> if the MBean is a DynamicMBean, 
     *         the computed {@link javax.management.MBeanInfo} otherwise.
     * @exception NotCompliantMBeanException The specified class is not a 
     *            JMX compliant MBean
     */    
    public static synchronized MBeanInfo testCompliance(Class c) 
	throws NotCompliantMBeanException {

	// ------------------------------ 
	// ------------------------------
	
	// Check if the MBean implements the MBean or the Dynamic 
	// MBean interface
	if (isDynamic(c)) 
	    return null;
	
	return testCompliance(c,null);
    }

    
    /**
     * Basic method for testing if a given class is a JMX compliant MBean.
     *
     * @param c The class to be tested
     *
     * @return <code>null</code> if the MBean is a DynamicMBean, 
     *         the computed {@link javax.management.MBeanInfo} otherwise.
     * @exception NotCompliantMBeanException The specified class is not a 
     *            JMX compliant MBean
     */    
    static synchronized MBeanInfo testCompliance(final Class c,
						 Class mbeanInterface) 
	throws NotCompliantMBeanException {
	
	if (c.isInterface()) 
	    throw new NotCompliantMBeanException(c.getName() + 
						 " must be a class.");
	// ------------------------------ 
	// ------------------------------
	baseClass = c;
	className = c.getName();
	
	if (mbeanInterface == null)
	    // No interface specified: look for default MBean interface.
	    mbeanInterface = getStandardMBeanInterface(c);
	else if (! mbeanInterface.isAssignableFrom(c)) throw new 
	    // specified interface not implemented by given class
	    NotCompliantMBeanException(c.getName() + 
				       " does not implement the " + 
				       mbeanInterface.getName() + 
				       " interface");
	else if (! mbeanInterface.isInterface()) throw new 
	    // specified interface not implemented by given class
	    NotCompliantMBeanException(c.getName() + 
				       ": " + mbeanInterface.getName() + 
				       " is not an interface");


	if (mbeanInterface == null) throw new 
	    // Error: MBean does not implement javax.management.DynamicMBean 
	    // nor MBean interface
	    NotCompliantMBeanException(c.getName() + 
				       " does not implement the " +
				       c.getName() + "MBean interface " +
				       "or the DynamicMBean interface");
	
	final int mods = mbeanInterface.getModifiers();
	if (!Modifier.isPublic(mods)) 
	    throw new NotCompliantMBeanException(mbeanInterface.getName() + 
					 " implemented by " + c.getName() + 
					 " must be public.");

	return (introspect(mbeanInterface));
    }

    
    /**
     * Get the MBean interface implemented by a JMX standard MBean 
     * class.
     *
     * @param c The class to be tested
     * 
     * @return The MBean interface implemented by the MBean. 
     *         Return <code>null</code> if the MBean is a DynamicMBean, 
     *         or if no MBean interface is found.
     *
     */
    public static synchronized Class getMBeanInterface(Class c) {

	// ------------------------------ 
	// ------------------------------
	
	// Check if the MBean implements the MBean or the Dynamic 
	// MBean interface
	if (isDynamic(c)) return null;

	return getStandardMBeanInterface(c);     
    }
    
    /**
     * Get the MBean interface implemented by a JMX standard MBean 
     * class.
     *
     * @param c The class to be tested
     * 
     * @return The MBean interface implemented by the MBean. 
     *         Return <code>null</code> if no MBean interface is found.
     *         Does not check whether the MBean is a DynamicMBean.
     *
     */
    static synchronized Class getStandardMBeanInterface(Class c) {

	// ------------------------------ 
	// ------------------------------
	
	Class current = c;       
	Class mbeanInterface = null;
	
	while (current != null) {
	    mbeanInterface = 
		findMBeanInterface(current, current.getName());
	    if (mbeanInterface != null) break;
	    current = current.getSuperclass();
	}
	return mbeanInterface;     
    }
    
    /*
     * ------------------------------------------
     *  PRIVATE METHODS
     * ------------------------------------------
     */
    
    
    /**
     * Try to find the MBean interface corresponding to the class aName 
     * - i.e. <i>aName</i>MBean, from within aClass and its superclasses.
     **/
    private final static Class findMBeanInterface(Class aClass, String aName) {
	Class current = aClass;
	while (current != null) {
	    final Class[] interfaces = current.getInterfaces();   
	    final int len = interfaces.length;
	    for (int i=0;i<len;i++)  {	     
		final Class inter = 
		    implementsMBean(interfaces[i], aName);
		if (inter != null) return inter;
	    }
	    current = current.getSuperclass();
	}
	return null;     
    }


    /**
     * Discovers the getters, setters, operations of the class
     *
     * @param beanClass The XXMBean interface implemented by the tested class.
     *
     * @exception NotCompliantMBeanException The tested class is not a JMX compliant MBean
     */    
    private static MBeanInfo introspect(Class beanClass)  throws NotCompliantMBeanException {

	// ------------------------------ 
	// ------------------------------

	attributes = new java.util.Vector();
	operations = new java.util.Vector();
	
	Method methodList[] = null;
	methodList = beanClass.getMethods();
	
	// Now analyze each method.        
	for (int i = 0; i < methodList.length; i++) { 
	    Method method = methodList[i];
	    if (method == null) {
		continue;
	    }            
	    String name = method.getName();            
	    Class argTypes[] = method.getParameterTypes();
	    Class resultType = method.getReturnType();
	    int argCount = argTypes.length;
	    MBeanAttributeInfo attr = null;
	    MBeanOperationInfo oper = null;
	    
	    // if the method name is get(), set(), is() it is an operation 
	    if (name.equals("get") || name.equals("is") || name.equals("set")) {	
		try {
		    oper = new MBeanOperationInfo(operationDescription,method);
		} catch (Exception e) { 
		    // Guess it never happens...
		    error("introspect",e);
		}
		operations.addElement(oper);
		continue;
	    } 
	    // if the method name does not start with "get", "set", "is" it is an operation 
	    if ((!name.startsWith("get")) &&  (!name.startsWith("set")) &&  (!name.startsWith("is"))) {
		try {
		    oper = new MBeanOperationInfo(operationDescription, method);
		} catch (Exception e) { 
		    // Guess it never happens...
		    error("introspect",e);
		}
		operations.addElement(oper);
		continue;	    
	    }
	    // if the method has no parameters
	    if (argCount == 0) {
		// if the method has no parameters and starts with "get" and returns void it is an operation 
		if (name.startsWith("get")) {
		    if (resultType == Void.TYPE) {
			// operation
			try {
			    oper = new MBeanOperationInfo(operationDescription, method);
			} catch (Exception e) { 
			    // Guess it never happens...
			    error("introspect",e);
			}
			operations.addElement(oper);
			continue;
		    }
		    
		    // if the method has no parameters and starts with "get" and does not return void it is a getter. 
		    try {
			attr = new MBeanAttributeInfo(name.substring(3), attributeDescription, method, null);
		    } catch (Exception e) {
			// Guess it never happens...
			error("introspect",e);
		    }		
		    if (testConsistency(attr)) {
			attributes.addElement(attr);
		    }
		    continue;
		}
		
		// if the method has no parameters and starts with "is" and returns boolean or Boolean it is a getter. 
		if (((resultType == Boolean.class ) || (resultType == Boolean.TYPE)) && (name.startsWith("is"))) {
		    try {
			attr = new MBeanAttributeInfo(name.substring(2), attributeDescription, method, null);
		    } catch (Exception e) { 
			// Guess it never happens...
			error("introspect",e);
		    }		
		    if (testConsistency(attr)) {
			attributes.addElement(attr);
		    }
		    continue;
		}
		// if the method has no parameters and starts with "set" it is an operation.
		if (name.startsWith("set")) {
		    // operation
		    try {
			oper = new MBeanOperationInfo(operationDescription, method);
		    } catch (Exception e) { 
			// Guess it never happens...
			error("introspect",e);
		    }
		    operations.addElement(oper);
		    continue;
		}	
	    } 
	    // if the method has one parameter	 
	    if (argCount == 1) {
		// if the method has one parameter and starts with "set" and returns void it is a setter.
		if (((resultType == Void.TYPE)) && (name.startsWith("set"))) {
		    // Simple setter.
		    try {
			attr = new MBeanAttributeInfo(name.substring(3), attributeDescription, null, method);
		    } catch (Exception e) { 
			// Guess it never happens...
			error("introspect",e);
		    }		
		    if (testConsistency(attr)) {
			attributes.addElement(attr);
		    }
		    continue;
		} else {
		    // if the method has one parameter and starts with "set" and does not return  void it is an operation.
		    try {
			oper = new MBeanOperationInfo(operationDescription, method);
		    } catch (Exception e) { 
			// Guess it never happens...
			error("introspect",e);
		    }	
		    operations.addElement(oper);
		    continue;
		}
	    } 
	    // if the method has more then one parameters it is an operation	 
	    if (argCount > 1) {
		// operation
		try {
		    oper = new MBeanOperationInfo(operationDescription,method);
		} catch (Exception e) { 
		    // Guess it never happens...
		    error("introspect",e);
		}
		operations.addElement(oper);
		continue;		   	
	    }		    	    
	}
	return constructResult(); 
    }
        
    /**
     * Checks if the types and the signatures of getters/setters/operations are conform to the MBean
     * design patterns.Error cases:
     * 	-  It exposes a method void Y getXX() AND a method void setXX(Z) (parameter type mismatch) 
     * 	-  It exposes a method void setXX(Y) AND a method void setXX(Z) (parameter type mismatch) 
     *     -  It exposes a  boolean isXX() method AND a YY getXX() or a void setXX(Y).
     * Returns false if the attribute is already in attributes Vector
     */    
    private static boolean testConsistency(MBeanAttributeInfo attr)  
	throws NotCompliantMBeanException {
	for (Enumeration i = attributes.elements();i.hasMoreElements();) {
	    MBeanAttributeInfo mb = (MBeanAttributeInfo) i.nextElement();	
	    if (mb.getName().compareTo(attr.getName()) == 0) {
		if ((attr.isReadable() && mb.isReadable()) && 
		    (attr.isIs() != mb.isIs())) {
		    throw new NotCompliantMBeanException("Getter is" + mb.getName() + " cannot co-exist with getter get" + attr.getName());
		}  
		if (mb.getType().compareTo(attr.getType()) != 0) {
		    if (mb.isWritable() && attr.isWritable()) {
			throw new NotCompliantMBeanException("Type mismatch between parameters of set" + mb.getName() + " methods");
		    } else {
			throw new NotCompliantMBeanException("Type mismatch between parameters of get or is" + mb.getName() + ", set" + mb.getName() + " methods");		    
		    }		  
		}
		if (attr.isReadable() && mb.isReadable()) {
		    return false;
		}
		if (attr.isWritable() && mb.isWritable()) {
		    return false;
		}
	    }   
	}
	return true;
    }

    /**
     * Discovers the constructors of the MBean
     */
    static MBeanConstructorInfo[] getConstructors () {
	Constructor[] consList = baseClass.getConstructors();
	MBeanConstructorInfo[] resultConstructors;
	java.util.Vector constructors = new java.util.Vector();
	
	// Now analyze each Constructor.        
	for (int i = 0; i < consList.length; i++) {
	    Constructor constructor = consList[i];    	    
	    MBeanConstructorInfo mc = null;
	    try {               
		mc = new MBeanConstructorInfo(constructorDescription, constructor);		     		                
	    } catch (Exception ex) {
		mc = null;
	    }
	    if (mc != null) {
		constructors.addElement(mc);
	    }
	}
	// Allocate and populate the result array.
	resultConstructors = new MBeanConstructorInfo[constructors.size()];        
	for (int i = 0; i < resultConstructors.length; i++) {
	    resultConstructors[i] = (MBeanConstructorInfo)constructors.elementAt(i);
	}
	return resultConstructors;     
    }
    
    /**
     * Constructs the MBeanInfo of the MBean.
     */
    private static MBeanInfo constructResult() {
	
	final int len = attributes.size();
	final MBeanAttributeInfo[] attrlist = new MBeanAttributeInfo[len];
	attributes.toArray(attrlist);
	final ArrayList mergedAttributes = new ArrayList();
	
	for (int i=0;i<len;i++) {
	    final MBeanAttributeInfo bi = attrlist[i];
	    
	    // bi can be null if it has already been eliminated
	    // by the loop below at an earlier iteration
	    // (cf. attrlist[j]=null;) In this case, just skip it.
	    //
	    if (bi == null) continue;

	    // Placeholder for the final attribute info we're going to
	    // keep.
	    //
	    MBeanAttributeInfo att = bi;

	    // The loop below will try to find whether bi is also present
	    // elsewhere further down the list. 
	    // If it is not, att will be left unchanged.
	    // Otherwise, the found attribute info will be merged with
	    // att and `removed' from the array by setting them to `null'
	    //
            for (int j=i+1;j<len;j++) {
		MBeanAttributeInfo mi = attrlist[j];
		
		// mi can be null if it has already been eliminated
		// by this loop at an earlier iteration.
		// (cf. attrlist[j]=null;) In this case, just skip it.
		//
		if (mi == null) continue;
                if ((mi.getName().compareTo(bi.getName()) == 0)) {
		    // mi and bi have the same name, which means that 
		    // that the attribute has been inserted twice in 
		    // the list, which means that it is a read-write
		    // attribute.
		    // So we're going to replace att with a new 
		    // attribute info with read-write mode.
		    // We also set attrlist[j] to null in order to avoid
		    // duplicates (attrlist[j] and attrlist[i] are now
		    // merged into att).
		    //
		    attrlist[j]=null;
		    att = new MBeanAttributeInfo(bi.getName(), 
						 bi.getType(), 
						 attributeDescription, 
						 true, true, bi.isIs());
		    // I think we could break, but it is probably
		    // safer not to...
		    //
		    // break;
		}
	    }
                
	    // Now all attributes info which had the same name than bi
	    // have been merged together in att. 
	    // Simply add att to the merged list.
	    //
	    mergedAttributes.add(att);	    
        }
 
        final MBeanAttributeInfo[] resultAttributes = 
	    new MBeanAttributeInfo[mergedAttributes.size()];
	mergedAttributes.toArray(resultAttributes);

        final MBeanOperationInfo[] resultOperations = 
	    new MBeanOperationInfo[operations.size()];
	operations.toArray(resultOperations);

	final MBeanConstructorInfo[] resultConstructors = 
	    getConstructors();     

        final MBeanInfo resultMBeanInfo = 
	    new MBeanInfo(className, mbeanInfoDescription, 
			  resultAttributes, resultConstructors, 
			  resultOperations, null);
	return resultMBeanInfo;
    }

    /**
     * Returns the XXMBean interface or null if no such interface exists
     *
     * @param c The interface to be tested
     * @param clName The name of the class implementing this interface
     */
    static Class implementsMBean(Class c, String clName) {
	if (c.getName().compareTo(clName + "MBean") == 0) {
	    return c;
	}   
	Class current = c;
	Class[] interfaces = c.getInterfaces();
	for (int i = 0;i < interfaces.length; i++) {

	    try {
		if (interfaces[i].getName().compareTo(clName + "MBean") == 0) {
		    return interfaces[i];
		}     
	    } catch (Exception e) {
		return null;
	    }  	
	}
	
	return null;
    }
    
    private static void error(String method,Throwable t) {
	com.sun.jmx.trace.Trace.send(com.sun.jmx.trace.Trace.LEVEL_ERROR,
				     com.sun.jmx.trace.Trace.INFO_MBEANSERVER,
				     "Introspector",
				     method,
				     t);
				     
    }
}

