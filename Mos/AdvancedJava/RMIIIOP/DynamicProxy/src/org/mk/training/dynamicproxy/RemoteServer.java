package org.mk.training.dynamicproxy;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class RemoteServer extends UnicastRemoteObject implements ServerInterface{
    Object proxyfor;

    public RemoteServer(Object proxyfor) throws RemoteException {
        this.proxyfor = proxyfor;
    }
    public static void main(String[] args) throws Exception {
        LocateRegistry.createRegistry(1099);
		RemoteServer obj=new RemoteServer(new X());
        List<Class<?>> interfaces=new ArrayList<Class<?>>(Arrays.asList(obj.proxyfor.getClass().getInterfaces()));
        interfaces.add(Remote.class);
        interfaces.add(Serializable.class);
        Class []classes=new Class[obj.proxyfor.getClass().getInterfaces().length+2];
        Object o=Proxy.newProxyInstance(obj.proxyfor.getClass().getClassLoader(),
                interfaces.toArray(classes), new ProxyInvoker((ServerInterface)obj));
		Naming.rebind("/RemoteServer",(Remote) o);
		System.out.println("RemoteServer bound in registry:: "+obj);
	}

    public Object call(String methodname, Object[] arg) throws RemoteException {
        Method m=null;
        Class [] params=new Class[arg.length];
        for (int i = 0; i < arg.length; i++) {
            params[i] = arg[i].getClass();
        }
        try {
            m = proxyfor.getClass().getMethod(methodname, params);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(RemoteServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(RemoteServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            return m.invoke(proxyfor, arg);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(RemoteServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(RemoteServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(RemoteServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
