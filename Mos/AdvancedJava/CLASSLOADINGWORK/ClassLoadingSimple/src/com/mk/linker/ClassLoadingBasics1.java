package com.mk.linker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ClassLoadingBasics1 {

	/**
	 * @param args
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws ClassNotFoundException {
		String basepath=".."+File.separator+"ClassLoadingMule"+File.separator+"src";
        System.out.println(ClassLoaderBasics.class.getProtectionDomain().getCodeSource().getLocation());

		System.out.println("Relative Path from ClassLoader :: "+basepath);
		PetsClassLoader dadcl=new PetsClassLoader(basepath,"DADClassloader");
		PetsClassLoader soncl=new PetsClassLoader(dadcl,basepath,"SONClassLoader");
		Class s=soncl.loadClass("com.mule.Animal");
		System.out.println("Class loader :: "+s.getClassLoader());
		soncl.loadClass("com.mule.Animal");
		dadcl.loadClass("com.mule.Animal");
	}

}
