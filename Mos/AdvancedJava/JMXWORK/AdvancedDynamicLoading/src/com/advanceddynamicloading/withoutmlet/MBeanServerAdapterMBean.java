package com.advanceddynamicloading.withoutmlet;

import java.io.IOException;

import javax.management.InstanceNotFoundException;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;

public interface MBeanServerAdapterMBean extends MBeanServerConnection {
	public void addNotificationListener(ObjectName arg0, ObjectName arg1)
	throws InstanceNotFoundException, IOException ;
}
