/**
* SampleMBean.java
* Copyright 2006 Sun Microsystems, Inc. All rights reserved.
* SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*/
/**
 * Interface SampleMBean
 * Sample MBean
 */
public interface SampleMBean
{
    /**
     * Get Returns an hello message
     */
    public String getHello();
    public String doIt(String p);
}


