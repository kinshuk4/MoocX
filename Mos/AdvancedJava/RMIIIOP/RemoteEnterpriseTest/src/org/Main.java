package org;

import javax.naming.Context;
import javax.rmi.PortableRemoteObject;

import org.mk.training.iioptest.Customer;
import org.mk.training.iioptest.CustomerService;

public class Main {
	public static void main(String[] args) {
		try {
			Context jndiContext = getInitialContext();
			Object ref = jndiContext.lookup("org.mk.training.iioptest.CustomerService");
			CustomerService cs = (CustomerService) PortableRemoteObject.narrow(
					ref, CustomerService.class);
            Customer cust=cs.getCustomer("Some","one");
			System.out.println("Returned Customer"+cust);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static Context getInitialContext()
			throws javax.naming.NamingException {
		return new javax.naming.InitialContext();
	}

}