/*
 * Copyright 2004-2005 Sun Microsystems, Inc.  All rights reserved.
 * Use is subject to license terms.
 */
package com.grapevineim.xmpp.ra.outbound;

import java.util.*;
import java.util.logging.*;

import javax.resource.ResourceException;
import javax.resource.spi.*;
import javax.resource.spi.security.*;

import javax.security.auth.Subject;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * Utilities. The following methods handle authentication.
 *
 *  Note: If subject is null, credential is created from
 *        the ConnectionRequestInfo user/password info.
 *        Otherwise it is created using the subject object.
 *
 * @author Alejandro E. Murillo
 */
public class Util {

    static Logger logger =
            Logger.getLogger("samples.connectors.mailconnector.ra.outbound");

    /**
     * Returns a PasswordCredential.
     *
     * @param mcf  a ManagedConnectionFactory object
     * @param subject  security context as JAAS subject
     * @param info  ConnectionRequestInfo instance
     *
     * @return a PasswordCredential
     */
    static public PasswordCredential getPasswordCredential(final ManagedConnectionFactory mcf,
            final Subject subject, ConnectionRequestInfo info)
            throws ResourceException {
        if (subject == null) {
            if (info == null) {
                //logger.info("\tUtil::GetPasswordCred: INFO is null");
                return null;
            } else {

                XmppConnectionRequestInfo myinfo =
                        (XmppConnectionRequestInfo) info;

                // Can't create a PC with null values

                if (myinfo.getUsername() == null || myinfo.getPassword() == null) {
                    //logger.info("\tUtil::GetPasswordCred: User or password is null");
                    return null;
                }

                char[] password = myinfo.getPassword().toCharArray();

                PasswordCredential pc =
                        new PasswordCredential(myinfo.getUsername(), password);

                pc.setManagedConnectionFactory(mcf);
                //logger.info("\tUtil::GetPasswordCred: returning a created PC");
                return pc;
            }
        } else {
            PasswordCredential pc =
                    (PasswordCredential) AccessController.doPrivileged(
                    new PrivilegedAction() {

                        public Object run() {
                            Set creds = subject.getPrivateCredentials(
                                    PasswordCredential.class);
                            Iterator iter = creds.iterator();
                            while (iter.hasNext()) {
                                PasswordCredential temp =
                                        (PasswordCredential) iter.next();
                                if (temp != null &&
                                        temp.getManagedConnectionFactory() != null &&
                                        temp.getManagedConnectionFactory().equals(mcf)) {
                                    return temp;
                                }
                            }
                            return null;
                        }
                    });
            if (pc == null) {
                throw new java.lang.SecurityException("util.no_credential");
            } else {
                //logger.info("\tUtil::GetPasswordCred: returning a FOUND PC");
                return pc;
            }
        }
    }

    /**
     * Determines whether two strings are the same.
     *
     * @param a  first string
     * @param b  second string
     *
     * @return  true if the two strings are equal; false otherwise
     */
    static public boolean isEqual(String a, String b) {
        if (a == null) {
            return (b == null);
        } else {
            return a.equals(b);
        }
    }

    /**
     * Determines whether two PasswordCredentials are the same.
     *
     * @param a  first PasswordCredential
     * @param b  second PasswordCredential
     *
     * @return  true if the two parameters are equal; false otherwise
     */
    static public boolean isPasswordCredentialEqual(PasswordCredential a,
            PasswordCredential b) {
        if (a == b) {
            return true;
        }
        if ((a == null) && (b != null)) {
            return false;
        }
        if ((a != null) && (b == null)) {
            return false;
        }
        if (!isEqual(a.getUserName(), b.getUserName())) {
            return false;
        }

        String p1 = null;
        String p2 = null;

        if (a.getPassword() != null) {
            p1 = new String(a.getPassword());
        }
        if (b.getPassword() != null) {
            p2 = new String(b.getPassword());
        }
        return (isEqual(p1, p2));
    }
}
