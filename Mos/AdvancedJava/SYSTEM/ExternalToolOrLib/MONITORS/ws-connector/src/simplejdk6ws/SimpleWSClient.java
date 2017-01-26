/*
 * Copyright 2006-2007 Sun Microsystems, Inc.  All Rights Reserved.
 * SUN PROPRIETARY/CONFIDENTIAL.  Use is subject to license terms.
 */

package simplejdk6ws;

import java.util.HashMap;
import java.util.Map;
import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import javax.xml.ws.BindingProvider;

public class SimpleWSClient {
    
    public static void main(String[] args) throws Exception {
        JMXServiceURL url = new JMXServiceURL("service:jmx:ws://localhost:8080/jmxws");
        
        Map<String, Object> env = new HashMap<String, Object>();
        env.put(BindingProvider.USERNAME_PROPERTY, "Ron");
        env.put(BindingProvider.PASSWORD_PROPERTY, "noR");
        
        JMXConnector connector = JMXConnectorFactory.connect(url, env);
        System.out.println(env);
		;
		System.out.println(System.getProperty("jmx.remote.protocol.provider.pkgs"));
        //Get the MBeanServerConnection
        MBeanServerConnection mbsc = connector.getMBeanServerConnection();
        String domain = mbsc.getDefaultDomain();
        System.out.println("Default Domain = "+ domain);
    }
    
}
