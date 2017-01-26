/**
* SampleServlet.java
* Copyright 2006 Sun Microsystems, Inc. All rights reserved.
* SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*/

import java.io.*;
import java.lang.management.ManagementFactory;
import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * Register Sample Mbean in platform MBeanServer.
 */
public class SampleServlet extends HttpServlet {
    
    /**
     * The servlet is inited when the web application is deployed.
     * MBeans are the deployed.
     */
    public void init() {
        try {
            //Registering Sample MBean in MBeanServer
            ManagementFactory.getPlatformMBeanServer().registerMBean(new Sample(), 
                  new ObjectName(":type=Sample")); 
        } catch (Exception ex) {
            ex.printStackTrace();
        }
     }
     
    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
       //No request handled by this servlet
    }
}
