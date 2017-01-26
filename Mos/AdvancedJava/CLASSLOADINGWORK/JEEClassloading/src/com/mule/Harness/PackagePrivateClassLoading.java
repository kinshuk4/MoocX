package com.mule.Harness;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class PackagePrivateClassLoading {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		URL ejbjar=new URL("http","127.0.0.1",8080,"/files/repository/ejbjar.jar");
		URL[] ejbjarurl = {ejbjar};
	    URLClassLoader ejbjarclassloader = new MyURLClassLoader("EJBJARCLASSLOADER",ejbjarurl);
        Thread.currentThread().setContextClassLoader(ejbjarclassloader);
        Class sessioninterface=ejbjarclassloader.loadClass("com.mule.eapp.SessionInterface");

        Class anothersessionbeanclass=ejbjarclassloader.loadClass("com.mule.eapp.AnotherSessionBean");
        Object anothersessionbeaninstance=anothersessionbeanclass.newInstance();
        

	}

}
