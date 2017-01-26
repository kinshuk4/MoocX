package com.mule.HarnessPeer;

import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;

public class NonDefaultPeerClassLoader extends URLClassLoader {

    private String name = null;

    public NonDefaultPeerClassLoader(String name, URL[] urls, ClassLoader parent, URLStreamHandlerFactory factory) {
        super(urls, parent, factory);
        this.name = name;
    }

    public NonDefaultPeerClassLoader(String name, URL[] urls, ClassLoader parent) {
        super(urls, parent);
        this.name = name;
    }

    public NonDefaultPeerClassLoader(String name, URL[] urls) {
        super(urls);
        this.name = name;
    }

    @Override
    public synchronized Class<?> loadClass(String name) throws ClassNotFoundException {
        Class clazz = null;
        //have i loaded the class befor(same as parent delegation model)
        clazz = this.findLoadedClass(name);
        if (clazz != null) {
            System.out.println("loadClass :::::::::::::::::::::::: Loaded by me" + clazz);
            return clazz;
        }
        try {
            //use default model for library classes and extension classes(same as parent delegation model)
            if (name.startsWith("java") || name.startsWith("javax")) {
                clazz = this.getParent().loadClass(name);
                return clazz;
            }
        } catch (ClassNotFoundException cnfe) {/*ignore*/
            cnfe.printStackTrace(System.out);
        }
        //Now Dont follow the default model because it end up loading the same class more than
        //once and can become a cause for ClassCastException and LinkageError.
        //So Check the class cache which is a Singleton.
        //if the class is present in the cache note that we are using a class loaded by a different
        //classloader.The class visibility rule (classes loaded by a loader and its parent) is imposed as a
        //as a matter of convention of parent delegation model.The reason for class visibility rule is
        //that the reference of parent loader is kept in the classloader.
        clazz = UnifiedLoaderRepository.getInstance().getFromCache(name);
        if (clazz != null) {
            return clazz;
        }
        try {
            //if the cache doesnt return the class get back to delegation model(same as parent delegation model)
            clazz = this.getParent().loadClass(name);
            if (clazz != null) {
                return clazz;
            }
        } catch (ClassNotFoundException cnfe) {/*ignore*/

        }
        try {
            //so when every thing fails let me fond the class myself.
            clazz = this.findClass(name);
            if (clazz != null) {
                //if i find the class let me update the cache so that somebody else can find it.
                UnifiedLoaderRepository.getInstance().updateCacheIfAbsent(name, clazz);
                return clazz;
            } else {
                throw new ClassNotFoundException("could not find your class" + name);
            }
        } catch (ClassNotFoundException cnfe) {
            throw cnfe;
        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return super.toString() + " NAME :: " + name;
    }
}
