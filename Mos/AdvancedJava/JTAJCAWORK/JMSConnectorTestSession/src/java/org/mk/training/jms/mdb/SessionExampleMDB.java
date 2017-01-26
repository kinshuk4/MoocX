package org.mk.training.jms.mdb;



import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;

@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType",
    propertyValue = "javax.jms.Topic"),
    @ActivationConfigProperty(propertyName = "destination",
    propertyValue = "topic/SessionExample")})
public class SessionExampleMDB implements MessageListener {

    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            System.out.println("SessionExampleMDB().onMessage():::::: " + message);
            try {
                Thread.sleep(50000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("SessionExampleMDB().onMessage()::::::Done Sleeping");
        }
    }
}
