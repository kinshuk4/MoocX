/*
 * Copyright 2004-2005 Sun Microsystems, Inc.  All rights reserved.
 * Use is subject to license terms.
 */
package samples.connectors.mailconnector.ra.outbound;

import org.mk.training.jca.ConnectionEventListenerImpl;
import javax.resource.spi.*;
import javax.resource.ResourceException;
import javax.transaction.xa.XAResource;
import javax.security.auth.Subject;
import java.io.PrintWriter;
import java.util.*;
import java.util.logging.*;
import javax.resource.spi.security.PasswordCredential;
import javax.resource.spi.SecurityException;
import javax.resource.spi.IllegalStateException;
import javax.resource.NotSupportedException;

import samples.connectors.mailconnector.api.*;

/**
 * The ManagedConnectionImpl class represents a physical connection to the 
 * backend Mail Server.
 */
public class ManagedConnectionImpl implements ManagedConnection {

    private ManagedConnectionFactoryImpl mcf;
    private ConnectionEventListenerImpl eventListener;
    private Set connectionSet; // set of Mail Server Connections
    private PrintWriter logWriter;
    private boolean destroyed;
    private MailServerSession mailsession = null; //Several connections fro a mailsession
    private static int testCounter = 0;
    private int myId = 0;
    private PasswordCredential passCred = null;
    static Logger logger =
            Logger.getLogger("samples.connectors.mailconnector.ra.outbound");

    ManagedConnectionImpl(ManagedConnectionFactoryImpl mcf,
            Subject subject,
            ConnectionRequestInfo cxRequestInfo)
            throws ResourceException {
        myId = testCounter++;
        logger.info(" 3B.- (" + myId + ") ManagedConnection::constructor");
        this.mcf = mcf;
        // Note: this will select the credential that matches this MC's MCF.
        // The credential's MCF is set by the application server.
        this.passCred = Util.getPasswordCredential(mcf, subject, cxRequestInfo);
        // Open the physical connection to a mailsession
        openTransport(cxRequestInfo);
        connectionSet = new HashSet();
        eventListener = new ConnectionEventListenerImpl(this);
    }

    public Object getConnection(Subject subject,
            ConnectionRequestInfo cxRequestInfo)
            throws ResourceException {
        logger.info(" 4.- (" + myId + ") testManagedConnection::getConnection: ConnectionManager requested a Connection handle");
        checkIfDestroyed();
        if(cxRequestInfo!=null){
            //get default
            if(!this.mailsession.isTheSameTranport((ConnectionRequestInfoImpl) cxRequestInfo))
                throw new SecurityException("PRINCIPAL_DOES_NOT_MATCH");
        }
        JavaMailConnection javaMailCon = new JavaMailConnectionImpl(this, mailsession);
        addJavaMailConnection(javaMailCon);
        return javaMailCon;
    }

    public void destroy() throws ResourceException {
        if (destroyed) {
            return;
        }
        logger.info(" 9.- (" + myId + ") ManagedConnection::destroy called");
        destroyed = true;
        testCounter--;
        invalidateJavaMailConnections();
        try {
            mailsession.closeTranport();
        } catch (Exception e) {
            logger.info("ManagedConnectionImpl::destroy threw exception: " + e);
            throw new ResourceException(e.getMessage());
        }
    }

    public void cleanup() throws ResourceException {
        checkIfDestroyed();
        logger.info(" 8.- (" + myId + ") ManagedConnection::cleanup called");
        invalidateJavaMailConnections();
    }

    private void invalidateJavaMailConnections() {
        Iterator it = connectionSet.iterator();
        while (it.hasNext()) {
            JavaMailConnectionImpl javaMailCon =
                    (JavaMailConnectionImpl) it.next();
            javaMailCon.invalidate();
        }
        connectionSet.clear();
    }

    public void associateConnection(Object connection)
            throws ResourceException {
        checkIfDestroyed();
        if (connection instanceof JavaMailConnection) {
            JavaMailConnectionImpl javaMailCon =
                    (JavaMailConnectionImpl) connection;
            javaMailCon.associateConnection(this);
        } else {
            throw new IllegalStateException("INVALID_CONNECTION");
        }
    }

    public void addConnectionEventListener(ConnectionEventListener listener) {
        eventListener.addConnectorListener(listener);
    }

    public void removeConnectionEventListener(ConnectionEventListener listener) {
        eventListener.removeConnectorListener(listener);
    }

    public XAResource getXAResource()
            throws ResourceException {
        throw new NotSupportedException("NO_XATRANSACTION");
    }

    public javax.resource.spi.LocalTransaction getLocalTransaction()
            throws ResourceException {
        throw new NotSupportedException("NO_TRANSACTION");
    }

    public ManagedConnectionMetaData getMetaData()
            throws ResourceException {
        checkIfDestroyed();
        return new ManagedConnectionMetaDataImpl(this);
    }

    public void setLogWriter(PrintWriter out)
            throws ResourceException {
        this.logWriter = out;
    }

    public PrintWriter getLogWriter()
            throws ResourceException {
        return logWriter;
    }

    public String getUserName() {
        if (passCred != null) {
            return passCred.getUserName();
        } else {
            return null;
        }
    }

    public PasswordCredential getPasswordCredential() {
        return passCred;
    }

    public void addJavaMailConnection(JavaMailConnection javaMailCon) {
        connectionSet.add(javaMailCon);
    }

    private void checkIfDestroyed()
            throws ResourceException {
        if (destroyed) {
            throw new IllegalStateException("DESTROYED_CONNECTION");
        }
    }

    public void removeJavaMailConnection(JavaMailConnection javaMailCon) {
        connectionSet.remove(javaMailCon);
    }

    boolean isDestroyed() {
        return destroyed;
    }

    public ManagedConnectionFactoryImpl getManagedConnectionFactory() {
        return this.mcf;
    }

    public void sendEvent(int eventType, Exception ex) {
        eventListener.sendEvent(eventType, ex, null);
    }

    public void sendEvent(int eventType, Exception ex, Object connectionHandle) {
        eventListener.sendEvent(eventType, ex, connectionHandle);
    }

    private boolean openTransport(ConnectionRequestInfo cxRequestInfo)
            throws ResourceException {
        if (cxRequestInfo == null) {
            cxRequestInfo = mcf.getBean();
        }
        try {
            mailsession = new MailServerSession((ConnectionRequestInfoImpl) cxRequestInfo);
        } catch (Exception e) {
            logger.severe("JavaMailConnectionImpl::openStore threw exception: " + e);
            throw new ResourceException(e.getMessage());
        }
        return true;
    }

    public boolean isTheSameTransport(ConnectionRequestInfoImpl cxRequestInfo) {
        logger.info(" X.- (" + myId + ") ManagedConnection::isTheSame called");
        return mailsession.isTheSameTranport(cxRequestInfo);
    }
}
