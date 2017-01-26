/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mk.training.harness;

import com.grapevineim.xmpp.XmppConnection;
import com.grapevineim.xmpp.XmppConnectionFactory;
import com.grapevineim.xmpp.XmppConnectionSpec;
import com.grapevineim.xmpp.XmppMessage;
import com.grapevineim.xmpp.XmppMessageListener;
import com.grapevineim.xmpp.ra.outbound.XmppConnectionRequestInfo;
import com.grapevineim.xmpp.ra.outbound.XmppManagedConnectionFactory;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.resource.ResourceException;

/**
 *
 * @author veenamohitkumar
 */
public class SendingMain {

    public static void main(String args[]) throws Exception {
        XmppManagedConnectionFactory managedfactory = new XmppManagedConnectionFactory();
        XmppConnectionFactory confaq = (XmppConnectionFactory) managedfactory.createConnectionFactory();
        XmppConnection con = confaq.createConnection(new XmppConnectionRequestInfo("talk.google.com", 5222, "gmail.com", "org.mk.training", "beginnersmind"));
        //con.open();
        con.addMessageListener(new XmppMessageListener() {

            public void onMessage(XmppMessage arg0) throws ResourceException {
                try {
                    System.out.println("Message::::"+arg0.getBody());
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(SendingMain.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("Done Sleeping"+Thread.currentThread().getName());
            }
        });
        XmppMessage xmppMessage = new XmppMessage();
        try {
            xmppMessage.setBody("ECHOED:" + "HELLO BUGGER");
            xmppMessage.setTo("mohit.riverstone@gmail.com");
            con.sendMessage(xmppMessage);
        } finally {
            con.close();
        }
    }
}
