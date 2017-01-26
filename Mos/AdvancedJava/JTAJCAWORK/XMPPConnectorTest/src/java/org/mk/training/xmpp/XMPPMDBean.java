package org.mk.training.xmpp;

import com.grapevineim.xmpp.XmppConnection;
import com.grapevineim.xmpp.XmppConnectionFactory;
import com.grapevineim.xmpp.XmppMessage;
import com.grapevineim.xmpp.XmppMessageListener;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.resource.ResourceException;

@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "host", propertyValue = "talk.google.com"),
    @ActivationConfigProperty(propertyName = "port", propertyValue = "5222"),
    @ActivationConfigProperty(propertyName = "domain", propertyValue = "gmail.com"),
    @ActivationConfigProperty(propertyName = "username", propertyValue = "org.mk.training"),
    @ActivationConfigProperty(propertyName = "password", propertyValue = "beginnersmind")})
public class XMPPMDBean implements XmppMessageListener {
    /* This also works but creates another connection.So typically to be used in
     * a component which is not an XMPPMDB or connection to a different XMPP server than
     * the incomming connection.
    @Resource(mappedName = "java:eis/ra/XmppMessagingConnector",shareable=true)
    XmppConnectionFactory connectionFactory;*/

    public void onMessage(XmppMessage xmppMessage) throws ResourceException {
        System.out.println("message" + xmppMessage);
        System.out.println("message:" + xmppMessage.getBody());
        XmppConnection connection = xmppMessage.getCurrentConnectionHandle();
        //XmppConnection connection = connectionFactory.createConnection();
        System.out.println("bean connection:::::" + connection);
        try {
            xmppMessage.setBody("ECHOED:" + xmppMessage.getBody());
            xmppMessage.setPacketId(null);
            String to = xmppMessage.getFrom();
            xmppMessage.setFrom(xmppMessage.getTo());
            xmppMessage.setTo(to);
            connection.sendMessage(xmppMessage);
            //xmppMessage.setFrom(xmppMessage.getTo());
            //xmppMessage.setTo("org.mk.training.jca@gmail.com");
            //connection.sendMessage(xmppMessage);
        } finally {
            System.out.println("connection.close():");
            connection.close();
        }
    }
}
