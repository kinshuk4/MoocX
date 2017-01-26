package com.grapevineim.xmpp.ra.outbound;

import javax.resource.spi.ConnectionRequestInfo;

import com.grapevineim.xmpp.XmppConnectionSpec;

/**
 * This class implements the ConnectionRequestInfo interface, which enables a
 * resource adapter to pass its own request-specific data structure across the
 * connection request flow.
 */

public class XmppConnectionRequestInfo extends XmppConnectionSpec implements ConnectionRequestInfo {
	
	private static final long serialVersionUID = -1;

	public XmppConnectionRequestInfo() {

	}

    public XmppConnectionRequestInfo(String host, Integer port, String domain, String username, String password) {
        super(host, port, domain, username, password);
    }

}
