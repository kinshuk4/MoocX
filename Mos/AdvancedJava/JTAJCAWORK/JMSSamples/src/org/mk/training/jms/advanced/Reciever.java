
package org.mk.training.jms.advanced;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.jms.Destination;
import javax.jms.ObjectMessage;
import javax.jms.ConnectionFactory;
import javax.jms.Connection;
import javax.jms.Session;
import javax.jms.Queue;
import javax.jms.MessageConsumer;
import javax.jms.JMSException;

import javax.naming.Context;
import javax.naming.InitialContext;

public class Reciever {
	public Reciever(String factoryName, String destinationName,
			boolean transacted) throws Exception {
		Context jndiContext = getInitialContext();
		ConnectionFactory factory = (ConnectionFactory) jndiContext
				.lookup(factoryName);

		Destination dest = (Destination) jndiContext.lookup(destinationName);

		Connection connect = factory.createConnection();
		Session session = connect.createSession(transacted,
				Session.AUTO_ACKNOWLEDGE);
		MessageConsumer receiver = session.createConsumer(dest);
		receiver.setMessageListener(new TextListener());

		System.out.println("Listening for messages on Destination:: " + dest);
		connect.start();
	}

	public static void main(String[] args) {
		String destinationName = null;
		String factoryName = null;
		String transacted = null;
		if (args.length != 2) {
			System.out
					.println("Usage: java "
							+ "SimpleQueueReceiver <factory-name> <destination-name> <transacted>");
			System.exit(1);
		}
		destinationName = new String(args[0]);
		factoryName = new String(args[1]);
		System.out.println("Queue name is " + factoryName);
	}

	public static Context getInitialContext()
			throws javax.naming.NamingException {
		return new InitialContext();
	}
}
