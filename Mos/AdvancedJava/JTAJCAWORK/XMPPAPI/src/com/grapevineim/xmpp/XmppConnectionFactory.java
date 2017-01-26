package com.grapevineim.xmpp;

import javax.resource.ResourceException;
import javax.resource.cci.ConnectionSpec;

public interface XmppConnectionFactory
{
    public XmppConnection createConnection(ConnectionSpec properties)
        throws ResourceException;
    public XmppConnection createConnection()
        throws ResourceException;
}
