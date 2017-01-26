/**
* SampleMBean.java
* Copyright 2006 Sun Microsystems, Inc. All rights reserved.
* SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*/
import mycustomclasses.HelloHolder;
/**
 * Interface SampleMBean
 * Sample MBean
 * @author jfdenise
 */
public interface SampleMBean
{
    /**
     * Returns an hello holder
     */
    public HelloHolder getHello();
}


