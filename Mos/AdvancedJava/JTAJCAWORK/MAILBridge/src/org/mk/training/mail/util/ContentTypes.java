/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mk.training.mail.util;

import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.internet.ContentType;
import javax.mail.internet.ParseException;

/**
 *
 * @author veenamohitkumar
 */
public class ContentTypes {

    private static ContentType _TEXTXML;
    private static ContentType _TEXTHTML;
    private static ContentType _TEXTPLAIN;
    private static Set<ContentType> cts;

    static {
        try {
            _TEXTXML = new ContentType("text/xml; charset=us-ascii");
            _TEXTHTML = new ContentType("text/html; charset=us-ascii");
            _TEXTPLAIN = new ContentType("text/plain; charset=us-ascii");
            cts = new HashSet<ContentType>();
            cts.add(_TEXTXML);
            cts.add(_TEXTHTML);
            cts.add(_TEXTPLAIN);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }

    public enum TYPE {

        TEXTXML(_TEXTXML),
        TEXTHTML(_TEXTHTML),
        TEXTPLAIN(_TEXTPLAIN);
        private ContentType type;

        private TYPE(ContentType type) {
            this.type = type;
        }

        public static TYPE baseTypeFor(String ctstring) {
            ContentType ct = null;
            try {
                ct = new ContentType(ctstring);
            } catch (ParseException ex) {
                return null;
            }
            for (TYPE bt : TYPE.values()) {
                if (bt.type.getPrimaryType().equalsIgnoreCase(ct.getPrimaryType())
                        && bt.type.getSubType().equalsIgnoreCase(ct.getSubType())) {
                    return bt;
                }
            }
            return null;
        }

        public boolean match(String ctstring) {
            return type.match(ctstring);
        }

        public boolean match(ContentType ct) {
            return type.match(ct);
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println("ContentTypes.match()" + ContentTypes.TYPE.TEXTXML.match("TEXT/XML"));
        Enumeration<NetworkInterface> ni = NetworkInterface.getNetworkInterfaces();
        /*while (ni.hasMoreElements()) {
            NetworkInterface networkInterface = ni.nextElement();
            System.out.println("networkInterface.getInetAddresses():" + networkInterface.getName());
            System.out.println("networkInterface.isUp():" + networkInterface.isUp());
            System.out.println("networkInterface.getDisplayName():" + networkInterface.getDisplayName());
            System.out.println("networkInterface.isLoopback():" + networkInterface.isLoopback());
            List<InterfaceAddress> la = networkInterface.getInterfaceAddresses();
            for (Iterator<InterfaceAddress> it = la.iterator(); it.hasNext();) {
                InterfaceAddress interfaceAddress = it.next();
                System.out.println("interfaceAddress.getAddress():" + interfaceAddress.getAddress());
            }
            //System.out.println("networkInterface.getInetAddresses():");
        }*/
        System.out.println(checkWebConnectivity());
    }

    private static boolean checkWebConnectivity() {
        boolean isup=false;
        Enumeration<NetworkInterface> interfaces = null;
        try {
            interfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException ex) {
            Logger.getLogger(ContentTypes.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (interfaces.hasMoreElements()) {
            NetworkInterface interf = interfaces.nextElement();
            try {
                if (interf.isUp() && !interf.isLoopback()) {
                    isup=true;
                }
            } catch (SocketException ex) {
                Logger.getLogger(ContentTypes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return isup;
    }
}
