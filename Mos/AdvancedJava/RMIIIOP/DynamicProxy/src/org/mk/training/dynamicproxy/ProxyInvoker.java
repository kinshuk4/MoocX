/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mk.training.dynamicproxy;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 *
 * @author veenamohitkumar
 */
public class ProxyInvoker implements InvocationHandler,Serializable{
    ServerInterface proxied;

    public ProxyInvoker(ServerInterface proxied) {
        this.proxied = proxied;
    }
    
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result =null;
        try {
            System.out.print("begin method " +
             method.getName() + "(");
            for(int i=0; i<args.length; i++) {
               if(i>0) System.out.print(",");
                  System.out.print(" " +
                   args[i].toString());
            }
            System.out.println(" )");
            result=proxied.call(method.getName(), args);
        } catch (Exception e) {
           e.printStackTrace();
        } finally {
            System.out.println("end method " +
             method.getName());
        }
        return result;
    }

}
