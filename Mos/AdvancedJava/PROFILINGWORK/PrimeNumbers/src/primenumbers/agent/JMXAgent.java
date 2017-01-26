/*
 * JMXAgent.java
 *
 * Created on June 13, 2005, 1:22 PM
 */

package primenumbers.agent;

import javax.management.ObjectName;
import javax.management.MBeanServer;
import java.lang.management.ManagementFactory;
import primenumbers.mbeans.PrimeNumbersMgmt;

/**
 * JMX agent class.
 * You may use the New JMX MBean wizard to create a Managed Bean.
 * @author gs145266
 */
public class JMXAgent {
    
    private PrimeNumbersMgmt jmxBean_;
    
    public PrimeNumbersMgmt getPrimeNumbersMgmt() {
        return jmxBean_;
    }
    
    /**
     * Instantiate and register your MBeans.
     */
    public void init() throws Exception {
        
        jmxBean_ = new PrimeNumbersMgmt();
        
        ObjectName mbeanName = new ObjectName (":type=PrimeNumbersMgmt");
        
        //Register the CounterMonitor MBean
        getMBeanServer().registerMBean(jmxBean_, mbeanName);

    }
    
    /**
     * Returns an agent singleton.
     */
    public synchronized static JMXAgent getDefault() throws Exception {
        if(singleton == null) {
            singleton = new JMXAgent();
            singleton.init();
        }
        return singleton;
    }
    
    public MBeanServer getMBeanServer() {
        return mbs;
    }
    
    // Platform MBeanServer used to register your MBeans
    private final MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
    
    // Singleton instance
    private static JMXAgent singleton;
}


