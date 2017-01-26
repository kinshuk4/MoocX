package com.advanceddynamicloading.withoutmlet;

import java.io.IOException;
import java.util.Set;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.InvalidAttributeValueException;
import javax.management.ListenerNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.NotCompliantMBeanException;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.QueryExp;
import javax.management.ReflectionException;

public class MBeanServerAdapter implements MBeanServerAdapterMBean {
	private MBeanServer myMBeanServer=null;
	
	public MBeanServerAdapter(MBeanServer myMBeanServer) {
		super();
		this.myMBeanServer = myMBeanServer;
	}

	public void addNotificationListener(ObjectName arg0,
			NotificationListener arg1, NotificationFilter arg2, Object arg3)
			throws InstanceNotFoundException, IOException {
		myMBeanServer.addNotificationListener(arg0,arg1, arg2, arg3);

	}

	public void addNotificationListener(ObjectName arg0, ObjectName arg1,
			NotificationFilter arg2, Object arg3)
			throws InstanceNotFoundException, IOException {
		myMBeanServer.addNotificationListener(arg0, arg1,arg2, arg3);


	}
	public void addNotificationListener(ObjectName arg0, ObjectName arg1)
			throws InstanceNotFoundException, IOException {
		myMBeanServer.addNotificationListener(arg0, arg1,null, null);


	}


	public ObjectInstance createMBean(String arg0, ObjectName arg1)
			throws ReflectionException, InstanceAlreadyExistsException,
			MBeanRegistrationException, MBeanException,
			NotCompliantMBeanException, IOException {
		
		return myMBeanServer.createMBean(arg0, arg1);
	}

	public ObjectInstance createMBean(String arg0, ObjectName arg1,
			ObjectName arg2) throws ReflectionException,
			InstanceAlreadyExistsException, MBeanRegistrationException,
			MBeanException, NotCompliantMBeanException,
			InstanceNotFoundException, IOException {
		return myMBeanServer.createMBean(arg0, arg1, arg2);
	}

	public ObjectInstance createMBean(String arg0, ObjectName arg1,
			Object[] arg2, String[] arg3) throws ReflectionException,
			InstanceAlreadyExistsException, MBeanRegistrationException,
			MBeanException, NotCompliantMBeanException, IOException {
		return myMBeanServer.createMBean(arg0, arg1, arg2, arg3);
	}

	public ObjectInstance createMBean(String arg0, ObjectName arg1,
			ObjectName arg2, Object[] arg3, String[] arg4)
			throws ReflectionException, InstanceAlreadyExistsException,
			MBeanRegistrationException, MBeanException,
			NotCompliantMBeanException, InstanceNotFoundException, IOException {
		return myMBeanServer.createMBean(arg0, arg1, arg2, arg3, arg4);
	}

	public Object getAttribute(ObjectName arg0, String arg1)
			throws MBeanException, AttributeNotFoundException,
			InstanceNotFoundException, ReflectionException, IOException {
		return myMBeanServer.getAttribute(arg0, arg1);
	}

	public AttributeList getAttributes(ObjectName arg0, String[] arg1)
			throws InstanceNotFoundException, ReflectionException, IOException {
		return myMBeanServer.getAttributes(arg0, arg1);
	}

	public String getDefaultDomain() throws IOException {
		// TODO Auto-generated method stub
		return myMBeanServer.getDefaultDomain();
	}

	public String[] getDomains() throws IOException {
		// TODO Auto-generated method stub
		return myMBeanServer.getDomains();
	}

	public Integer getMBeanCount() throws IOException {
		// TODO Auto-generated method stub
		return myMBeanServer.getMBeanCount();
	}

	public MBeanInfo getMBeanInfo(ObjectName arg0)
			throws InstanceNotFoundException, IntrospectionException,
			ReflectionException, IOException {
		return myMBeanServer.getMBeanInfo(arg0);
	}

	public ObjectInstance getObjectInstance(ObjectName arg0)
			throws InstanceNotFoundException, IOException {
		return myMBeanServer.getObjectInstance(arg0);
	}

	public Object invoke(ObjectName arg0, String arg1, Object[] arg2,
			String[] arg3) throws InstanceNotFoundException, MBeanException,
			ReflectionException, IOException {
		return myMBeanServer.invoke(arg0, arg1, arg2, arg3);
	}

	public boolean isInstanceOf(ObjectName arg0, String arg1)
			throws InstanceNotFoundException, IOException {
		return myMBeanServer.isInstanceOf(arg0, arg1);
	}

	public boolean isRegistered(ObjectName arg0) throws IOException {
		return myMBeanServer.isRegistered(arg0);
	}

	public Set<ObjectInstance> queryMBeans(ObjectName arg0, QueryExp arg1)
			throws IOException {
		return myMBeanServer.queryMBeans(arg0, arg1);
	}

	public Set<ObjectName> queryNames(ObjectName arg0, QueryExp arg1)
			throws IOException {
		return myMBeanServer.queryNames(arg0, arg1);
	}

	public void removeNotificationListener(ObjectName arg0, ObjectName arg1)
			throws InstanceNotFoundException, ListenerNotFoundException,
			IOException {
		myMBeanServer.removeNotificationListener(arg0, arg1);

	}

	public void removeNotificationListener(ObjectName arg0,
			NotificationListener arg1) throws InstanceNotFoundException,
			ListenerNotFoundException, IOException {
		myMBeanServer.removeNotificationListener(arg0, arg1);

	}

	public void removeNotificationListener(ObjectName arg0, ObjectName arg1,
			NotificationFilter arg2, Object arg3)
			throws InstanceNotFoundException, ListenerNotFoundException,
			IOException {
		myMBeanServer.removeNotificationListener(arg0, arg1, arg2, arg3);

	}

	public void removeNotificationListener(ObjectName arg0,
			NotificationListener arg1, NotificationFilter arg2, Object arg3)
			throws InstanceNotFoundException, ListenerNotFoundException,
			IOException {
		myMBeanServer.removeNotificationListener(arg0, arg1, arg2, arg3);

	}

	public void setAttribute(ObjectName arg0, Attribute arg1)
			throws InstanceNotFoundException, AttributeNotFoundException,
			InvalidAttributeValueException, MBeanException,
			ReflectionException, IOException {
		myMBeanServer.setAttribute(arg0, arg1);

	}

	public AttributeList setAttributes(ObjectName arg0, AttributeList arg1)
			throws InstanceNotFoundException, ReflectionException, IOException {
		
		return myMBeanServer.setAttributes(arg0, arg1);
	}

	public void unregisterMBean(ObjectName arg0)
			throws InstanceNotFoundException, MBeanRegistrationException,
			IOException {
		myMBeanServer.unregisterMBean(arg0);

	}

}
