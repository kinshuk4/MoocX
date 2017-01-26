package com.model;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import javax.management.AttributeChangeNotification;
import javax.management.Descriptor;
import javax.management.IntrospectionException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanConstructorInfo;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;
import javax.management.RuntimeOperationsException;
import javax.management.modelmbean.DescriptorSupport;
import javax.management.modelmbean.ModelMBeanAttributeInfo;
import javax.management.modelmbean.ModelMBeanConstructorInfo;
import javax.management.modelmbean.ModelMBeanInfo;
import javax.management.modelmbean.ModelMBeanInfoSupport;
import javax.management.modelmbean.ModelMBeanNotificationInfo;
import javax.management.modelmbean.ModelMBeanOperationInfo;
import javax.management.modelmbean.XMLParseException;

public class ModelMBeanInfoBuilder {
	private ModelMBeanAttributeInfo[] dAttributes =  new ModelMBeanAttributeInfo[6];
    private ModelMBeanConstructorInfo[] dConstructors = new ModelMBeanConstructorInfo[1];
    private ModelMBeanNotificationInfo[] dNotifications =  new ModelMBeanNotificationInfo[2];
    private ModelMBeanOperationInfo[] dOperations =    new ModelMBeanOperationInfo[7];
    private ModelMBeanInfo dMBeanInfo = null;
    private Descriptor desattrmap[]=new DescriptorSupport[6];
    
    
    public ModelMBeanInfo buildDynamicMBeanInfo() throws SecurityException, NoSuchMethodException, RuntimeOperationsException, MBeanException, XMLParseException, IntrospectionException{
    	Method getLastName=ModelEmployee.class.getMethod("getLastName",null);
    	Method getFirstName=ModelEmployee.class.getMethod("getFirstName",null);
    	Method getSalary=ModelEmployee.class.getMethod("getSalary",null);
    	Method getTax=ModelEmployee.class.getMethod("getTax",null);
    	Method isManager=ModelEmployee.class.getMethod("isManager",null);
    	Method getAddress=ModelEmployee.class.getMethod("getAddress",null);
    	Method setSalary=ModelEmployee.class.getMethod("setSalary",long.class);
    	Method reset=ModelEmployee.class.getMethod("reset",null);
    	desattrmap[0]=new DescriptorSupport(new String[]{"name","descriptorType","GetMethod"},
    			new String[]{"lastName","attribute","getLastName"});
		dAttributes[0] =
            new ModelMBeanAttributeInfo("lastName",
                                   "java.lang.String",
                                   "last name",
                                   true,
                                   false,
                                   false,desattrmap[0]);
		desattrmap[1]=new DescriptorSupport(new String[]{"name","descriptorType","GetMethod"},
				new String[]{"firstName","attribute","getFirstName"});
		dAttributes[1] =
            new ModelMBeanAttributeInfo("firstName",
                                   "java.lang.String",
                                   "first name",
                                   true,
                                   false,
                                   false,desattrmap[1]);
		desattrmap[2]=new DescriptorSupport(new String[]{"name","descriptorType","GetMethod","SetMethod"},
				new String[]{"salary","attribute","getSalary","setSalary"});
        dAttributes[2] =
            new ModelMBeanAttributeInfo("salary",
                                   "salary",
                                   ModelEmployee.class.getMethod("getSalary",null),
                                   ModelEmployee.class.getMethod("setSalary",long.class),desattrmap[2]);
        desattrmap[3]=new DescriptorSupport(new String[]{"name","descriptorType","GetMethod"},
        		new String[]{"tax","attribute","getTax"});
        dAttributes[3] =
            new ModelMBeanAttributeInfo("tax",
                                   "java.lang.Float",
                                   "tax",
                                   true,
                                   false,
                                   false,desattrmap[3]);
        desattrmap[4]=new DescriptorSupport(new String[]{"name","descriptorType","GetMethod"},
        		new String[]{"manager","attribute","isManager"});
        dAttributes[4] =
            new ModelMBeanAttributeInfo("manager",
                                   "java.lang.Boolean",
                                   "manager",
                                   true,
                                   false,
                                   true,desattrmap[4]);
        desattrmap[5]=new DescriptorSupport(new String[]{"name","descriptorType","GetMethod"},
        		new String[]{"address","attribute","getAddress"});
        dAttributes[5] =
            new ModelMBeanAttributeInfo("address",
                                   "com.model.ModelAddress",
                                   "address",
                                   true,
                                   false,
                                   false,desattrmap[5]);
        Constructor[] constructors = this.getClass().getConstructors();
        dConstructors[0] =
            new ModelMBeanConstructorInfo("Constructs a " +
                                     "SimpleDynamic object",
                                     constructors[0]);
         dOperations[0] =
            new ModelMBeanOperationInfo("reset",reset);
         dOperations[1] =
             new ModelMBeanOperationInfo("getLastName",getLastName);
         dOperations[2] =
             new ModelMBeanOperationInfo("getFirstName",getFirstName);
         dOperations[3] =
             new ModelMBeanOperationInfo("getSalary",getSalary);
         dOperations[4] =
             new ModelMBeanOperationInfo("getTax",getTax);
         dOperations[5] =
             new ModelMBeanOperationInfo("isManager",isManager);
         dOperations[6] =
             new ModelMBeanOperationInfo("getAddress",getAddress);
         /*dOperations[7] =
             new ModelMBeanOperationInfo("setSalary",setSalary);*/
        dNotifications[0] =
            new ModelMBeanNotificationInfo(
            new String[] { AttributeChangeNotification.ATTRIBUTE_CHANGE },
            AttributeChangeNotification.class.getName(),
            "This notification is emitted when the reset() method is called.");
        dNotifications[1] =
            new ModelMBeanNotificationInfo(
            new String[] { AttributeChangeNotification.ATTRIBUTE_CHANGE },
            AttributeChangeNotification.class.getName(),
            "This notification is emitted when is changed.");
        dMBeanInfo = new ModelMBeanInfoSupport(this.getClass().getName(),
                                   "Employee Description",
                                   dAttributes,
                                   dConstructors,
                                   dOperations,
                                   dNotifications);
        return dMBeanInfo;
	}

}
