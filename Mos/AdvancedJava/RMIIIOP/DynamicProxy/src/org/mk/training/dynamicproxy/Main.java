/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mk.training.dynamicproxy;

import java.lang.reflect.Proxy;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author veenamohitkumar
 */
public class Main {
    public static void main(String[] args) {
        XIntf sessionbean = null;
        try {
            sessionbean = (XIntf) Naming.lookup("rmi://localhost:1099/RemoteServer");
        } catch (NotBoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
            sessionbean.setName("NANU");
    }
}
