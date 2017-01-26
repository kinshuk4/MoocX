/*
 * Copyright 2004-2005 Sun Microsystems, Inc.  All rights reserved.
 * Use is subject to license terms.
 */
package samples.connectors.mailconnector.ra.outbound;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Logger;
import javax.resource.ResourceException;
import javax.security.auth.Subject;
import javax.resource.spi.ConnectionManager;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.ManagedConnection;
import javax.resource.spi.ManagedConnectionFactory;
import javax.resource.spi.security.PasswordCredential;

/**
 * An object of this class is a factory of both ManagedConnection and
 * connection factory instances.
 * This class supports connection pooling by defining methods for
 * matching and creating connections.
 * This class is implemented as a JavaBeans component.
 */
public class ManagedConnectionFactoryImpl implements
        ManagedConnectionFactory, Serializable {

    static Logger logger =
            Logger.getLogger("samples.connectors.mailconnector.ra.outbound");
    PrintWriter out = null;

    /**
     * Constructor.
     */
    public ManagedConnectionFactoryImpl() {
        bean = new ConnectionRequestInfoImpl();
    }

    public ManagedConnectionFactoryImpl(String serverName, String port, String userName, String password, String fromAddress, String protocol, boolean secure) {
        bean = new ConnectionRequestInfoImpl(userName, password, serverName, protocol, secure, port, fromAddress);
        System.out.println("bean::::" + bean);
    }

    public Object createConnectionFactory(ConnectionManager cxManager)
            throws ResourceException {
        logger.info(" 2.- MCF::createConnectionFactory(cxManager)");
        JavaMailConnectionFactoryImpl cf = null;
        try {
            cf = new JavaMailConnectionFactoryImpl(this, cxManager);
        } catch (Exception e) {
            throw new ResourceException(e.getMessage());
        }
        return cf;
    }

    public Object createConnectionFactory()
            throws ResourceException {
        logger.info(" 2.- MCF::createConnectionFactory()");
        return new JavaMailConnectionFactoryImpl(this, null);
    }

    public ManagedConnection createManagedConnection(Subject subject,
            ConnectionRequestInfo cxRequestInfo)
            throws ResourceException {
        logger.info(" 3A.- ManagedConnectionFactory::createManagedConnection(Subject, cxM)" + cxRequestInfo);
        ManagedConnectionImpl mc = new ManagedConnectionImpl(this, subject, cxRequestInfo);
        logger.info("ManagedConnection:" + mc);
        return mc;
    }

    public ManagedConnection matchManagedConnections(Set connectionSet,
            Subject subject,
            ConnectionRequestInfo cxRequestInfo)
            throws ResourceException {
        System.out.println("connectionSet:::::"+connectionSet);
        if(cxRequestInfo==null){
            //get default
            cxRequestInfo=this.getBean();
        }
        Iterator it = connectionSet.iterator();
        while (it.hasNext()) {
            Object obj = it.next();
            if (obj instanceof ManagedConnectionImpl) {
                ManagedConnectionImpl mc = (ManagedConnectionImpl) obj;
                if (mc.isTheSameTransport((ConnectionRequestInfoImpl) cxRequestInfo)) {
                    System.out.println("MCF::matchManagedConnections: found match mc = " + mc);
                    return mc;
                }
            }
        }
        return null;
    }
    /*public ManagedConnection matchManagedConnections(Set connectionSet,
            Subject subject,
            ConnectionRequestInfo cxRequestInfo)
            throws ResourceException {
        System.out.println("connectionSet:::::"+connectionSet);
        PasswordCredential pc=null;
        if(subject!=null||cxRequestInfo!=null){
            pc = Util.getPasswordCredential(this, subject, cxRequestInfo);
        }
        if(cxRequestInfo==null){
            //get default
            cxRequestInfo=this.getBean();
        }
        Iterator it = connectionSet.iterator();
        while (it.hasNext()) {
            Object obj = it.next();
            if (obj instanceof ManagedConnectionImpl) {
                ManagedConnectionImpl mc = (ManagedConnectionImpl) obj;
                System.out.println("Util.isPasswordCredentialEqual(mc.getPasswordCredential(), pc):" + Util.isPasswordCredentialEqual(mc.getPasswordCredential(), pc));
                if ((pc!=null?Util.isPasswordCredentialEqual(mc.getPasswordCredential(), pc):true) && mc.isTheSameTransport((ConnectionRequestInfoImpl) cxRequestInfo)) {
                    System.out.println("MCF::matchManagedConnections: found match mc = " + mc);
                    return mc;
                }
            }
        }
        return null;
    }*/

    @Override
    public int hashCode() {
        //The rule here is that if two objects have the same Id
        //i.e. they are equal and the .equals method returns true
        //     then the .hashCode method *must* return the same
        //     hash code for those two objects
        return bean.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null) {
            if (obj instanceof ManagedConnectionFactoryImpl) {
                ManagedConnectionFactoryImpl other = (ManagedConnectionFactoryImpl) obj;
                return bean.equals(other.bean);
            }
        }
        return false;
    }
    private ConnectionRequestInfoImpl bean;

    public ConnectionRequestInfoImpl getBean() {
        return bean;
    }

    public void setBean(ConnectionRequestInfoImpl bean) {
        this.bean = bean;
    }

    public String getPassword() {
        return bean.getPassword();
    }

    public void setPassword(String password) {
        bean.setPassword(password);
    }

    public String getPort() {
        return bean.getPort();
    }

    public void setPort(String port) {
        bean.setPort(port);
    }

    public String getProtocol() {
        return bean.getProtocol();
    }

    public void setProtocol(String protocol) {
        bean.setProtocol(protocol);
    }

    public boolean isSecure() {
        return bean.isSecure();
    }

    public void setSecure(String secure) {
        bean.setSecure(Boolean.valueOf(secure));
    }

    public String getServerName() {
        return bean.getServerName();
    }

    public void setServerName(String serverName) {
        bean.setServerName(serverName);
    }

    public String getUserName() {
        return bean.getUserName();
    }

    public void setUserName(String userName) {
        bean.setUserName(userName);
    }

    public String getFromAddress() {
        return bean.getFromAddress();
    }

    public void setFromAddress(String fromAddress) {
        bean.setFromAddress(fromAddress);
    }

    public void setLogWriter(PrintWriter out) throws ResourceException {
        this.out = out;
    }

    public PrintWriter getLogWriter() throws ResourceException {
        return this.out;
    }
}
