package org.mk.training.iioptest;

import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.mk.training.iioptest.CustomerService;
@Stateless
@Remote(CustomerService.class)
public class CustomerServiceBean {

	public String echoString(String str) {
		System.out.println("CustomerServiceBean().echoString():: "+str);
		return "echoed:: "+str;
	}

	public Customer getCustomer(String lastName, String firstName) {
		Customer cust=null;
		if (firstName!=null || lastName!=null) {
			cust=new Customer(lastName,firstName);
		}else{
			cust=new Customer("Some other","person");
		}
		return cust;
	}
}
