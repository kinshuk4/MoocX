package com.mk.linker;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;


public class ClassLoaderHierarchy{

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		String basepath=".."+File.separator+"ClassLoaderMule";
		System.out.println("Relative Path from ClassLoader :: "+basepath);
		PetsClassLoader petcl=new PetsClassLoader(basepath);
		Class s1=petcl.loadClass("com.mule.Dog");
		ClassLoader s=s1.getClassLoader();
		while (s!=null) {
			System.out.println("Name :: "+s);
			s=s.getParent();
		}
		System.out.println("ClassLoader.getSystemClassLoader() :: "+ClassLoader.getSystemClassLoader());
		System.out.println("String.class.getClassLoader() :: "+String.class.getClassLoader());
		System.out.println("Implemented Interfaces, Extended Class Information ::");
		Class clazzez []=s1.getClasses();
		for (int i = 0; i < clazzez.length; i++) {
			System.out.println("member clazzez :: "+clazzez[i].getName()+" :: ClassLoaders :: "+clazzez[i].getClassLoader());
		}
		Class interfazes[]=s1.getInterfaces();
		for (int i = 0; i < interfazes.length; i++) {
			System.out.println("Interfazes :: "+interfazes[i].getName()+" :: ClassLoaders :: "+interfazes[i].getClassLoader());
		}
		Class superclazz=s1.getSuperclass();
		while(superclazz!=null){
			System.out.println("superclazz :: "+superclazz.getName()+" :: ClassLoaders :: "+superclazz.getClassLoader());
			superclazz=superclazz.getSuperclass();
		}
		
	}
	
}
