package com.dynamic;

import java.lang.reflect.Constructor;
import java.util.Iterator;

import javax.management.Attribute;
import javax.management.AttributeChangeNotification;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.DynamicMBean;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanConstructorInfo;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;
import javax.management.NotificationBroadcasterSupport;
import javax.management.ReflectionException;
import javax.management.RuntimeOperationsException;

import com.util.ObjectAnalyzer;

public class DynamicEmployee extends NotificationBroadcasterSupport implements DynamicMBean{
	private String lastName;
	private String firstName;
	private long salary;
	private float tax;
	private boolean manager;
	private DynamicAddress address;
	
	public DynamicAddress getAddress() {
		return address;
	}
	public void setAddress(DynamicAddress address) {
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
		AttributeChangeNotification acn =
            new AttributeChangeNotification(this,
                                            0,
                                            0,
                                            "salary change",
                                            "salary",
                                            "long",
                                            new Long(oldsalary),
                                            new Long(this.salary));
		sendNotification(acn);
	}
	public float getTax() {
		return tax;
	}
	public void setTax(float tax) {
		this.tax = tax;
	}
	public DynamicEmployee(String lastName, String firstName, long salary, float tax, boolean manager, DynamicAddress address) {
		super();
		// TODO Auto-generated constructor stub
		this.buildDynamicMBeanInfo();
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
		AttributeChangeNotification acn =
            new AttributeChangeNotification(this,
                                            0,
                                            0,
                                            "employee value reset",
                                            "all values",
                                            "mixed types",
                                            null,
                                            null);
		sendNotification(acn);
		
	}
	public String toString() {
		// TODO Auto-generated method stub
		return ObjectAnalyzer.genericToString(this);
	}
	public Object getAttribute(String attribute_name) throws AttributeNotFoundException, MBeanException, ReflectionException {
		if (attribute_name == null) {
            throw new RuntimeOperationsException(
                  new IllegalArgumentException("Attribute name cannot be null"),
                  "Cannot invoke a getter of " + this.getClass().getName() +
                  " with null attribute name");
        }
        if (attribute_name.equals("lastName")) {
            return getLastName();
        } 
        if (attribute_name.equals("firstName")) {
            return getFirstName();
        }
        if (attribute_name.equals("salary")) {
            return new Long(getSalary());
        }
        if (attribute_name.equals("tax")) {
            return new Float(getTax());
        }
        if (attribute_name.equals("manager")) {
            return new Boolean(isManager());
        }
        if (attribute_name.equals("address")) {
            return getAddress();
        } 
        throw new AttributeNotFoundException("Cannot find " +
                attribute_name +
                " attribute in " +
                this.getClass().getName());

	}
	public void setAttribute(Attribute attribute) throws AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException {
		if (attribute == null) {
            throw new RuntimeOperationsException(
                  new IllegalArgumentException("Attribute cannot be null"), 
                  "Cannot invoke a setter of " + this.getClass().getName() +
                  " with null attribute");
        }
        String name = attribute.getName();
        Object value = attribute.getValue();
        if (name == null) {
            throw new RuntimeOperationsException(
                  new IllegalArgumentException("Attribute name cannot be null"),
                  "Cannot invoke the setter of " + this.getClass().getName() +
                  " with null attribute name");
        }
        if (name.equals("salary")) {
            if (value == null) {
                throw new InvalidAttributeValueException(
                              "Cannot set attribute " + name + " to null");
            }
            // if non null value, make sure it is assignable to the attribute
            else {
                try {
                    if (Class.forName("java.lang.Long").isAssignableFrom(
                                                          value.getClass())) {
                        setSalary(((Long )value).longValue());
                    } else {
                        throw new InvalidAttributeValueException(
                                  "Cannot set attribute " + name + " to a " + 
                                  value.getClass().getName() +
                                  " object, String expected");
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        else if (name.equals("lastName")||name.equals("firstName")||name.equals("tax")||name.equals("manager")||name.equals("address")) {
            throw new AttributeNotFoundException(
                  "Cannot set attribute " + name + " because it is read-only");
        }
        // unrecognized attribute name:
        else {
            throw new AttributeNotFoundException("Attribute " + name +
                                                 " not found in " +
                                                 this.getClass().getName());
        }
	}
	public AttributeList getAttributes(String[] attributeNames) {
		// Check attributeNames is not null to avoid NullPointerException
        // later on
        if (attributeNames == null) {
            throw new RuntimeOperationsException(
                new IllegalArgumentException("attributeNames[] cannot be null"),
                "Cannot invoke a getter of " + this.getClass().getName());
        }
        AttributeList resultList = new AttributeList();
        // If attributeNames is empty, return an empty result list
        //
        if (attributeNames.length == 0)
            return resultList;
        // Build the result attribute list
        for (int i = 0 ; i < attributeNames.length ; i++) {
            try {        
                Object value = getAttribute((String) attributeNames[i]);     
                resultList.add(new Attribute(attributeNames[i],value));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultList;
	}
	public AttributeList setAttributes(AttributeList attributes) {
		// Check attributes is not null to avoid NullPointerException later on
        if (attributes == null) {
            throw new RuntimeOperationsException(
                      new IllegalArgumentException(
                                 "AttributeList attributes cannot be null"),
                      "Cannot invoke a setter of " + this.getClass().getName());
        }
        AttributeList resultList = new AttributeList();
        // If attributeNames is empty, nothing more to do
        if (attributes.isEmpty())
            return resultList;
        // For each attribute, try to set it and add to the result list if
        // successfull
        for (Iterator i = attributes.iterator(); i.hasNext();) {
            Attribute attr = (Attribute) i.next();
            try {
                setAttribute(attr);
                String name = attr.getName();
                Object value = getAttribute(name); 
                resultList.add(new Attribute(name,value));
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        return resultList;
	}
	public Object invoke(String operationName, Object[] params, String[] signatures) throws MBeanException, ReflectionException {
		// Check operationName is not null to avoid NullPointerException
        // later on
		System.out.println("signatures.length :: "+signatures.length);
		for (int S = 0; S < signatures.length; S++) {
			
			System.out.println(signatures[S]);
		}
        if (operationName == null) {
            throw new RuntimeOperationsException(
                 new IllegalArgumentException("Operation name cannot be null"), 
                 "Cannot invoke a null operation in " + this.getClass().getName());
        }
        // Check for a recognized operation name and call the corresponding
        // operation
        if (operationName.equals("reset")) {
            reset();
            return null;
        } else { 
            // Unrecognized operation name
            //
            throw new ReflectionException(
                                new NoSuchMethodException(operationName), 
                                "Cannot find the operation " + operationName +
                                " in " + this.getClass().getName());
        }
    }
	public MBeanInfo getMBeanInfo() {
		return dMBeanInfo;
	}
	private void buildDynamicMBeanInfo(){
		dAttributes[0] =
            new MBeanAttributeInfo("lastName",
                                   "java.lang.String",
                                   "last name",
                                   true,
                                   false,
                                   false);
        dAttributes[1] =
            new MBeanAttributeInfo("firstName",
                                   "java.lang.String",
                                   "first name",
                                   true,
                                   false,
                                   false);
        dAttributes[2] =
            new MBeanAttributeInfo("salary",
                                   "java.lang.Long",
                                   "salary",
                                   true,
                                   true,
                                   false);
        dAttributes[3] =
            new MBeanAttributeInfo("tax",
                                   "java.lang.Float",
                                   "tax",
                                   true,
                                   false,
                                   false);
        dAttributes[4] =
            new MBeanAttributeInfo("manager",
                                   "java.lang.Boolean",
                                   "manager",
                                   true,
                                   false,
                                   true);
        dAttributes[5] =
            new MBeanAttributeInfo("address",
                                   "java.lang.Address",
                                   "address",
                                   true,
                                   false,
                                   false);
        Constructor[] constructors = this.getClass().getConstructors();
        dConstructors[0] =
            new MBeanConstructorInfo("Constructs a " +
                                     "SimpleDynamic object",
                                     constructors[0]);
        MBeanParameterInfo[] params = null;
        dOperations[0] =
            new MBeanOperationInfo("reset",
                                   "reset State and NbChanges " +
                                   "attributes to their initial values",
                                   params , 
                                   "void", 
                                   MBeanOperationInfo.ACTION);
        dNotifications[0] =
            new MBeanNotificationInfo(
            new String[] { AttributeChangeNotification.ATTRIBUTE_CHANGE },
            AttributeChangeNotification.class.getName(),
            "This notification is emitted when the reset() method is called.");
        dNotifications[1] =
            new MBeanNotificationInfo(
            new String[] { AttributeChangeNotification.ATTRIBUTE_CHANGE },
            AttributeChangeNotification.class.getName(),
            "This notification is emitted when is changed.");

        dMBeanInfo = new MBeanInfo(this.getClass().getName(),
                                   "Employee Description",
                                   dAttributes,
                                   dConstructors,
                                   dOperations,
                                   dNotifications);

	}
	private MBeanAttributeInfo[] dAttributes =
        new MBeanAttributeInfo[6];
    private MBeanConstructorInfo[] dConstructors =
        new MBeanConstructorInfo[1];
    private MBeanNotificationInfo[] dNotifications =
        new MBeanNotificationInfo[2];
    private MBeanOperationInfo[] dOperations =
        new MBeanOperationInfo[1];
    private MBeanInfo dMBeanInfo = null;
}
