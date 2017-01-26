package org.mk.training.mail.incoming;

import org.mk.training.mail.incoming.jse.JSEENVMailActivator;
import java.io.Serializable;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import javax.resource.cci.ConnectionSpec;
import samples.connectors.mailconnector.api.ConnectionSpecImpl;


public class MailReceiver implements Serializable {

    /** @since 1.0 */
    private static final long serialVersionUID = -3034364895936568423L;
    /** The mail server hostname/address */
    private String mailServer;
    /** The mail store protocol */
    private String storeProtocol;
    /** The mail folder name */
    private String mailFolder = "Inbox";
    /** The mail store user */
    private String userName;
    /** The mail store password */
    private String password;
    /** The new messages check delay in MS */
    private long pollingInterval = 60000;
    /** Enable JavaMail debugging */
    private boolean debug = false;
    /** flush - for pop3 flush the mailbox after checking */
    private boolean flush = true;
    private String port;
    private boolean secureExchange;
    private boolean imapIdle = true;
    private Executor listenerThreadExecutor = Executors.newSingleThreadExecutor();
    private Executor dispatchThreadExecutor = Executors.newCachedThreadPool();
    private Properties sessionProperties;
    private AbstractMailActivator activator;

    public MailReceiver(String mailServer, String storeProtocol, String userName, String password, String port,MailListener listener,ListenerFactory factory, Properties sessionProperties) {
        this.mailServer = mailServer;
        this.storeProtocol = storeProtocol;
        this.userName = userName;
        this.password = password;
        this.port = port;
        this.sessionProperties = sessionProperties;
        activator = new JSEENVMailActivator(this, listener,factory);
    }

    public MailReceiver() {
        System.out.println("MailReceiver()");
    }

    public Properties getSessionProperties() {
        return sessionProperties;
    }

    public void setSessionProperties(Properties sessionProperties) {
        this.sessionProperties = sessionProperties;
    }

    public boolean isImapIdle() {
        return imapIdle;
    }

    public void setImapIdle(String imapIdle) {
        this.imapIdle = Boolean.valueOf(imapIdle);
    }

    public void setImapIdle(boolean imapIdle) {
        this.imapIdle = imapIdle;
    }

    public Executor getListenerThreadExecutor() {
        return listenerThreadExecutor;
    }

    public void setListenerThreadExecutor(Executor listenerThreadExecutor) {
        this.listenerThreadExecutor = listenerThreadExecutor;
    }

    public Executor getDispatchThreadExecutor() {
        return dispatchThreadExecutor;
    }

    public void setDispatchThreadExecutor(Executor dispatchThreadExecutor) {
        this.dispatchThreadExecutor = dispatchThreadExecutor;

    }

    public String getMailServer() {
        return mailServer;
    }

    public void setMailServer(String mailServer) {
        this.mailServer = mailServer;
    }

    public String getStoreProtocol() {
        return storeProtocol;
    }

    public void setStoreProtocol(String storeProtocol) {
        this.storeProtocol = storeProtocol;
    }

    public String getMailFolder() {
        return mailFolder;
    }

    public void setMailFolder(String mailFolder) {
        this.mailFolder = mailFolder;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getPollingInterval() {
        return pollingInterval;
    }

    public void setPollingInterval(String pollingInterval) {
        this.pollingInterval = Long.parseLong(pollingInterval);
    }

    public void setDebug(String debug) {
        this.debug = Boolean.valueOf(debug);
    }

    public boolean isDebug() {
        return debug;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setFlush(String flush) {
        this.flush = Boolean.valueOf(flush);
    }

    public boolean isFlush() {
        return flush;
    }

    public AbstractMailActivator getActivator() {
        return activator;
    }

    public void setActivator(AbstractMailActivator activator) {
        this.activator = activator;
    }

    public boolean isSecureExchange() {
        return secureExchange;
    }

    public void setSecureExchange(String secureExchange) {
        this.secureExchange = Boolean.valueOf(secureExchange);
    }
    
    public void setSecureExchange(boolean secureExchange) {
        this.secureExchange = secureExchange;
    }
    public static MailReceiver newSecureMailReceiver(String mailServer, String storeProtocol, String userName,
            String password, String port, MailListener listener) {
        Properties sessionProps = MailReceiver.getExchangeProperties(port, true, storeProtocol);
        return new MailReceiver(mailServer, storeProtocol, userName, password, port, listener,null, sessionProps);
    }
    public static MailReceiver newSecureMailReceiver(String mailServer, String storeProtocol, String userName,
            String password, String port,ListenerFactory factory) {
        Properties sessionProps = MailReceiver.getExchangeProperties(port, true, storeProtocol);
        return new MailReceiver(mailServer, storeProtocol, userName, password, port,null,factory, sessionProps);
    }
    public static MailReceiver newSecureMailReceiver(ConnectionSpec spec,ListenerFactory factory) {
        ConnectionSpecImpl conspec=(ConnectionSpecImpl)spec;
        return newSecureMailReceiver(conspec.getServerName(),conspec.getProtocol(),conspec.getUserName(),conspec.getPassword(),conspec.getPort(),factory);
    }
    public static MailReceiver newSecureMailReceiver(ConnectionSpec spec,MailListener listener) {
        ConnectionSpecImpl conspec=(ConnectionSpecImpl)spec;
        return newSecureMailReceiver(conspec.getServerName(),conspec.getProtocol(),conspec.getUserName(),conspec.getPassword(),conspec.getPort(),listener);
    }

    public static Properties getExchangeProperties(String port, boolean secureexchange, String storeProtocol) {
        Properties sessionProps = new Properties();
        if (secureexchange) {
            if ("pop3".equalsIgnoreCase(storeProtocol)) {
                sessionProps.setProperty("mail.imap.starttls.enable", "true");
                sessionProps.setProperty("mail.pop3.socketFactory.class",
                        "javax.net.ssl.SSLSocketFactory");
                sessionProps.setProperty("mail.pop3.socketFactory.fallback", "false");
                sessionProps.setProperty("mail.pop3.socketFactory.port", port);
            } else if ("imap".equalsIgnoreCase(storeProtocol)) {
                sessionProps.setProperty("mail.imap.starttls.enable", "true");
                sessionProps.setProperty("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                sessionProps.setProperty("mail.imap.socketFactory.fallback", "false");
                sessionProps.setProperty("mail.imap.socketFactory.port", port);
            }
        }
        return sessionProps;
    }

    public void setupIncomingMail() {
        listenerThreadExecutor.execute(activator);
        System.out.println("After Execute");
    }

    public void shutdown() {
        activator.shutdown();
    }

    public String toString() {
        StringBuffer tmp = new StringBuffer("MailActivationSpec(");
        tmp.append("mailServer=");
        tmp.append(mailServer);
        tmp.append(", port=");
        tmp.append(port);
        tmp.append(", storeProtocol=");
        tmp.append(storeProtocol);
        tmp.append(", mailFolder=");
        tmp.append(mailFolder);
        tmp.append(", pollingInterval=");
        tmp.append(pollingInterval);
        tmp.append(", userName=");
        tmp.append(userName);
        tmp.append(", debug=");
        tmp.append(debug);
        tmp.append(", imapIdle=");
        tmp.append(imapIdle);
        tmp.append(")");
        return tmp.toString();
    }
}