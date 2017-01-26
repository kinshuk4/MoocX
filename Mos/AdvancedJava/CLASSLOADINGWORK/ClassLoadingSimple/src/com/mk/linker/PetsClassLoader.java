package com.mk.linker;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

public class PetsClassLoader extends ClassLoader {
	private String basePath=File.pathSeparator;
	private String name=null;
	public PetsClassLoader(ClassLoader parent, String basePath, String name) {
		super(parent);
		this.basePath = basePath;
		this.name = name;
	}
	public PetsClassLoader( String basePath, String name) {
		
		this.basePath = basePath;
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public PetsClassLoader(String basePath) {
        this.basePath = basePath;
    }
    public PetsClassLoader(ClassLoader parent, String basePath) {
        super(parent);    
        this.basePath = basePath;
    }
    public PetsClassLoader() {
		super();
    }
	public PetsClassLoader(ClassLoader parent) {
		super(parent);
	}
    protected Class findClass(String className)
        throws ClassNotFoundException {
    	System.out.println("findClass():: "+className+" ClassLoader :: "+this.getName());
        byte classData[];
        // Try to load it from the basePath directory.
        classData = getTypeFromBasePath(className);
        if (classData == null)throw new ClassNotFoundException(className);
        // Parse it
        Class clazz=defineClass(className, classData, 0,
                classData.length);
        System.out.println("findClass():: got the class from defineClass() :: "+clazz+" Classloader :: "+this.getName());
        
        return clazz;
    }
    private byte[] getTypeFromBasePath(String typeName) {
    	System.out.println("getTypeFromBasePath():: "+typeName+" Classloader :: "+this.getName());
        FileInputStream fis;
        String fileName = basePath + File.separatorChar
            + typeName.replace('.', File.separatorChar)
            + ".class";
        try {
            fis = new FileInputStream(fileName);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("fileName::"+fileName);
            return null;
        }
        BufferedInputStream bis = new BufferedInputStream(fis);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            int c = bis.read();
            while (c != -1) {
                out.write(c);
                c = bis.read();
            }
        }
        catch (IOException e) {
            return null;
        }
        return out.toByteArray();
    }
	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		Class c = findLoadedClass(name);
		System.out.println("LoadClass :: findLoadedClass :: name :: "+name+" class :: "+c+" ClassLoader :: "+this.getName());
	    if(c == null) {
	        try {
	            c = getParent().loadClass(name);
	        } catch (ClassNotFoundException e) {
	        }
	        if(c == null)
	            c = findClass(name);
	    }
	    return c;
	}
	@Override
	public synchronized Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
		//dont override and make it public because it is of no use.
		//i am doing it to show that it makes no sense.
		return super.loadClass(name, resolve);
	}
	public String toString() {
		return super.toString()+" NAME :: "+name;
	}

}
