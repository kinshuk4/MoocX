package org.mk.training.jms.advanced;

import javax.naming.*;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

public class DurableSubscriberExample {
	String conFacName = null;

	String topicName = null;

	static int startindex = 0;

	/**
	 * The DurableSubscriber class contains a constructor, a startSubscriber
	 * method, a closeSubscriber method, and a finish method.
	 * 
	 * The class fetches messages asynchronously, using a message listener,
	 * TextListener.
	 */
	public class DurableSubscriber {
		Context jndiContext = null;

		ConnectionFactory topicConnectionFactory = null;

		Connection topicConnection = null;

		Session topicSession = null;

		Destination topic = null;

		MessageConsumer topicSubscriber = null;

		TextListener topicListener = null;

		/**
		 * The TextListener class implements the MessageListener interface by
		 * defining an onMessage method for the DurableSubscriber class.
		 */
		private class TextListener implements MessageListener {
			final SampleUtilities.DoneLatch monitor = new SampleUtilities.DoneLatch();

			/**
			 * Casts the message to a TextMessage and displays its text. A
			 * non-text message is interpreted as the end of the message stream,
			 * and the message listener sets its monitor state to all done
			 * processing messages.
			 * 
			 * @param message
			 *            the incoming message
			 */
			public void onMessage(Message message) {
				if (message instanceof TextMessage) {
					TextMessage msg = (TextMessage) message;

					try {
						System.out.println("SUBSCRIBER: " + "Reading message: "
								+ msg.getText());
					} catch (JMSException e) {
						System.err.println("Exception in " + "onMessage(): "
								+ e.toString());
					}
				} else {
					monitor.allDone();
				}
			}
		}

		/**
		 * Constructor: looks up a connection factory and topic and creates a
		 * connection and session.
		 */
		public DurableSubscriber() {

			/*
			 * Create a JNDI API InitialContext object if none exists yet.
			 */
			try {
				jndiContext = new InitialContext();
			} catch (NamingException e) {
				System.err.println("Could not create JNDI API " + "context: "
						+ e.toString());
				System.exit(1);
			}

			/*
			 * Look up connection factory and topic. If either does not exist,
			 * exit.
			 */
			try {
				topicConnectionFactory = (ConnectionFactory) jndiContext
						.lookup(conFacName);
			} catch (NamingException e) {
				System.err.println("JNDI API lookup failed: " + e.toString());
				System.exit(1);
			}

			try {
				topicConnection = topicConnectionFactory.createConnection();
				topicSession = topicConnection.createSession(false,
						Session.AUTO_ACKNOWLEDGE);
				topic = SampleUtilities.getTopic(topicName, topicSession);
			} catch (Exception e) {
				System.err.println("Connection problem: " + e.toString());
				if (topicConnection != null) {
					try {
						topicConnection.close();
					} catch (JMSException ee) {
					}
				}
				System.exit(1);
			}
		}

		/**
		 * Stops connection, then creates durable subscriber, registers message
		 * listener (TextListener), and starts message delivery; listener
		 * displays the messages obtained.
		 */
		public void startSubscriber() {
			try {
				System.out.println("Starting subscriber");
				topicConnection.stop();
				topicSubscriber = topicSession.createDurableSubscriber(
						(Topic) topic, "MakeItLast");
				/*
				 * Uncomment the below lines to get destination independant
				 * behavior. But specific functionalities will not be available
				 * unless you downcast. Examples are durable subscription for
				 * topics,queue browsing for queues
				 */
				/*
				 * topicSubscriber = topicSession.createConsumer(topic);
				 */
				topicListener = new TextListener();
				topicSubscriber.setMessageListener(topicListener);
				topicConnection.start();
			} catch (JMSException e) {
				System.err.println("Exception occurred: " + e.toString());
			}
		}

		/**
		 * Blocks until publisher issues a control message indicating end of
		 * publish stream, then closes subscriber.
		 */
		public void closeSubscriber() {
			try {
				topicListener.monitor.waitTillDone();
				System.out.println("Closing subscriber");
				topicSubscriber.close();
			} catch (JMSException e) {
				System.err.println("Exception occurred: " + e.toString());
			}
		}

