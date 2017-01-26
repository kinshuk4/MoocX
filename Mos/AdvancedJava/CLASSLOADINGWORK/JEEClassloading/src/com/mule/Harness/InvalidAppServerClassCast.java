package com.mule.Harness;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class InvalidAppServerClassCast {

	/**
	 * @param args
	 * @throws MalformedURLException 
	 */
	/*
	 * 1. one way to avoid it is make web classloader the child of enterprise classloader
	 * 2. second way is go for peer classloading
	 */
	public static void main(String[] args) throws Exception {
		//Using different class loaders for diferent application.
		//ejbjarurl for ejb application
		//offcourse using reflection for dynamic binding.
        URL ejbjar=new URL("http","127.0.0.1",8080,"/files/repository/ejbjar.jar");
		URL[] ejbjarurl = {ejbjar};
        URLClassLoader ejbjarclassloader = new MyURLClassLoader("EJBJARCLASSLOADER",ejbjarurl);
        Thread.currentThread().setContextClassLoader(ejbjarclassloader);
        Class sessionbeanclass=ejbjarclassloader.loadClass("com.mule.eapp.SessionBean");
        Object sessionbeaninstance=sessionbeanclass.newInstance();
        
        URL webappjar=new URL("http","127.0.0.1",8080,"/files/repository/webappjar.jar");
        URL[] webappjarurl = {webappjar};
        URLClassLoader webappjarurlclassloader = new MyURLClassLoader("WEBAPPJARCLASSLOADER",webappjarurl);
        Thread.currentThread().setContextClassLoader(webappjarurlclassloader);
        Class servletclass=webappjarurlclassloader.loadClass("com.mule.wapp.MyServlet");
        Object servletinstance=servletclass.newInstance();
        
        //now calling the method
        Class []parms={Object.class};
        Method setbean=servletclass.getMethod("setBean", parms);
        try{//will throw class cast.
        	setbean.invoke(servletinstance, sessionbeaninstance);
        }catch (InvocationTargetException e){e.printStackTrace(System.out);
        }
        	
        
        System.out.println("Details of Servlet ::::::::::::::::::");
        System.out.println("SERVLETCLASS :: "+servletclass.getName()+"("+servletclass.hashCode()+") :: ClassLoaders :: "+servletclass.getClassLoader());
        Field fields []=servletclass.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
        	System.out.println("Instance fields :: "+fields[i].getType()+"("+fields[i].getType().hashCode()+") :: ClassLoaders :: "+fields[i].getType().getClassLoader());
		}
		Class interfazes[]=servletclass.getInterfaces();
		for (int i = 0; i < interfazes.length; i++) {
			System.out.println("Interfazes :: "+interfazes[i].getName()+"("+interfazes[i].hashCode()+") :: ClassLoaders :: "+interfazes[i].getClassLoader());
		}
		Class superclazz=servletclass.getSuperclass();
		while(superclazz!=null){
			System.out.println("superclazz :: "+superclazz.getName()+"("+superclazz.hashCode()+") :: ClassLoaders :: "+superclazz.getClassLoader());
			superclazz=superclazz.getSuperclass();
		}
		System.out.println("Details of SessionBean ::::::::::::::::::");
		System.out.println("SESSIONBEANCLASS :: "+sessionbeanclass.getName()+"("+sessionbeanclass.hashCode()+") :: ClassLoaders :: "+sessionbeanclass.getClassLoader());
		Class clazzez1 []=sessionbeanclass.getDeclaredClasses();
		for (int i = 0; i < clazzez1.length; i++) {
			System.out.println("member clazzez :: "+clazzez1[i].getName()+"("+clazzez1[i].hashCode()+") :: ClassLoaders :: "+clazzez1[i].getClassLoader());
		}
		Class interfazes1[]=sessionbeanclass.getInterfaces();
		for (int i = 0; i < interfazes1.length; i++) {
			System.out.println("Interfazes :: "+interfazes1[i].getName()+"("+interfazes1[i].hashCode()+") :: ClassLoaders :: "+interfazes1[i].getClassLoader());
		}
		Class superclazz1=sessionbeanclass.getSuperclass();
		while(superclazz1!=null){
			System.out.println("superclazz :: "+superclazz1.getName()+"("+superclazz1.hashCode()+") :: ClassLoaders :: "+superclazz1.getClassLoader());
			superclazz1=superclazz1.getSuperclass();
		}
		
	}

}
