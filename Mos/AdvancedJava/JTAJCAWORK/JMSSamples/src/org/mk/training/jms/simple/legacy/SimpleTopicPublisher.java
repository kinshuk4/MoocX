package org.mk.training.jms.simple.legacy;

import javax.jms.*;
import javax.naming.*;

public class SimpleTopicPublisher {

    /**
     * Main method.
     *
     * @param args     the topic used by the example and,
     *                 optionally, the number of messages to send
     */
    public static void main(String[] args) {
        String                  topicName = null;
        Context                 jndiContext = null;
        TopicConnectionFactory  topicConnectionFactory = null;
        TopicConnection         topicConnection = null;
        TopicSession            topicSession = null;
        Topic                   topic = null;
        TopicPublisher          topicPublisher = null;
        TextMessage             message = null;
        final int               NUM_MSGS;
        
        if ( (args.length < 1) || (args.length > 2) ) {
            System.out.println("Usage: java " +
                "SimpleTopicPublisher <topic-name> " +
                "[<number-of-messages>]");
            System.exit(1);
        } 
        topicName = new String(args[0]);
        System.out.println("Topic name is " + topicName);
        if (args.length == 2){
            NUM_MSGS = (new Integer(args[1])).intValue();
        } else {
            NUM_MSGS = 1;
        }
        
        /* 
         * Create a JNDI API InitialContext object if none exists
         * yet.
         */
        try {
            jndiContext = new InitialContext();
        } catch (NamingException e) {
            System.out.println("Could not create JNDI API " +
                "context: " + e.toString());
            e.printStackTrace();
            System.exit(1);
        }
        
        /* 
         * Look up connection factory and topic.  If either does
         * not exist, exit.
         */
        try {
            topicConnectionFactory = (TopicConnectionFactory)
                jndiContext.lookup("ConnectionFactory");
            topic = (Topic) jndiContext.lookup(topicName);
        } catch (NamingException e) {
            System.out.println("JNDI API lookup failed: " +
                e.toString());
            e.printStackTrace();
            System.exit(1);
        }

        /*
         * Create connection.
         * Create session from connection; false means session is
         * not transacted.
         * Create publisher and text message.
         * Send messages, varying text slightly.
         * Finally, close connection.
         */
        try {
            topicConnection = 
                topicConnectionFactory.createTopicConnection();
            topicSession = 
                topicConnection.createTopicSession(false, 
                    Session.AUTO_ACKNOWLEDGE);
            topicPublisher = topicSession.createPublisher(topic);
            message = topicSession.createTextMessage();
            for (int i = 0; i < NUM_MSGS; i++) {
                message.setText("This is message " + (i + 1));
                System.out.println("Publishing message: " + 
                    message.getText());
                topicPublisher.publish(message);
            }
        } catch (JMSException e) {
            System.out.println("Exception occurred: " + 
                e.toString());
        } finally {
            if (topicConnection != null) {
                try {
                    topicConnection.close();
                } catch (JMSException e) {}
            }
        }
    }
}