		/**
		 * Closes the connection.
		 */
		public void finish() {
			if (topicConnection != null) {
				try {
					System.out.println("Unsubscribing from "
							+ "durable subscription");
					topicSession.unsubscribe("MakeItLast");
					topicConnection.close();
				} catch (JMSException e) {
				}
			}
		}
	}

	/**
	 * The MultiplePublisher class publishes several messages to a topic. It
	 * contains a constructor, a publishMessages method, and a finish method.
	 */
	public class MultiplePublisher {
		Connection topicConnection = null;

		Session topicSession = null;

		Destination topic = null;

		MessageProducer topicPublisher = null;

		/**
		 * Constructor: looks up a connection factory and topic and creates a
		 * connection, session, and publisher.
		 */
		public MultiplePublisher() {
			ConnectionFactory topicConnectionFactory = null;

			try {
				topicConnectionFactory = SampleUtilities
						.getTopicConnectionFactory();
				topicConnection = topicConnectionFactory.createConnection();
				topicSession = topicConnection.createSession(false,
						Session.AUTO_ACKNOWLEDGE);
				topic = SampleUtilities.getTopic(topicName, topicSession);
				topicPublisher = topicSession.createProducer(topic);
			} catch (Exception e) {
				System.err.println("Connection problem: " + e.toString());
				if (topicConnection != null) {
					try {
						topicConnection.close();
					} catch (JMSException ee) {
					}
				}
				System.exit(1);
			}
		}

		/**
		 * Creates text message. Sends some messages, varying text slightly.
		 * Messages must be persistent.
		 */
		public void publishMessages() {
			TextMessage message = null;
			int i;
			final int NUMMSGS = 3;
			final String MSG_TEXT = new String("Here is a message");

			try {
				message = topicSession.createTextMessage();
				for (i = startindex; i < startindex + NUMMSGS; i++) {
					message.setText(MSG_TEXT + " " + (i + 1));
					System.out.println("PUBLISHER: Publishing " + "message: "
							+ message.getText());
					topicPublisher.send(topic, message);
				}

				/*
				 * Send a non-text control message indicating end of messages.
				 */
				topicPublisher.send(topic, topicSession.createMessage());
				startindex = i;
			} catch (JMSException e) {
				System.err.println("Exception occurred: " + e.toString());
			}
		}

		/**
		 * Closes the connection.
		 */
		public void finish() {
			if (topicConnection != null) {
				try {
					topicConnection.close();
				} catch (JMSException e) {
				}
			}
		}
	}

	/**
	 * Instantiates the subscriber and publisher classes. Starts the subscriber;
	 * the publisher publishes some messages. Closes the subscriber; while it is
	 * closed, the publisher publishes some more messages. Restarts the
	 * subscriber and fetches the messages. Finally, closes the connections.
	 */
	public void run_program() {
		DurableSubscriber durableSubscriber = new DurableSubscriber();
		MultiplePublisher multiplePublisher = new MultiplePublisher();

		durableSubscriber.startSubscriber();
		multiplePublisher.publishMessages();
		durableSubscriber.closeSubscriber();
		multiplePublisher.publishMessages();
		durableSubscriber.startSubscriber();
		durableSubscriber.closeSubscriber();
		multiplePublisher.finish();
		durableSubscriber.finish();
	}

	/**
	 * Reads the topic name from the command line, then calls the run_program
	 * method.
	 * 
	 * @param args
	 *            the topic used by the example
	 */
	public static void main(String[] args) {
		DurableSubscriberExample dse = new DurableSubscriberExample();

		if (args.length != 2) {
			System.out.println("Usage: java " + "DurableSubscriberExample "
					+ "<connection_factory_name> <topic_name>");
			System.exit(1);
		}
		dse.conFacName = new String(args[0]);
		System.out.println("Connection factory name is " + dse.conFacName);
		dse.topicName = new String(args[1]);
		System.out.println("Topic name is " + dse.topicName);

		dse.run_program();
	}
}