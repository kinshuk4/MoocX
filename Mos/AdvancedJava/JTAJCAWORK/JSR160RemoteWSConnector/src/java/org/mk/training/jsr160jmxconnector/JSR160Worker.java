package org.mk.training.jsr160jmxconnector;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.resource.spi.work.Work;
import javax.resource.spi.work.WorkManager;

public class JSR160Worker implements Work {
    private static Logger logger;
    static {
        logger = LogUtils.getLogger();
    }

    JSR160Adapter ra;
    private WorkManager wm = null;
    private boolean released = false;
    JMXConnectorServer cs = null;
    
    public JSR160Worker(JSR160Adapter ra,WorkManager wm) {
        super();
        this.wm = wm;
        this.ra=ra;
    }

    public void release() {
        System.out.println("release()");
        released = true;
        try {
            cs.stop();
            synchronized (this) {
                this.notify();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (!released) {
            MBeanServer mbs = null;
            try {
                InitialContext ic = new InitialContext();
                mbs = this.getMBeanServer();
                JMXServiceURL url = new JMXServiceURL(ra.getJsr160URL());
                cs = JMXConnectorServerFactory.newJMXConnectorServer(url, null, mbs);
                cs.start();
                synchronized (this) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } catch (NamingException e) {
                e.printStackTrace();
                break;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                break;
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public MBeanServer getMBeanServer() {
        try {
            /*	This gives the same results
             * 
             * InitialContext ctx = new InitialContext();
             * Object objref = ctx.lookup("ejb/mgmt/MEJB");
             * ManagementHome home = (ManagementHome)
             * PortableRemoteObject.narrow(objref,ManagementHome.class);
             * mejb = home.create();
             * return new MBServerMEJBAdaptor(mejb);
             */
            //Since JDK1.5 This should be the preferred way.
            logger.log(Level.FINE,"Finding MbeanServer for domain:-"+ra.getMbeanServerDomainName());
            List<MBeanServer> mbslist = MBeanServerFactory.findMBeanServer(ra.getMbeanServerDomainName());
            if(mbslist.isEmpty()) mbslist = MBeanServerFactory.findMBeanServer(null);
            if(mbslist.isEmpty())logger.log(Level.FINE,"Could not find any MBeanServer..Jinxed Server hell");
            Iterator<MBeanServer> iter = mbslist.iterator();
            return mbslist.iterator().next();
        } catch (Exception e) {
            logger.log(Level.WARNING,"Could not find any MBeanServer..:-"+e);
            return null;
        }
    }
}
