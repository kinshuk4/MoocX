package com.misc.beans;

import java.util.List;

public interface StandardEmployeeMBean {
	public List<StandardAddress> getBillingAddress();
	public void setBillingAddress(List<StandardAddress> billingAddress);
	public void setFirstName(String firstName);
	public String getLastName() ;
	public void setLastName(String lastName);
	public boolean isManager() ;
	//public void setManager(boolean manager);
	public long getSalary() ;
	public void setSalary(long salary);
	public float getTax() ;
	public void setTax(float tax);
	public void reset();

}
