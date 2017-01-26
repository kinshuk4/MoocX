package com.grapevineim.xmpp.ra.outbound;


import javax.resource.ResourceException;
import javax.resource.spi.ManagedConnection;


import com.grapevineim.xmpp.XmppConnection;

/**
 * Application-level connection handle that is used by a client component to
 * access an EIS instance.
 */
public class XmppConnectionImpl extends AbstractConnectionImpl implements XmppConnection {
    

    public XmppConnectionImpl(ManagedConnection managedConnection)
            throws ResourceException {
        super((XmppManagedConnection) managedConnection);
        LOG.info("ConnectionHandle::::" + this);
        LOG.info("this.managedconnection::::" + this.managedconnection);
    }

    @Override
    public void close() throws ResourceException {
        LOG.info("close()");
        managedconnection.closePhysicalConnection();
        super.close();
    }
}




