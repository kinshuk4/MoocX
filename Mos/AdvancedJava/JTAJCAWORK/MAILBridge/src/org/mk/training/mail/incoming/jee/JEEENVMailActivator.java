/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mk.training.mail.incoming.jee;

import javax.mail.Message;
import javax.resource.spi.endpoint.MessageEndpoint;
import javax.resource.spi.endpoint.MessageEndpointFactory;
import javax.resource.spi.work.Work;
import org.mk.training.mail.incoming.AbstractMailActivator;
import org.mk.training.mail.incoming.MailListener;
import org.mk.training.mail.incoming.MailReceiver;

/**
 *
 * @author veenamohitkumar
 */
public class JEEENVMailActivator extends AbstractMailActivator implements Work {

    MessageEndpointFactory endpointFactory;

    public JEEENVMailActivator(MailReceiver mailSpec, MessageEndpointFactory endpointFactory) {
        super(mailSpec);
        this.endpointFactory = endpointFactory;
    }

    @Override
    protected void deliverMsg(Message msg) {
        MessageEndpoint endpoint = null;
        try {
            endpoint = endpointFactory.createEndpoint(null);
            if (endpoint != null) {
                MailListener listener = (MailListener) endpoint;
                System.out.println("MailActivation.beforeonMessage():::::::::::::::::");
                listener.onMessage(msg);
                System.out.println("MailActivation.afteronMessage():::::::::::::::::");
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            if (endpoint != null) {
                endpoint.release();
            }
        }
    }

    public void release() {
        this.shutdown();
    }
}
