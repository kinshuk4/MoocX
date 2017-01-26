package org.mk.training.jms.simple.legacy;

import javax.jms.*;

public class TextListener implements MessageListener {

    /**
     * Casts the message to a TextMessage and displays its text.
     *
     * @param message     the incoming message
     */
    public void onMessage(Message message) {
        TextMessage msg = null;
            
        try {
        	Thread.sleep(10000);
            if (message instanceof TextMessage) {
                msg = (TextMessage) message;
                System.out.println("Reading message:: "+ msg.getText());
            } 
        } catch (JMSException e) {
            System.out.println("JMSException in onMessage(): " +
                e.toString());
        } catch (Throwable t) {
            System.out.println("Exception in onMessage():" +
                t.getMessage());
        }
    }
}