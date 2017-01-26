package com.grapevineim.xmpp.ra.outbound;

import java.io.PrintWriter;
import java.io.Serializable;

import javax.resource.ResourceException;
import javax.resource.cci.ConnectionSpec;
import javax.resource.spi.ConnectionManager;
import javax.resource.spi.ManagedConnectionFactory;


import com.grapevineim.xmpp.XmppConnection;
import com.grapevineim.xmpp.XmppConnectionFactory;
import java.security.Principal;
import java.util.Collections;
import java.util.logging.Logger;
import javax.resource.spi.security.PasswordCredential;
import javax.security.auth.Subject;

public class XmppConnectionFactoryImpl implements XmppConnectionFactory, Serializable {

    private static final long serialVersionUID = -1;
    private static final Logger LOG = Logger.getLogger(XmppConnectionFactoryImpl.class.getName());
    private final ManagedConnectionFactory mcf;
    private final ConnectionManager cm;
    private PrintWriter out;

    public XmppConnectionFactoryImpl(ManagedConnectionFactory mcf,
            ConnectionManager cm) {
        this.mcf = mcf;
        this.cm = cm;
    }

    public XmppConnection createConnection()
            throws ResourceException {
        return this.createConnection(null);
    }

    public XmppConnection createConnection(ConnectionSpec properties)
            throws ResourceException {
        LOG.info("createConnection(ConnectionSpec)");
        final XmppConnectionRequestInfo info = (XmppConnectionRequestInfo) properties;
        if (cm != null) {
            return (XmppConnection) cm.allocateConnection(this.mcf, info);
        } else {
            return (XmppConnection) this.mcf.createManagedConnection(new Subject(true, Collections.singleton(new Principal() {

                public String getName() {
                    return info.getUsername();
                }
            }), Collections.EMPTY_SET, Collections.singleton(new PasswordCredential(info.getUsername(), info.getPassword().toCharArray()))), info).getConnection(new Subject(true, Collections.singleton(new Principal() {

                public String getName() {
                    return info.getUsername();
                }
            }), Collections.EMPTY_SET, Collections.singleton(new PasswordCredential(info.getUsername(), info.getPassword().toCharArray()))), info);
        }
    }

    public void setLogWriter(PrintWriter out) throws ResourceException {
        this.out = out;
    }

    public PrintWriter getLogWriter() throws ResourceException {
        return this.out;
    }
}
