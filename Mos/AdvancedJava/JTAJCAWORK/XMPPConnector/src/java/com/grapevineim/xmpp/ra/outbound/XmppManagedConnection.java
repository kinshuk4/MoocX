package com.grapevineim.xmpp.ra.outbound;

import com.grapevineim.xmpp.XmppConnection;
import com.grapevineim.xmpp.XmppMessage;
import com.grapevineim.xmpp.XmppMessageListener;
import java.io.PrintWriter;
import java.security.Principal;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Logger;
import javax.resource.NotSupportedException;
import javax.resource.ResourceException;
import javax.resource.spi.ConnectionEventListener;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.LocalTransaction;
import javax.resource.spi.ManagedConnection;
import javax.resource.spi.ManagedConnectionMetaData;
import javax.resource.spi.work.Work;
import javax.security.auth.Subject;
import javax.transaction.xa.XAResource;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.filter.ToContainsFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.mk.training.jca.ConnectionEventListenerImpl;

public class XmppManagedConnection implements ManagedConnection {

    private final ManagedConnectionMetaData metaData;
    private final XMPPConnection physicalconnection;
    private final XmppConnectionRequestInfo connectionRequestInfo;
    private final ConnectionEventListenerImpl eventListener;
    private final Set connectionSet;
    private PrintWriter out;
    private static final Logger LOG = Logger.getLogger(XmppManagedConnection.class.getName());
    private boolean destroyed;
    private final MessagePacketProcessor messagePacketProcessor;
    private final PresencePacketProcessor presencePacketProcessor;
    //private final ConnectionListenerImpl connectionListener;
    private final Set<XmppMessageListener> messageListeners;
    private XmppManagedConnectionFactory mcf;

    public XmppManagedConnection(XmppManagedConnectionFactory mcf,
            Subject subject, ConnectionRequestInfo connectionRequestInfo)
            throws ResourceException {
        this.mcf=mcf;
        this.metaData = new XmppManagedConnectionMetaData(getUserName(subject));
        if (connectionRequestInfo != null) {
            this.connectionRequestInfo = (XmppConnectionRequestInfo) connectionRequestInfo;
        } else {
            //get default
            this.connectionRequestInfo = mcf.getBean();
        }
        this.physicalconnection = this.getConnection((XmppConnectionRequestInfo) this.connectionRequestInfo);
        eventListener = new ConnectionEventListenerImpl(this);
        connectionSet = Collections.newSetFromMap(new ConcurrentHashMap<Object, Boolean>());
        messageListeners = Collections.newSetFromMap(new ConcurrentHashMap<XmppMessageListener, Boolean>());
        this.messagePacketProcessor = new MessagePacketProcessor(this);
        this.presencePacketProcessor = new PresencePacketProcessor(this.physicalconnection);
        this.open();
        LOG.info("XmppManagedConnection::::" + this);
        LOG.info("XMPP physical Connection::::" + this.physicalconnection);
    }

    private String getUserName(Subject subject) {
        if (subject != null) {
            // return the first principal's name
            for (Principal principal : subject.getPrincipals()) {
                return principal.getName();
            }
        }
        return "unknown";
    }

    public Object getConnection(Subject subject,
            ConnectionRequestInfo connectionRequestInfo)
            throws ResourceException {
        LOG.info("getConnection()");
        XmppConnection handle = null;
        if (connectionRequestInfo == null) {
            connectionRequestInfo = this.getConnectionrequestInfo();
        }
        if (this.connectionRequestInfo.equals(connectionRequestInfo)) {
            handle = new XmppConnectionImpl(this);
            this.open();
            this.addXmppConnection((AbstractConnectionImpl) handle);
        } else {
            throw new NotSupportedException("Getting a connection with a different connectionRequestInfo is not supported");
        }
        return handle;
    }

    XMPPConnection getPhysicalconnection() {
        return physicalconnection;
    }

    public void destroy() throws ResourceException {
        LOG.info("destroy()");
        if (destroyed) {
            return;
        }
        destroyed = true;
        invalidateXmppConnectionHandles();
        this.closePhysicalConnection();
    }

    void addMessageListener(XmppMessageListener listener){
        messageListeners.add(listener);
        Executor e=mcf.getDispatchThreadExecutor();
        if(e instanceof ThreadPoolExecutor){
            ((ThreadPoolExecutor)e).prestartCoreThread();
        }
    }
    void removeMessageListener(XmppMessageListener listener){
        System.out.println("removeMessageListener(XmppMessageListener listener)");
        messageListeners.remove(listener);
    }
    public void cleanup() throws ResourceException {
        LOG.info("cleanup()");
        checkIfDestroyed();
        invalidateXmppConnectionHandles();
    }

    public void associateConnection(Object connection) throws ResourceException {
        LOG.info("associateConnection(Object)");
        throw new NotSupportedException("associateConnection() not supported");
    }

