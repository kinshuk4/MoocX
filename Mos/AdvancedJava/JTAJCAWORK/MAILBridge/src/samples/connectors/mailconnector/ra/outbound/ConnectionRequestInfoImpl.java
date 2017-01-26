/*
 * Copyright 2004-2005 Sun Microsystems, Inc.  All rights reserved.
 * Use is subject to license terms.
 */
package samples.connectors.mailconnector.ra.outbound;

import javax.resource.spi.*;
import samples.connectors.mailconnector.api.ConnectionSpecImpl;

/**
 * This class implments the ConnectionRequestInfo interface, which enables a 
 * resource adapter to pass its own request-specific data structure across the 
 * connection request flow.
 */
public class ConnectionRequestInfoImpl extends ConnectionSpecImpl implements ConnectionRequestInfo {

    public ConnectionRequestInfoImpl(String userName, String password, String serverName, String protocol, boolean secure, String port, String fromAddress) {
        super(userName, password, serverName, protocol, secure, port, fromAddress);
    }

    public ConnectionRequestInfoImpl() {
    }
}
