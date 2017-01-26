package com.standard.notification;

import javax.management.AttributeChangeNotification;
import javax.management.MBeanNotificationInfo;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;

public class StandardEmployee extends NotificationBroadcasterSupport implements StandardEmployeeMBean{
	private String lastName;
	private String firstName;
	private long salary;
	private float tax;
	private boolean manager;
	private StandardAddress address;
	
	private long sequenceNumber=0;
	
	public StandardAddress getAddress() {
		return address;
	}
	public void setAddress(StandardAddress address) {
		this.address = address;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public boolean isManager() {
		return manager;
	}
	public void setManager(boolean manager) {
		this.manager = manager;
	}
	public long getSalary() {
		return salary;
	}
	public void setSalary(long salary) {
		long oldsalary=this.salary;
		this.salary = salary;
		System.out.println("salary amount is size now " + this.salary); 
		 
        Notification n = 
            new AttributeChangeNotification(this, 
					    sequenceNumber++, 
					    System.currentTimeMillis(), 
					    "salary changed", 
					    "salary amount", 
					    "long", 
					    new Long(oldsalary), 
					    new Long(this.salary)); 
        sendNotification(n);
 
	}
	public float getTax() {
		return tax;
	}
	public void setTax(float tax) {
		this.tax = tax;
	}
	public StandardEmployee(String lastName, String firstName, long salary, float tax, boolean manager, StandardAddress address) {
		super();
		// TODO Auto-generated constructor stub
		this.lastName = lastName;
		this.firstName = firstName;
		this.salary = salary;
		this.tax = tax;
		this.manager = manager;
		this.address = address;
	}
	public void reset() {
		lastName=null;
		firstName=null;
		salary=0;
		tax=0.0f;
		address.reset();
		
	}
	public MBeanNotificationInfo[] getNotificationInfo() {
		String[] types = new String[] { 
	            AttributeChangeNotification.ATTRIBUTE_CHANGE 
	        }; 
        String name = AttributeChangeNotification.class.getName(); 
        String description = "An attribute of this MBean has changed"; 
        MBeanNotificationInfo info = 
            new MBeanNotificationInfo(types, name, description); 
        return new MBeanNotificationInfo[] {info}; 
	}
}
