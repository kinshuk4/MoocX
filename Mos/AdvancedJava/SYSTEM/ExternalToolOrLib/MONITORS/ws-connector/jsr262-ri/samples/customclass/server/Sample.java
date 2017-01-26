/**
* Sample.java
* Copyright 2006 Sun Microsystems, Inc. All rights reserved.
* SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*/

import javax.management.*;
import mycustomclasses.HelloHolder;

/**
 * Class Sample
 * Sample MBean
 */
public class Sample implements SampleMBean {

    /**
     * Attribute : Hello
     */
    private HelloHolder hello = new HelloHolder();
    
    public Sample() {
    }

    /**
     * Returns an hello message
     */
    public HelloHolder getHello() {
        return hello;
    }

}


