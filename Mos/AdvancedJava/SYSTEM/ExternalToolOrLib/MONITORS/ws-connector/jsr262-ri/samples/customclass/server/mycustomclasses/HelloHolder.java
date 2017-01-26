/**
* HelloHolder.java
* Copyright 2006 Sun Microsystems, Inc. All rights reserved.
* SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*/
package mycustomclasses;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Custom class JAXB annotated in order for to marshall to XML 
 * and unmarshall to Java.
 *
 * A class named <code>ObjectFactory</code>, located in the same package
 * is needed in order for JAXB to properly handle the custom type.
 * 
 */
//Declare the element that will represent the XML representation
@XmlRootElement(name="Hello", namespace="http://my.custom.classes/hello")
public class HelloHolder {
    //Make the hello string an XML element attribute
    @XmlAttribute(name="message")
    private String hello = "Hello World(s)!";
    
    /** Creates a new instance of HelloHolder */
    public HelloHolder() {
    }
    
    public String getHello() {
        return hello;
    }
}
