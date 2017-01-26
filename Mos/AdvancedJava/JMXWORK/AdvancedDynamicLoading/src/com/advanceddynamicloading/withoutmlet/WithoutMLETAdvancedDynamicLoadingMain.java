package com.advanceddynamicloading.withoutmlet;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.loading.MLet;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;

public class WithoutMLETAdvancedDynamicLoadingMain {

	/* 0. Connect using "service:jmx:rmi:///jndi/rmi://localhost:8888/jmxrmi"
	 * 1. Load the load the beans.jar by executing loaderbean.addURL() from console.
         * http://localhost:8080/files/repository/beans.jar
	 * 2. (If you want to configure Mail Notification)Load the load the mail-1.4.jar
	 *  by executing loaderbean.addURL() from console.
         * http://localhost:8080/files/repository/mail-1.4.jar
	 * 3.call mbs.createMbean(clasname,obnameforbean,obnameforloader) from console;
	 * (com.misc.beans.StandardEmployee,anything(com.dynamic:Type=StandardEmployee),com.advanceddynamicloading:type=LoaderBean);
	 * 4.call mbs.createMbean(clasname,obnameforbean,obnameforloader) from console;
	 * (org.mk.training.mail.SMTPSimpleSender,anything(com.dynamic:Type=SMTPListener),com.advanceddynamicloading:type=LoaderBean);
	 * 5. configure mail listener with mail parameters.
	 * 6. configure mail listener as notification listener for employee through mbs.addNotificationListener(name,listenername)
	 * (com.dynamic:Type=StandardEmployee,com.dynamic:Type=SMTPListener) 
	 * 7. change employee's salary
	 */
	public static void main(String[] args) throws Exception{
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		MLet loaderbean=new MLet();
		ObjectName lbName = new ObjectName("com.advanceddynamicloading:type=LoaderBean");
		mbs.registerMBean(loaderbean, lbName);
		ObjectName mbscName = new ObjectName("com.advanceddynamicloading:type=MBeanServerAdapter");
		mbs.registerMBean(new MBeanServerAdapter(mbs), mbscName);
		Thread.sleep(Long.MAX_VALUE);
	}

}
