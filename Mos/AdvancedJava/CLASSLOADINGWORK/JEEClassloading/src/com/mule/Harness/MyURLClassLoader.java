package com.mule.Harness;

import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;

public class MyURLClassLoader extends URLClassLoader {
	private String name=null;
	public MyURLClassLoader(String name,URL[] urls, ClassLoader parent, URLStreamHandlerFactory factory) {
		super(urls, parent, factory);
		this.name=name;
	}

	public MyURLClassLoader(String name,URL[] urls, ClassLoader parent) {
		super(urls, parent);
		this.name=name;
	}

	public MyURLClassLoader(String name,URL[] urls) {
		super(urls);
		this.name=name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return super.toString()+" NAME :: "+name;
	}

}