    public void addConnectionEventListener(ConnectionEventListener listener) {
        LOG.info("addConnectionEventListener(ConnectionEventListener)");
        eventListener.addConnectorListener(listener);
    }

    public void removeConnectionEventListener(ConnectionEventListener listener) {
        LOG.info("removeConnectionEventListener(ConnectionEventListener)");
        eventListener.removeConnectorListener(listener);
    }

    public XAResource getXAResource() throws ResourceException {
        throw new NotSupportedException("NO_XATRANSACTION");
    }

    public LocalTransaction getLocalTransaction() throws ResourceException {
        throw new NotSupportedException("NO_TRANSACTION");
    }

    public ManagedConnectionMetaData getMetaData() throws ResourceException {
        return this.metaData;
    }

    public void setLogWriter(PrintWriter out) throws ResourceException {
        this.out = out;
    }

    public PrintWriter getLogWriter() throws ResourceException {
        return out;
    }

    public ConnectionRequestInfo getConnectionrequestInfo() {
        return connectionRequestInfo;
    }

    public void removeXmppConnection(AbstractConnectionImpl handle) {
        connectionSet.remove(handle);
    }

    public void sendEvent(int eventType, Exception ex, Object connectionHandle) {
        eventListener.sendEvent(eventType, ex, connectionHandle);
    }

    public void addXmppConnection(AbstractConnectionImpl handle) {
        connectionSet.add(handle);
    }

    private XMPPConnection getConnection(
            XmppConnectionRequestInfo connectionRequestInfo)
            throws ResourceException {
        System.out.println("XmppConnectionRequestInfo::::"+connectionRequestInfo);
        try {
            ConnectionConfiguration config = new ConnectionConfiguration(
                    connectionRequestInfo.getHost(), connectionRequestInfo.getPort().intValue(), connectionRequestInfo.getDomain());
            return new XMPPConnection(config);

        } catch (Exception e) {
            LOG.severe("Could not create connection." + e);
            throw new ResourceException("Could not connect", e);
        }
    }

    private void invalidateXmppConnectionHandles() {
        Iterator it = connectionSet.iterator();
        while (it.hasNext()) {
            XmppConnectionImpl xmppCon =
                    (XmppConnectionImpl) it.next();
            xmppCon.invalidate();
        }
        connectionSet.clear();
    }

    private void checkIfDestroyed()
            throws ResourceException {
        if (destroyed) {
            throw new IllegalStateException("DESTROYED_CONNECTION");
        }
    }

    private void sendMessage(String to, String message)
            throws ResourceException {
        LOG.info("sendMessage(String, String)");
        try {
            ChatManager chatManager = physicalconnection.getChatManager();
            // create the chat
            Chat chat = chatManager.createChat(to, null);
            // send the message
            chat.sendMessage(message);

        } catch (Exception e) {
            LOG.severe("Could not sendMessage." + e);
            throw new ResourceException("Could not sendMessage.", e);
        }
    }

    private void open() throws ResourceException {
        try {
            if (!this.physicalconnection.isConnected()) {
                this.physicalconnection.connect();
                
                addMessagePacketListener(this.physicalconnection,
                        this.messagePacketProcessor, connectionRequestInfo.getUsername());

                addPresencePacketListener(this.physicalconnection,
                        this.presencePacketProcessor,
                        this.connectionRequestInfo.getUsername());

                login(this.physicalconnection,
                        this.connectionRequestInfo.getUsername(),
                        this.connectionRequestInfo.getPassword());

                acceptSubscriptionsManually(this.physicalconnection);
            }
        } catch (Exception e) {
            LOG.severe("Could not open connection." + e);
            throw new ResourceException("Could not open connection", e);
        }
    }
    
    void closePhysicalConnection() throws ResourceException{
        System.out.println("closePhysicalConnection():");
        try {
            if (this.physicalconnection.isConnected()) {
                this.physicalconnection.removePacketListener(this.messagePacketProcessor);
                this.physicalconnection.removePacketListener(this.presencePacketProcessor);
                this.physicalconnection.disconnect();
            }
        } catch (Exception e) {
            throw new ResourceException(e.getMessage());
        }
    }
    private void addMessagePacketListener(XMPPConnection conn,
            PacketListener listener, String toContains)
            throws ResourceException {
        try {
            conn.addPacketListener(listener, new AndFilter(
                    new PacketTypeFilter(Message.class), new ToContainsFilter(
                    toContains)));
        } catch (Exception e) {
            LOG.severe("Could not set PacketListener." + e);
            throw new ResourceException("Could not set PacketListener", e);
        }
    }

    private void addPresencePacketListener(XMPPConnection conn,
            PacketListener listener, String toContains)
            throws ResourceException {
        try {
            conn.addPacketListener(listener, new AndFilter(
                    new PacketTypeFilter(Presence.class), new ToContainsFilter(
                    toContains)));
        } catch (Exception e) {
            LOG.severe("Could not set PacketListener." + e);
            throw new ResourceException("Could not set PacketListener", e);
        }
    }

