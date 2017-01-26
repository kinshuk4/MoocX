package com.standard.notification;

import java.lang.management.ManagementFactory;

import javax.management.AttributeChangeNotification;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.Notification;
import javax.management.NotificationFilter;
import javax.management.NotificationFilterSupport;
import javax.management.NotificationListener;
import javax.management.ObjectName;

public class NotificationStandardMBeanMain {

	/**
	 * @param args
	 * @throws  
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

		// Construct the ObjectName for the MBean we will register
		ObjectName empname = new ObjectName("com.NotificationStandardMBeanMain:type=Employee");

		// Create the Employee and Address MBean
		
		ObjectName addname = new ObjectName("com.NotificationStandardMBeanMain:type=Address");
		StandardAddress add=new StandardAddress("MGRoad","Bangalore");
		
		StandardEmployee emp = new StandardEmployee("Cheeky","Monkey",1000,5.15f,false,add);

		
		mbs.registerMBean(emp, empname);
		mbs.registerMBean(add, addname);
		
		Thread.sleep(30000);
		System.out.println("Waiting 20000 ...");
		emp.setSalary(2000);
		mbs.addNotificationListener(empname, new NotificationListenerImpl(), new NotificationFilterImpl(), null);
		
		System.out.println("Waiting forever...");
		Thread.sleep(Long.MAX_VALUE);
	    

	}

}

class NotificationListenerImpl implements NotificationListener{

	public void handleNotification(Notification arg0, Object arg1) {
		System.out.println("handleNotification :: called");
		System.out.println("handleNotification :: called :: Message :: "+arg0.getMessage());
		System.out.println("handleNotification :: called :: SequenceNumber :: "+arg0.getSequenceNumber());
		System.out.println("handleNotification :: called :: getType() :: "+arg0.getType());
		System.out.println("handleNotification :: called :: "+arg0.getUserData());
	}
}
class NotificationFilterImpl implements NotificationFilter{
	
	public boolean isNotificationEnabled(Notification arg0) {
		if (arg0!=null&& arg0 instanceof AttributeChangeNotification) {
			AttributeChangeNotification attr = (AttributeChangeNotification) arg0;
			System.out.println("condition :: "+(((Long)attr.getNewValue()).compareTo((Long)attr.getOldValue()) >  0));
			return ((Long)attr.getNewValue()).compareTo((Long)attr.getOldValue()) >  0;
		}
		return false;
	}
	
}