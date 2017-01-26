package com.grapevineim.xmpp.ra.inbound;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.resource.spi.endpoint.MessageEndpointFactory;
import javax.resource.spi.work.WorkException;
import javax.resource.spi.work.WorkManager;

import com.grapevineim.xmpp.XmppConnection;
import com.grapevineim.xmpp.XmppConnectionFactory;
import com.grapevineim.xmpp.XmppMessage;
import com.grapevineim.xmpp.XmppMessageListener;
import com.grapevineim.xmpp.ra.outbound.XmppConnectionRequestInfo;
import java.util.logging.Logger;

public class XmppMessageListenerImpl implements XmppMessageListener {

    private static final Logger LOG = Logger.getLogger(XmppMessageListenerImpl.class.getName());
    private static final String PRESENCE_STATUS_AVAILABLE = "available";
    private static final String PRESENCE_STATUS_UNAVAILABLE = "unavailable";
    private static final String INSTRUCTIONS = "Type 'help' for a list of commands";
    private final MessageEndpointFactory messageEndpointFactory;
    private final WorkManager workManager;
    private final XmppConnection connection;

    public XmppMessageListenerImpl(WorkManager workManager,
            MessageEndpointFactory messageEndpointFactory, ActivationSpecImpl as)
            throws Exception {
        this.workManager = workManager;
        this.messageEndpointFactory = messageEndpointFactory;
        this.connection = getConnection(as);
        //this.connection.open();
        this.connection.addMessageListener(this);
        this.connection.setPresence(PRESENCE_STATUS_AVAILABLE, INSTRUCTIONS);
    }

    public void onMessage(XmppMessage xmppMessage) {
        try {
            workManager.scheduleWork(new DeliveryThread(messageEndpointFactory,
                    xmppMessage));
        } catch (WorkException we) {
            LOG.severe("Could not schedule work" + we);
        }
    }

    /**
     * By connection in this fashion, it will consumer a ManagedConnection from the pool. This is what I want.
     * @param activationSpec
     * @return
     * @throws Exception
     */
    private XmppConnection getConnection(ActivationSpecImpl activationSpec)
            throws Exception {
        try {
            Context ctx = new InitialContext();
            System.out.println("looking up for"+":XmppMessagingConnector");
            Object o = ctx.lookup("java:eis/ra/XmppMessagingConnector");
            //System.out.println("IMPL::CL" + o.getClass().getClassLoader() + " CLH:" + o.getClass().getClassLoader().hashCode() + " PCL" + o.getClass().getClassLoader().getParent() + " PLCH:" + o.getClass().getClassLoader().getParent().hashCode());
            //System.out.println("INTF::CL" + XmppConnectionFactory.class.getClassLoader() + " CLH:" + XmppConnectionFactory.class.getClassLoader().hashCode() + " PCL" + XmppConnectionFactory.class.getClassLoader().getParent() + " PLCH:" + XmppConnectionFactory.class.getClassLoader().getParent().hashCode());
            //System.out.println("XmppConnectionFactory::"+XmppConnectionFactory.class.getClassLoader().getParent());
            //System.out.println("Are Classloaders EQUAL::::"+XmppConnectionFactory.class.getClassLoader().equals(o.getClass().getClassLoader()));
            XmppConnectionFactory connectionFactory = (XmppConnectionFactory) o;
            //XmppConnectionFactory connectionFactory = (XmppConnectionFactory) ctx
            //		.lookup("eis/ra/XmppMessagingConnector");
            XmppConnectionRequestInfo connectionRequestInfo = new XmppConnectionRequestInfo();
            connectionRequestInfo.setUsername(activationSpec.getUsername());
            connectionRequestInfo.setPassword(activationSpec.getPassword());
            connectionRequestInfo.setHost(activationSpec.getHost());
            connectionRequestInfo.setPort(activationSpec.getPort());
            connectionRequestInfo.setDomain(activationSpec.getDomain());
            return connectionFactory.createConnection(connectionRequestInfo);
        } catch (Exception e) {
            LOG.severe("Could not get connection" + e);
            throw e;
        }
    }

    public void cleanup() throws Exception {
        System.out.println("XmppMessageListenerImpl.cleanup()");
        try {
            this.connection.setPresence(PRESENCE_STATUS_UNAVAILABLE, "");
            this.connection.removeMessageListener(this);
            this.connection.close();
        } catch (Exception e) {
            LOG.severe("Could not cleanup" + e);
            throw e;
        }
    }
}
