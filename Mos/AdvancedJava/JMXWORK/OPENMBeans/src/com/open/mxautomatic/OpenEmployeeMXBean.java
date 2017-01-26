package com.open.mxautomatic;

import java.util.List;
import java.util.Map;

import javax.management.openmbean.TabularData;

public interface OpenEmployeeMXBean {
	public OpenAddress getAddress();
	public void setAddress(OpenAddress address);
	public String getFirstName() ;
	public void setFirstName(String firstName);
	public String getLastName() ;
	public void setLastName(String lastName);
	public boolean isManager() ;
	public void setManager(boolean manager);
	public long getSalary() ;
	public void setSalary(long salary);
	public float getTax() ;
	public void setTax(float tax);
	public void reset();
	public List<String> getProjectnames();
	public void setProjectnames(List<String> projectnames);
	public List<OpenAddress> getOnshorelocations();
	public void setOnshorelocations(List<OpenAddress> onshorelocations) ;
	public Map<String, String> getProjectnamesrolesmap();
	public void setProjectnamesrolesmap(Map<String, String> projectnamesrolesmap);
	public Map<String, OpenAddress> getProjectnameslocationsmap();
	public void setProjectnameslocationsmap(
			Map<String, OpenAddress> projectnameslocationsmap);

}