    private void login(XMPPConnection conn, String username, String password)
            throws ResourceException {
        try {
            conn.login(username, password);
        } catch (XMPPException xe) {
            LOG.severe("Could not login." + xe);
            throw new ResourceException("Could not login", xe);
        }
    }

    private void acceptSubscriptionsManually(XMPPConnection conn)
            throws ResourceException {
        try {
            Roster roster = conn.getRoster();
            roster.setSubscriptionMode(Roster.SubscriptionMode.manual);
        } catch (Exception e) {
            LOG.severe("Could not set subscription mode." + e);
            throw new ResourceException("Could not set subscription mode.", e);
        }
    }

    class MessagePacketProcessor implements PacketListener {

        private final XmppManagedConnection managedconnection;

        public MessagePacketProcessor(XmppManagedConnection managedconnection) {
             this.managedconnection = managedconnection;
        }

        public void processPacket(Packet packet) {
            try {
                if (packet instanceof Message) {
                    Message message = (Message) packet;
                    if (message.getBody() != null
                            && !"".equals(message.getBody())) {
                        XmppMessageInternal xmppMessage = new XmppMessageInternal(managedconnection);
                        xmppMessage.setFrom(message.getFrom());
                        xmppMessage.setTo(message.getTo());
                        xmppMessage.setPacketId(message.getPacketID());
                        xmppMessage.setBody(message.getBody());
                        xmppMessage.setSubject(message.getSubject());
                        xmppMessage.setThreadId(message.getThread());
                        xmppMessage.setType(message.getType().toString());
                        xmppMessage.setConnectionSpec(managedconnection.connectionRequestInfo);
                        dispatchXmppMessage(xmppMessage);
                    }
                    LOG.info("Discarding packet (body is empty): " + packet);
                    LOG.info("message.getBody(): " + message.getBody());
                } else {
                    LOG.info("Discarding packet (not recognized): " + packet);
                }
            } catch (Exception e) {
                LOG.info("Could not process packet received" + e);
            }
        }

        private void dispatchXmppMessage(XmppMessage xmppMessage) {
            try {
                Worker worker = new Worker(xmppMessage);
                mcf.getDispatchThreadExecutor().execute(worker);
                /*new Executor() {

                    public void execute(Runnable command) {
                        command.run();
                    }
                }.execute(worker);*/
            } catch (Exception e) {
                LOG.severe("Could not schedule work" + e);
            }
        }

        class Worker implements Work {

            private final XmppMessage xmppMessage;

            public Worker(XmppMessage xmppMessage) {
                this.xmppMessage = xmppMessage;
            }

            public void release() {
            }

            public void run() {
                System.out.println("messageListeners.size():"+messageListeners.size());
                for (XmppMessageListener listener : messageListeners) {
                    try {
                        listener.onMessage(this.xmppMessage);
                    } catch (Exception e) {
                        LOG.severe("Could not dispatch message to XmppMessageListenerImpl" + e);
                    }
                }
            }
        }
    }

    class PresencePacketProcessor implements PacketListener {

        private final XMPPConnection connection;

        public PresencePacketProcessor(XMPPConnection connection) {
            this.connection = connection;
        }

        public void processPacket(Packet packet) {
            try {
                if (packet instanceof Presence) {
                    Presence presence = (Presence) packet;
                    LOG.info("Received presence packet: " + presence);
                    if (Presence.Type.subscribe.equals(presence.getType())) {
                        addRosterEntry(presence.getFrom());
                    } else if (Presence.Type.unsubscribe.equals(presence.getType())) {
                        removeRosterEntry(presence.getFrom());
                    }
                }
            } catch (Exception e) {
                LOG.severe("Could not process packet received" + e);
            }
        }

        private void addRosterEntry(String jid) {
            try {
                Roster roster = this.connection.getRoster();
                roster.createEntry(jid, jid, null);
                sendMessage(jid, "Welcome!");
            } catch (Exception e) {
                LOG.severe("Could not create roster entry for " + jid + e);
            }
        }

        private void removeRosterEntry(String jid) {
            try {
                Roster roster = this.connection.getRoster();
                RosterEntry entry = roster.getEntry(jid);
                if (entry != null) {
                    roster.removeEntry(entry);
                }
            } catch (Exception e) {
                LOG.severe("Could not remove roster entry for " + jid + e);
            }
        }
    }
    
    class XmppMessageInternal extends XmppMessage{

        XmppManagedConnection managedConnection;

        public XmppMessageInternal(XmppManagedConnection managedConnection) {
            this.managedConnection = managedConnection;
        }

        @Override
        public XmppConnection getCurrentConnectionHandle() throws ResourceException {
            if(managedConnection!=null){
                XmppConnection ihandle=new XmppConnectionInternalImpl(managedConnection);
                managedConnection.addXmppConnection((AbstractConnectionImpl) ihandle);
                return ihandle;
            }
            else return super.getCurrentConnectionHandle();
        }
    }
}
