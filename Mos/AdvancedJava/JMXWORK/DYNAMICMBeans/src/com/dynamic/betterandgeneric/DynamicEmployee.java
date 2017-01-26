package com.dynamic.betterandgeneric;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
		MBeanAttributeInfo mbai[]=this.getMBeanInfo().getAttributes();
		String operationName=null;
		for (int i = 0; i < mbai.length; i++) {
			if (mbai[i].getName().equals(attribute_name)&& mbai[i].isIs()) {
				operationName=new StringBuffer (attribute_name).insert(0, "is").toString();
				break;
			}
			else if(mbai[i].getName().equals(attribute_name)&& !( mbai[i].isIs())){
				operationName=new StringBuffer (attribute_name).insert(0, "get").toString();
				break;
			}
		}
		return this.invoke(operationName, null, null); 
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
        Object params[]={value};
        String signature[]=new String[1];
        MBeanAttributeInfo mbai[]=this.getMBeanInfo().getAttributes();
        for (int i = 0; i < mbai.length; i++) {
			if (mbai[i].getType()!=null && mbai[i].getName().equals(name)) {
				if (!mbai[i].isWritable()) throw new AttributeNotFoundException("setAttribute failed: "
				+ name + " is not writable ");
				//primitives have to be treated differently
				Class primvalue=primitiveClassMap.get(mbai[i].getType());
				if (primvalue!=null) {
					signature[0]=primvalue.getName();
					System.out.println("primvalue.getName() :: "+primvalue.getName());
				}else{
					signature[0]=mbai[i].getType();
				}
			}
		}
        String operationName=new StringBuffer (name).insert(0, "set").toString();
        System.out.println("operationName :: "+operationName);
        this.invoke(operationName, params, signature);
    }
	public Object invoke(String operationName, Object[] params, String[] signatures) throws MBeanException, ReflectionException {
		// Check operationName is not null to avoid NullPointerException
        // later on
		if (operationName == null) {
            throw new RuntimeOperationsException(
                 new IllegalArgumentException("Operation name cannot be null"), 
                 "Cannot invoke a null operation in " + this.getClass().getName());
        }
		Class s[]=null;
		if (signatures!=null) {
			s=new Class[signatures.length];
			for (int i = 0; i < signatures.length; i++) {
				try {
					s[i]=primitiveClassMap.get(signatures[i]);/*primitive*/
					if (s[i]==null) {
						s[i]=Class.forName(signatures[i]);
					}
					System.out.println(" :: "+signatures[i]+" :: "+s[i].getName());
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					
				}
			}
		}
		
		Method m=null;
        try {
			 m=this.getClass().getMethod(operationName,s);
		} catch (SecurityException e) {
			e.printStackTrace();
			throw new ReflectionException(e,"Method not accessible");
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			throw new ReflectionException(e,"No Such Method");
		}
        try {
			return m.invoke(this, params);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new ReflectionException(e,"Illegal Argument ");
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new ReflectionException(e,"Illegal Access");
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			throw new ReflectionException(e,"Some other DAMN Reason");
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
	public MBeanInfo getMBeanInfo() {
		return dMBeanInfo;
	}
	private void buildDynamicMBeanInfo(){
		dAttributes[0] =
            new MBeanAttributeInfo("LastName",
                                   "java.lang.String",
                                   "last name",
                                   true,
                                   false,
                                   false);
        dAttributes[1] =
            new MBeanAttributeInfo("FirstName",
                                   "java.lang.String",
                                   "first name",
                                   true,
                                   false,
                                   false);
        dAttributes[2] =
            new MBeanAttributeInfo("Salary",
                                   "long",
                                   "salary",
                                   true,
                                   true,
                                   false);
        dAttributes[3] =
            new MBeanAttributeInfo("Tax",
                                   "float",
                                   "tax",
                                   true,
                                   false,
                                   false);
        dAttributes[4] =
            new MBeanAttributeInfo("Manager",
                                   "boolean",
                                   "manager",
                                   true,
                                   false,
                                   true);
        dAttributes[5] =
            new MBeanAttributeInfo("Address",
                                   "com.dynamic.betterandgeneric.DynamicAddress",
                                   "address",
                                   true,
                                   true,
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
    
    private static final Class[] primitiveClasses = {
    	int.class, long.class, boolean.class, double.class,
    	float.class, short.class, byte.class, char.class,
    };
    private static final Map <String,Class> primitiveClassMap =new HashMap<String,Class>();
    static {
    	for (int i = 0; i < primitiveClasses.length; i++) {
    	    final Class c = primitiveClasses[i];
    	    primitiveClassMap.put(c.getName(), c);
    	}
    }
}
