/**
* Sample.java
* Copyright 2006 Sun Microsystems, Inc. All rights reserved.
* SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*/

import javax.management.*;

/**
 * Class Sample
 * Sample MBean
 */
public class Sample implements SampleMBean {

    /**
     * Attribute : Hello
     */
    private String hello = "Hello World(s)!";
    
    public Sample() {
    }

    /**
     * Returns an hello message
     */
    public String getHello() {
        return hello;
    }

}


