package com.grapevineim.xmpp;

import javax.resource.ResourceException;

public interface XmppMessageListener {

	public void onMessage(XmppMessage message) throws ResourceException;
	
}
