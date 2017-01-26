package org.mk.training.jms.advanced;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.*;

public class SampleUtilities {
	public static final String QUEUECONFAC = "QueueConnectionFactory";

	public static final String TOPICCONFAC = "TopicConnectionFactory";

	private static Context jndiContext = null;

	/**
	 * Returns a QueueConnectionFactory object.
	 * 
	 * @return a QueueConnectionFactory object
	 * @throws javax.naming.NamingException
	 *             (or other exception) if name cannot be found
	 */
	public static ConnectionFactory getQueueConnectionFactory()
			throws Exception {
		return (ConnectionFactory) jndiLookup(QUEUECONFAC);
	}

	/**
	 * Returns a TopicConnectionFactory object.
	 * 
	 * @return a TopicConnectionFactory object
	 * @throws javax.naming.NamingException
	 *             (or other exception) if name cannot be found
	 */
	public static ConnectionFactory getTopicConnectionFactory()
			throws Exception {
		return (ConnectionFactory) jndiLookup(TOPICCONFAC);
	}

	/**
	 * Returns a Queue object.
	 * 
	 * @param name
	 *            String specifying queue name
	 * @param session
	 *            a QueueSession object
	 * 
	 * @return a Queue object
	 * @throws javax.naming.NamingException
	 *             (or other exception) if name cannot be found
	 */
	public static Destination getQueue(String name, Session session)
			throws Exception {
		return (Destination) jndiLookup(name);
	}

	/**
	 * Returns a Topic object.
	 * 
	 * @param name
	 *            String specifying topic name
	 * @param session
	 *            a TopicSession object
	 * 
	 * @return a Topic object
	 * @throws javax.naming.NamingException
	 *             (or other exception) if name cannot be found
	 */
	public static Destination getTopic(String name, Session session)
			throws Exception {
		return (Destination) jndiLookup(name);
	}

	/**
	 * Creates a JNDI API InitialContext object if none exists yet. Then looks
	 * up the string argument and returns the associated object.
	 * 
	 * @param name
	 *            the name of the object to be looked up
	 * 
	 * @return the object bound to name
	 * @throws javax.naming.NamingException
	 *             (or other exception) if name cannot be found
	 */
	public static Object jndiLookup(String name) throws NamingException {
		Object obj = null;

		if (jndiContext == null) {
			try {
				jndiContext = new InitialContext();
			} catch (NamingException e) {
				System.err.println("Could not create JNDI API " + "context: "
						+ e.toString());
				throw e;
			}
		}
		try {
			obj = jndiContext.lookup(name);
		} catch (NamingException e) {
			System.err.println("JNDI API lookup failed: " + e.toString());
			throw e;
		}
		return obj;
	}

	/**
	 * Waits for 'count' messages on controlQueue before continuing. Called by a
	 * publisher to make sure that subscribers have started before it begins
	 * publishing messages.
	 * 
	 * If controlQueue does not exist, the method throws an exception.
	 * 
	 * @param prefix
	 *            prefix (publisher or subscriber) to be displayed
	 * @param controlQueueName
	 *            name of control queue
	 * @param count
	 *            number of messages to receive
	 */
	public static void receiveSynchronizeMessages(String prefix,
			String controlQueueName, int count) throws Exception {
		ConnectionFactory queueConnectionFactory = null;
		Connection queueConnection = null;
		Session queueSession = null;
		Destination controlQueue = null;
		MessageConsumer queueReceiver = null;

		try {
			queueConnectionFactory = SampleUtilities
					.getQueueConnectionFactory();
			queueConnection = queueConnectionFactory.createConnection();
			queueSession = queueConnection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);
			controlQueue = getQueue(controlQueueName, queueSession);
			queueConnection.start();
		} catch (Exception e) {
			System.err.println("Connection problem: " + e.toString());
			if (queueConnection != null) {
				try {
					queueConnection.close();
				} catch (JMSException ee) {
				}
			}
			throw e;
		}

		try {
			System.out.println(prefix + "Receiving synchronize messages from "
					+ controlQueueName + "; count = " + count);
			queueReceiver = queueSession.createConsumer(controlQueue);
			while (count > 0) {
				queueReceiver.receive();
				count--;
				System.out.println(prefix + "Received synchronize message; "
						+ " expect " + count + " more");
			}
		} catch (JMSException e) {
			System.err.println("Exception occurred: " + e.toString());
			throw e;
		} finally {
			if (queueConnection != null) {
				try {
					queueConnection.close();
				} catch (JMSException e) {
				}
			}
		}
	}

	/**
	 * Sends a message to controlQueue. Called by a subscriber to notify a
	 * publisher that it is ready to receive messages.
	 * <p>
	 * If controlQueue doesn't exist, the method throws an exception.
	 * 
	 * @param prefix
	 *            prefix (publisher or subscriber) to be displayed
	 * @param controlQueueName
	 *            name of control queue
	 */
	public static void sendSynchronizeMessage(String prefix,
			String controlQueueName) throws Exception {
		ConnectionFactory queueConnectionFactory = null;
		Connection queueConnection = null;
		Session queueSession = null;
		Destination controlQueue = null;
		MessageProducer queueSender = null;
		TextMessage message = null;

		try {
			queueConnectionFactory = SampleUtilities
					.getQueueConnectionFactory();
			queueConnection = queueConnectionFactory.createConnection();
			queueSession = queueConnection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);
			controlQueue = getQueue(controlQueueName, queueSession);
		} catch (Exception e) {
			System.err.println("Connection problem: " + e.toString());
			if (queueConnection != null) {
				try {
					queueConnection.close();
				} catch (JMSException ee) {
				}
			}
			throw e;
		}

		try {
			queueSender = queueSession.createProducer(controlQueue);
			message = queueSession.createTextMessage();
			message.setText("synchronize");
			System.out.println(prefix + "Sending synchronize message to "
					+ controlQueueName);
			queueSender.send(message);
		} catch (JMSException e) {
			System.err.println("Exception occurred: " + e.toString());
			throw e;
		} finally {
			if (queueConnection != null) {
				try {
					queueConnection.close();
				} catch (JMSException e) {
				}
			}
		}
	}

	/**
	 * Monitor class for asynchronous examples. Producer signals end of message
	 * stream; listener calls allDone() to notify consumer that the signal has
	 * arrived, while consumer calls waitTillDone() to wait for this
	 * notification.
	 */
	static public class DoneLatch {
		boolean done = false;

		/**
		 * Waits until done is set to true.
		 */
		public void waitTillDone() {
			synchronized (this) {
				while (!done) {
					try {
						this.wait();
					} catch (InterruptedException ie) {
					}
				}
			}
		}

		/**
		 * Sets done to true.
		 */
		public void allDone() {
			synchronized (this) {
				done = true;
				this.notify();
			}
		}
	}

}
