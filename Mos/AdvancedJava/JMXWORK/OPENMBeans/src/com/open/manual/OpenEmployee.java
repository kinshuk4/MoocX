package com.open.manual;

import java.util.HashMap;
import java.util.Map;

import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.OpenType;
import javax.management.openmbean.SimpleType;

import com.util.ObjectAnalyzer;

public class OpenEmployee implements OpenEmployeeMBean{
	private String lastName;
	private String firstName;
	private long salary;
	private float tax;
	private boolean manager;
	private OpenAddress address;
	public CompositeData getAddress() {
		CompositeDataSupport cd =null;
		try {
			String typeName =
	            "java.util.Map<java.lang.String, java.lang.String>";
	        String[] keys =
	            new String[] {"street", "city"};
	        String[] keydescription =
	            new String[] {"street", "city"};
	        OpenType[] openTypesdescvalues =
	            new OpenType[] {SimpleType.STRING, SimpleType.STRING};
	        CompositeType rowType = new CompositeType(typeName, typeName, keys, keydescription, openTypesdescvalues);
	        Map<String, String> addmap=new HashMap<String, String>();
	        addmap.put("street",address.getStreet());
	        addmap.put("city",address.getCity());
	        cd =new CompositeDataSupport(rowType,addmap);
		} catch (OpenDataException e) {e.printStackTrace();}
        return cd ;
	}
	public void setAddress(CompositeData address) {
		//this.address = address;
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
	public OpenEmployee(String lastName, String firstName, long salary, float tax, boolean manager, OpenAddress address) {
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
	public String toString() {
		// TODO Auto-generated method stub
		return ObjectAnalyzer.genericToString(this);
	}
}
