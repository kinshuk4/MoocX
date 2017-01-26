package org.mk.training.jms.simple.legacy;

import java.io.IOException;
import java.io.InputStreamReader;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class SimpleAsynchronousQueueReceiver{

	/**
     * Main method.
     *
     * @param args     the queue used by the example
     */
    public static void main(String[] args) {
        String                  queueName = null;
        Context                 jndiContext = null;
        QueueConnectionFactory  queueConnectionFactory = null;
        QueueConnection         queueConnection = null;
        QueueSession            queueSession = null;
        Queue                   queue = null;
        QueueReceiver           queueReceiver = null;
        TextMessage             message = null;
        InputStreamReader       inputStreamReader=null;
        char                    answer='\0';
                
        /*
         * Read queue name from command line and display it.
         */
        if (args.length != 1) {
            System.out.println("Usage: java " +
                "SimpleQueueReceiver <queue-name>");
            System.exit(1);
        }
        queueName = new String(args[0]);
        System.out.println("Queue name is " + queueName);
        
        /* 
         * Create a JNDI API InitialContext object if none exists
         * yet.
         */
        try {
            jndiContext = new InitialContext();
        } catch (NamingException e) {
            System.out.println("Could not create JNDI API " +
                "context: " + e.toString());
            System.exit(1);
        }
        
        /* 
         * Look up connection factory and queue.  If either does
         * not exist, exit.
         */
        try {
            queueConnectionFactory = (QueueConnectionFactory)
                jndiContext.lookup("ConnectionFactory");
            System.out.println(queueConnectionFactory);
            queue = (Queue) jndiContext.lookup(queueName);
        } catch (NamingException e) {
            System.out.println("JNDI API lookup failed: " +
                e.toString());
            System.exit(1);
        }

        /*
         * Create connection.
         * Create session from connection; false means session is
         * not transacted.
         * Create receiver, then start message delivery.
         * Receive all text messages from queue until
         * a non-text message is received indicating end of
         * message stream.
         * Close connection.
         */
        try {
            queueConnection = 
                queueConnectionFactory.createQueueConnection();
            System.out.println(queueConnection);
            queueSession = 
                queueConnection.createQueueSession(false, 
                    Session.AUTO_ACKNOWLEDGE);
            queueReceiver = queueSession.createReceiver(queue);
            queueReceiver.setMessageListener(new TextListener());
            queueConnection.start();
            inputStreamReader = new InputStreamReader(System.in);
            System.out.println("Type q/Q for quiting...");
            while (!((answer == 'q') || (answer == 'Q'))) {
                try {
                    answer = (char) inputStreamReader.read();
                } catch (IOException e) {
                    System.out.println("I/O exception: " 
                        + e.toString());
                }
            }
            
        } catch (JMSException e) {
            System.out.println("Exception occurred: " + 
                e.toString());
        } finally {
            if (queueConnection != null) {
                try {
                    queueConnection.close();
                } catch (JMSException e) {}
            }
        }
    }

}
