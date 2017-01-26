package org.mk.training.jmxjsr77;

// Import Servlet classes

// Import JNDI classes

// Import JDOM classes
import org.jdom.*;

// Import the Java classes
import java.util.*;
import javax.management.*;

/*
 *http://localhost:8080/JMXJSR77Demo/ServerStatsServlet for all
 *http://localhost:8080/JMXJSR77Demo/ServerStatsServlet?refresh=true&
 *objectName=jboss.management.local:j2eeType=ResourceAdapter,* for resource adapters
 * */
public class ServerStatsServlet extends AbstractStatsServlet {

    /**
     * Classes extending this Servlet are responsible for locating and returning
     * an MBeanServer instance. This instance is used to preload object names and for
     * managing state access.
     */
    public MBeanServer getMBeanServer() {
        try {
            List<MBeanServer> mbs = MBeanServerFactory.findMBeanServer(null);
            for (MBeanServer mBeanServer : mbs) {
                String [] domains=mBeanServer.getDomains();
                for (String string : domains) {
                    System.out.println("mBeanServer.getDefaultDomain():"+mBeanServer.getDefaultDomain()+" Domains::"+string);
                }
            }
            MBeanServer mbsjboss = mbs.iterator().next();
            return mbsjboss;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This is the main focus point of the Application Server specific Servlet classes;
     * though the getPerformanceRoot() method you will build an XML document that you
     * want to return to the caller
     */
    public Element getPerformanceRoot(MBeanServer server, Map objectNames) {
        return new Element("jboss-stats");
    }
}

