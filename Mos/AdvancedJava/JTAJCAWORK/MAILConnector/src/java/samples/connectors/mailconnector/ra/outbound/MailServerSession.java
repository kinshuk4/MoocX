/*
 * Copyright 2004-2005 Sun Microsystems, Inc.  All rights reserved.
 * Use is subject to license terms.
 */

/*
 * @(#)MailServerFolder.java	
 */
package samples.connectors.mailconnector.ra.outbound;

import samples.connectors.mailconnector.api.ConnectionSpecImpl;
import javax.mail.*;

import java.util.*;
import java.util.logging.*;

import samples.connectors.mailconnector.ra.*;


/**
 * @author Alejandro Murillo
 *
 */
public class MailServerSession {

    private javax.mail.Session session;
    private javax.mail.Authenticator authenticator;
    private javax.mail.Transport transport;
    private Properties mailProperties;
    private String userName,  password,  serverName,  protocol,  port;
    private boolean secure;
    private String fromAddress;
    public static final Logger logger =
            Logger.getLogger("samples.connectors.mailconnector.ra.outbound");
    private ConnectionRequestInfoImpl spec;

    /**
     * Constructor.
     *
     * @param spec the ConnectionSpec (ConnectionRequestInfo)
     */
    public MailServerSession(ConnectionRequestInfoImpl spec)
            throws Exception {
        System.out.println("spec:"+spec);
        this.spec=spec;
        userName = spec.getUserName();
        System.out.println("userName:"+userName);
        password = spec.getPassword();
        serverName = spec.getServerName();
        protocol = spec.getProtocol();
        port = spec.getPort();
        secure = spec.isSecure();
        fromAddress=spec.getFromAddress();
        
        mailProperties = new Properties();
        if ("smtp".equalsIgnoreCase(protocol)) {
            mailProperties.put("mail.transport.protocol", "smtp");
            mailProperties.put("mail.smtp.auth", "true");
            mailProperties.put("mail.smtp.host", serverName);
            mailProperties.put("mail.smtp.socketFactory.port", port);
            if (secure) {
                mailProperties.put("mail.transport.protocol", "smtp");
                mailProperties.put("mail.smtp.starttls.enable", "true");
                mailProperties.put("mail.smtp.auth", "true");
                mailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                mailProperties.put("mail.smtp.ssl", "true");
            }
        }
        connectTransport();
    }

    /**
     * Closes the Store.
     *
     * @exception Exception if the close fails
     */
    public void closeTranport()
            throws Exception {
        /*
         * The JavaMail Session object does not have an explicit close.
         */

        //logger.info("Listener::close()");
        this.transport.close();
        this.transport = null;
        this.authenticator = null;
        this.session = null;
    }

    /** 
     * Opens a connection to the mail server. Associated with a MC
     *
     * @exception Exception  if the open fails
     */
    private void connectTransport()
            throws Exception {
        try {
            // Get a session object
            if (userName != null && password != null) {
                authenticator = new SMTPAuthenticator();
            }
            session = javax.mail.Session.getInstance(mailProperties, authenticator);
            // Get a store object
            //store = session.getStore();
            transport = session.getTransport();
            transport.connect();
        } catch (Exception te) {
            logger.info("[S] Caught an exception when obtaining a " +
                    "JavaMail Session");
            throw new Exception(te.getMessage());
        }


    }

    public Transport getTransport() {
        return transport;
    }

    public Session getSession() {
        return session;
    }

    public boolean isTheSameTranport(ConnectionRequestInfoImpl cxRequestInfo) {
        boolean equal=this.spec.equals(cxRequestInfo);
        logger.info("isTheSameStore: found match!"+equal);
        return equal;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    private class SMTPAuthenticator extends javax.mail.Authenticator {

        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(userName, password);
        }
    }
}
