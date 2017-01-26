/*
 * Copyright 2006-2007 Sun Microsystems, Inc.  All Rights Reserved.
 * SUN PROPRIETARY/CONFIDENTIAL.  Use is subject to license terms.
 */

package simplejdk6ws;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.MalformedURLException;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;

public class Main {
    public static void main(String[] args) throws MalformedURLException, IOException {
        JMXConnectorServer server =
                JMXConnectorServerFactory.
                newJMXConnectorServer(new JMXServiceURL("service:jmx:ws:" +
                "//localhost:8080/jmxws"), null,
                ManagementFactory.getPlatformMBeanServer());
        server.start();
        System.out.println(server.getClass().getName());
        System.out.println("JSR 262 ConnectorServer is ready to serve on http://localhost:8080/jmxws");
    }
    
}
