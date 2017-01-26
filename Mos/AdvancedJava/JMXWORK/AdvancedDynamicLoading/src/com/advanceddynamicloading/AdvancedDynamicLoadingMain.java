package com.advanceddynamicloading;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.loading.MLet;

public class AdvancedDynamicLoadingMain {

	/**
	 * @param args
	 * @throws  
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		//MBeanServer mbs =MBeanServerFactory.createMBeanServer();
		MLet loaderbean=new MLet();
		ObjectName lbName = new ObjectName("com.advanceddynamicloading:type=LoaderBean");
		mbs.registerMBean(loaderbean, lbName);
		
		Thread.sleep(Long.MAX_VALUE);
		/*
		 * 1.Modify MLET.txt to provide class location
		 * 1.provide location to MLET.txt file in loader.getMBeansFromURL() method through the console;
		 *  file:/<ABSOLUTE LOCATION>/MLET.txt
		 * */
		
	}

}
