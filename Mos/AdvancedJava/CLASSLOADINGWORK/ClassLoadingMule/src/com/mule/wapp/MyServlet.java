package com.mule.wapp;

import com.mule.common.Employee;
import com.mule.eapp.SessionInterface;

public class MyServlet {
	private SessionInterface bean=null;
	public void service(){
		Employee e=bean.getEmployee("CHEEKY MONKEY");
		System.out.println(e);
	}
	public SessionInterface getBean() {
		return bean;
	}
	public void setBean(Object bean) {
		this.bean = (SessionInterface)bean;
	}
	public MyServlet() {
		super();
		System.out.println("Employee(MyServlet) :: "+Employee.class.getName()+"("+Employee.class.hashCode()+") :: ClassLoaders :: "+Employee.class.getClassLoader());
		
	}
	
}
