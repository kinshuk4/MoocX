package com.grapevineim.xmpp;

import javax.resource.ResourceException;

public interface XmppConnection
{
    public void sendMessage(XmppMessage message) throws ResourceException;
    
    public void setPresence(String type, String message) throws ResourceException;

    //public void open() throws ResourceException;
    
    public void close() throws ResourceException;
    
    public void addMessageListener(XmppMessageListener listener) throws ResourceException;
    
    public void removeMessageListener(XmppMessageListener listener) throws ResourceException;

}
