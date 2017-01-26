package com.mk.linker;

import java.io.File;

public class ClassLoaderBasics {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
        String basepath=".."+File.separator+"ClassLoadingMule"+File.separator+"src";
        System.out.println("Relative Path from ClassLoader :: "+basepath);
		PetsClassLoader petcl=new PetsClassLoader(basepath);
		
		System.out.println("Loading Cat........");
		petcl.loadClass("com.mule.Cat");
		System.out.println("Loading Dog........");
		petcl.loadClass("com.mule.Dog",true);
		System.out.println("Loading F........");
		Class.forName("com.mk.linker.F",true,Thread.currentThread().getContextClassLoader());
		System.out.println("Loading E........");
		Class.forName("com.mk.linker.E",false,Thread.currentThread().getContextClassLoader()	);
		System.out.println("Loading Animal........");
		Class.forName("com.mule.Animal",true,petcl);
		Class.forName("com.mule.G",false,petcl);
		Class.forName("com.mk.linker.H");
		
	}

}
