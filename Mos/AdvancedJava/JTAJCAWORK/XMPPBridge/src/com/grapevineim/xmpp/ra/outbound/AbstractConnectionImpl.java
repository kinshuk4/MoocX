/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grapevineim.xmpp.ra.outbound;

import com.grapevineim.xmpp.XmppMessage;
import com.grapevineim.xmpp.XmppMessageListener;
import java.util.logging.Logger;
import javax.resource.ResourceException;
import javax.resource.cci.ConnectionMetaData;
import javax.resource.spi.ConnectionEvent;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.packet.Presence;


/**
 * @author veenamohitkumar
 */
public abstract class AbstractConnectionImpl{
    static final Logger LOG = Logger.getLogger(XmppConnectionImpl.class.getName());
    XmppManagedConnection managedconnection; // if mc is null, connection is invalid
    

    /**
     * Constructor.
     *
     * @param mc  a physical connection to an underlying EIS
     * @param connectionInfo  connection-specific info/properties
     *
     */
    protected AbstractConnectionImpl(XmppManagedConnection mc) {
        this.managedconnection = mc;
        LOG.info(" 5. JavaMailConnectionImpl::Constructor:"+this);
    }

    /**
     * Closes the connection.
     */
    public void close()
            throws ResourceException {
        LOG.info("close()");
        if (managedconnection == null) {
            return;  // connection is already closed
        }
        managedconnection.removeXmppConnection(this);
        managedconnection.sendEvent(ConnectionEvent.CONNECTION_CLOSED, null, this);
        managedconnection = null;
    }

    /**
     * Associates connection handle with new managed connection.
     *
     * @param newMc  new managed connection
     */
    public void associateConnection(XmppManagedConnection newMc)
            throws ResourceException {
        checkIfValid();
        managedconnection.removeXmppConnection(this);
        newMc.addXmppConnection(this);
        managedconnection = newMc;
    }

    /**
     * Checks the validity of the physical connection to the EIS.
     */
    void checkIfValid()
            throws ResourceException {
        LOG.finest(" -- In JavaMailConnectionImpl::checkIfValid mc=" + managedconnection);
        if (managedconnection == null) {
            throw new ResourceException("INVALID_CONNECTION");
        }
    }

    /**
     * Sets the physical connection to the EIS as invalid.
     * The physical connection to the EIS cannot be used any more.
     */
    void invalidate() {
        LOG.info(" -- In JavaMailConnectionImpl::invalidate mc=" + managedconnection);
        managedconnection = null;
    }

    private XMPPConnection getPhysicalConnection() throws ResourceException{
        if(managedconnection!=null)
            return managedconnection.getPhysicalconnection();
        else throw new ResourceException("This connection handle is closed.");
    }

    public void sendMessage(XmppMessage xmppMessage) throws ResourceException {
        LOG.info("sendMessage(XmppMessage)");
        try {
            ChatManager chatManager = this.getPhysicalConnection().getChatManager();
            Chat chat = null;
            // get the chat if the threadID exists
            if (xmppMessage.getThreadId() != null) {
                chat = chatManager.getThreadChat(xmppMessage.getThreadId());
            }
            // if chat is still null then create a new one
            if (chat == null) {
                chat = chatManager.createChat(xmppMessage.getTo(), null);
            }
            // send the message
            chat.sendMessage(xmppMessage.getBody());
        } catch (Exception e) {
            LOG.severe("Could not sendMessage." + e);
            throw new ResourceException("Could not sendMessage.", e);
        }
    }

    public void setPresence(String type, String status)
            throws ResourceException {
        LOG.info("setPresence(String,String)");
        try {
            this.setPresence(this.getPhysicalConnection(), type, status);
        } catch (Exception e) {
            LOG.severe("Could not setPresence." + e);
            throw new ResourceException("Could not setPresence.", e);
        }
    }

    private void setPresence(XMPPConnection conn, String type, String status)
            throws ResourceException {
        try {
            // Create a new presence.
            Presence presence = new Presence(Presence.Type.valueOf(type));
            presence.setStatus(status);
            // Send the packet
            conn.sendPacket(presence);
        } catch (Exception e) {
            LOG.severe("Could not set presence." + e);
            throw new ResourceException("Could not set presence", e);
        }
    }

    public void addMessageListener(XmppMessageListener l)
            throws ResourceException {
        ((XmppManagedConnection)managedconnection).addMessageListener(l);
    }

    public void removeMessageListener(XmppMessageListener l)
            throws ResourceException {
        ((XmppManagedConnection)managedconnection).removeMessageListener(l);
    }
}
