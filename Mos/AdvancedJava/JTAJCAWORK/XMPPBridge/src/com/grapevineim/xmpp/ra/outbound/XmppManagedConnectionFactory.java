package com.grapevineim.xmpp.ra.outbound;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Logger;
import javax.resource.ResourceException;
import javax.resource.spi.ConnectionManager;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.ManagedConnection;
import javax.resource.spi.ManagedConnectionFactory;
import javax.resource.spi.security.PasswordCredential;
import javax.security.auth.Subject;

public class XmppManagedConnectionFactory implements ManagedConnectionFactory,
        Serializable {

    private static final long serialVersionUID = -1;
    private static final Logger LOG = Logger.getLogger(XmppManagedConnectionFactory.class.getName());
    private PrintWriter out;
    private Executor dispatchThreadExecutor = Executors.newFixedThreadPool(1);

    public XmppManagedConnectionFactory() {
        bean=new XmppConnectionRequestInfo();
    }

    public XmppManagedConnectionFactory(String host, Integer port, String domain, String username, String password) {
        bean=new XmppConnectionRequestInfo(host, port, domain, username, password);

    }

    Executor getDispatchThreadExecutor() {
        return dispatchThreadExecutor;
    }

    public void setDispatchThreadExecutor(Executor dispatchThreadExecutor) {
        this.dispatchThreadExecutor = dispatchThreadExecutor;

    }
    public Object createConnectionFactory(ConnectionManager cxManager)
            throws ResourceException {
        LOG.info("createConnectionFactory(ConnectionManager)");
        try {
            return new XmppConnectionFactoryImpl(this, cxManager);
        } catch (Exception e) {
            LOG.severe("Could not create XmppConnectionFactoryImpl" + e);
            throw new ResourceException(e);
        }
    }

    public Object createConnectionFactory() throws ResourceException {
        LOG.info("createConnectionFactory()");
        try {
            return new XmppConnectionFactoryImpl(this, null);
        } catch (Exception e) {
            LOG.severe("Could not create XmppConnectionFactoryImpl" + e);
            throw new ResourceException(e);
        }
    }

    public ManagedConnection createManagedConnection(Subject subject,
            ConnectionRequestInfo cxRequestInfo) throws ResourceException {
        LOG.info("createManagedConnection(Subject, ConnectionRequestInfo)");
        if (cxRequestInfo == null) {
            cxRequestInfo = this.getBean();
        }
        return new XmppManagedConnection(this, subject, cxRequestInfo);
    }

    public ManagedConnection matchManagedConnections(Set connectionSet,
            Subject subject, ConnectionRequestInfo cxRequestInfo)
            throws ResourceException {
        System.out.println("connectionSet:::::" + connectionSet);
        System.out.println("cxRequestInfo:::::" + cxRequestInfo);
        if (cxRequestInfo == null) {
            cxRequestInfo = this.getBean();
        }
        Iterator it = connectionSet.iterator();
        while (it.hasNext()) {
            Object obj = it.next();
            if (obj instanceof XmppManagedConnection) {
                XmppManagedConnection mc = (XmppManagedConnection) obj;
                System.out.println("mc.getConnectionrequestInfo() = " + mc.getConnectionrequestInfo());
                if (cxRequestInfo.equals(mc.getConnectionrequestInfo())) {
                    System.out.println("MCF::matchManagedConnections: found match mc = " + mc);
                    return mc;
                }
            }
        }
        return null;
    }

    public void setLogWriter(PrintWriter out) throws ResourceException {
        this.out = out;
    }

    public PrintWriter getLogWriter() throws ResourceException {
        return this.out;
    }
    private XmppConnectionRequestInfo bean;

    public XmppConnectionRequestInfo getBean() {
        return bean;
    }

    public void setBean(XmppConnectionRequestInfo bean) {
        this.bean = bean;
    }

    public String getHost() {
        return bean.getHost();
    }

    public void setHost(String host) {
        bean.setHost(host);
    }

    public Integer getPort() {
        return bean.getPort();
    }

    public void setPort(Integer port) {
        bean.setPort(port);
    }

    public String getDomain() {
        return bean.getDomain();
    }

    public void setDomain(String domain) {
        bean.setDomain(domain);
    }

    public String getUsername() {
        return bean.getDomain();
    }

    public void setUsername(String username) {
        bean.setUsername(username);
    }

    public String getPassword() {
        return bean.getPassword();
    }

    public void setPassword(String password) {
        bean.setPassword(password);
    }
}
