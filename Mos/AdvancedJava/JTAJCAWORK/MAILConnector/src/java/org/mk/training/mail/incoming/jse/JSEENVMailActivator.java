/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mk.training.mail.incoming.jse;

import org.mk.training.mail.incoming.*;
import javax.mail.Message;

/**
 *
 * @author veenamohitkumar
 */
public class JSEENVMailActivator extends AbstractMailActivator {

    MailListener mailListener;
    ListenerFactory factory;
    private final Object lock = new Object();

    public JSEENVMailActivator(MailReceiver mailSpec, MailListener mailListener, ListenerFactory factory) {
        super(mailSpec);
        if (mailListener != null) {
            this.mailListener = mailListener;
        }
        if (factory != null) {
            this.factory = factory;
        }
    }

    @Override
    protected void deliverMsg(Message message) {
        if (mailListener != null) {
            synchronized (lock) {
                System.out.println("Sync Before::::");
                mailListener.onMessage(message);
                System.out.println("Sync After::::");
            }
        } else {
            MailListener listener = (MailListener) factory.createListener();
            System.out.println("NOSync Before::::");
            listener.onMessage(message);
            System.out.println("NOSync After::::");
        }
    }
}
