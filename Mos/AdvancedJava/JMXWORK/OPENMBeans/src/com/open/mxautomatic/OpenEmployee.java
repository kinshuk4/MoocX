package com.open.mxautomatic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.OpenType;
import javax.management.openmbean.SimpleType;
import javax.management.openmbean.TabularData;
import javax.management.openmbean.TabularDataSupport;
import javax.management.openmbean.TabularType;

import com.util.ObjectAnalyzer;

public class OpenEmployee implements OpenEmployeeMXBean{
	private String lastName;
	private String firstName;
	private long salary;
	private float tax;
	private boolean manager;
	private OpenAddress address;
	private List <String>projectnames;
	private List <OpenAddress> onshorelocations;
	private Map <String,String> projectnamesrolesmap;
	private Map <String,OpenAddress> projectnameslocationsmap;
	
	public OpenEmployee(String lastName, String firstName, long salary, float tax, boolean manager, OpenAddress address, List<String> projectnames, List<OpenAddress> onshorelocations, Map<String, String> projectnamesrolesmap, Map<String, OpenAddress> projectnameslocationsmap) {
		super();
		// TODO Auto-generated constructor stub
		this.lastName = lastName;
		this.firstName = firstName;
		this.salary = salary;
		this.tax = tax;
		this.manager = manager;
		this.address = address;
		this.projectnames = projectnames;
		this.onshorelocations = onshorelocations;
		this.projectnamesrolesmap = projectnamesrolesmap;
		this.projectnameslocationsmap = projectnameslocationsmap;
	}
	public Map<String, String> getProjectnamesrolesmap() {
		return projectnamesrolesmap;
	}
	public void setProjectnamesrolesmap(Map<String, String> projectnamesrolesmap) {
		this.projectnamesrolesmap = projectnamesrolesmap;
	}
	public List<String> getProjectnames() {
		return projectnames;
	}
	public void setProjectnames(List<String>  projectnames) {
		this.projectnames = projectnames;
	}
	public OpenAddress getAddress() {
		return address;
	}
	public void setAddress(OpenAddress address) {
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
		this.salary = salary;
	}
	public float getTax() {
		return tax;
	}
	public void setTax(float tax) {
		this.tax = tax;
	}
	public List<OpenAddress> getOnshorelocations() {
		return onshorelocations;
	}
	public void setOnshorelocations(List<OpenAddress> onshorelocations) {
		this.onshorelocations = onshorelocations;
	}
	public Map<String, OpenAddress> getProjectnameslocationsmap() {
		return projectnameslocationsmap;
	}
	public void setProjectnameslocationsmap(
			Map<String, OpenAddress> projectnameslocationsmap) {
		this.projectnameslocationsmap = projectnameslocationsmap;
	}
	public void reset() {
		lastName=null;
		firstName=null;
		salary=0;
		tax=0.0f;
				
	}
	public String toString() {
		// TODO Auto-generated method stub
		return ObjectAnalyzer.genericToString(this);
	}
}
