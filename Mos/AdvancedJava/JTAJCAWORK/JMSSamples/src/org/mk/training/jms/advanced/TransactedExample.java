package org.mk.training.jms.advanced;

import java.util.*;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

public class TransactedExample {
	public static String vendorOrderQueueName = null;

	public static String retailerConfirmQueueName = null;

	public static String monitorOrderQueueName = null;

	public static String storageOrderQueueName = null;

	public static String vendorConfirmQueueName = null;

	/**
	 * The Retailer class orders a number of computers by sending a message to a
	 * vendor. It then waits for the order to be confirmed.
	 * 
	 * In this example, the Retailer places two orders, one for the quantity
	 * specified on the command line and one for twice that number.
	 * 
	 * This class does not use transactions.
	 */
	public static class Retailer extends Thread {
		int quantity = 0;

		/**
		 * Constructor. Instantiates the retailer with the quantity of computers
		 * being ordered.
		 * 
		 * @param q
		 *            the quantity specified in the program arguments
		 */
		public Retailer(int q) {
			quantity = q;
		}

		/**
		 * Runs the thread.
		 */
		public void run() {
			ConnectionFactory queueConnectionFactory = null;
			Connection queueConnection = null;
			Session queueSession = null;
			Destination vendorOrderQueue = null;
			Destination retailerConfirmQueue = null;
			MessageProducer queueSender = null;
			MapMessage outMessage = null;
			MessageConsumer orderConfirmReceiver = null;
			MapMessage inMessage = null;

			try {
				queueConnectionFactory = SampleUtilities
						.getQueueConnectionFactory();
				queueConnection = queueConnectionFactory.createConnection();
				queueSession = queueConnection.createSession(false,
						Session.AUTO_ACKNOWLEDGE);
				vendorOrderQueue = SampleUtilities.getQueue(
						vendorOrderQueueName, queueSession);
				retailerConfirmQueue = SampleUtilities.getQueue(
						retailerConfirmQueueName, queueSession);
			} catch (Exception e) {
				System.err.println("Connection problem: " + e.toString());
				System.err.println("Program assumes five "
						+ "queues named A B C D E");
				if (queueConnection != null) {
					try {
						queueConnection.close();
					} catch (JMSException ee) {
					}
				}
				System.exit(1);
			}

			/*
			 * Create non-transacted session and sender for vendor order queue.
			 * Create message to vendor, setting item and quantity values. Send
			 * message. Create receiver for retailer confirmation queue. Get
			 * message and report result. Send an end-of-message-stream message
			 * so vendor will stop processing orders.
			 */
			try {
				queueSender = queueSession.createProducer(vendorOrderQueue);
				outMessage = queueSession.createMapMessage();
				outMessage.setString("Item", "Computer(s)");
				outMessage.setInt("Quantity", quantity);
				outMessage.setJMSReplyTo(retailerConfirmQueue);
				queueSender.send(outMessage);
				System.out.println("Retailer: ordered " + quantity
						+ " computer(s)");

				orderConfirmReceiver = queueSession
						.createConsumer(retailerConfirmQueue);
				queueConnection.start();
				inMessage = (MapMessage) orderConfirmReceiver.receive();
				if (inMessage.getBoolean("OrderAccepted") == true) {
					System.out.println("Retailer: Order filled");
				} else {
					System.out.println("Retailer: Order not " + "filled");
				}

				System.out.println("Retailer: placing another " + "order");
				outMessage.setInt("Quantity", quantity * 2);
				queueSender.send(outMessage);
				System.out.println("Retailer: ordered "
						+ outMessage.getInt("Quantity") + " computer(s)");
				inMessage = (MapMessage) orderConfirmReceiver.receive();
				if (inMessage.getBoolean("OrderAccepted") == true) {
					System.out.println("Retailer: Order filled");
				} else {
					System.out.println("Retailer: Order not " + "filled");
				}

				/*
				 * Send a non-text control message indicating end of messages.
				 */
				queueSender.send(queueSession.createMessage());
			} catch (Exception e) {
				System.err.println("Retailer: Exception " + "occurred: "
						+ e.toString());
				e.printStackTrace();
			} finally {
				if (queueConnection != null) {
					try {
						queueConnection.close();
					} catch (JMSException e) {
					}
				}
			}
		}
	}

	/**
	 * The Vendor class uses one transaction to receive the computer order from
	 * the retailer and order the needed number of monitors and disk drives from
	 * its suppliers. At random intervals, it throws an exception to simulate a
	 * database problem and cause a rollback.
	 * 
	 * The class uses an asynchronous message listener to process replies from
	 * suppliers. When all outstanding supplier inquiries complete, it sends a
	 * message to the Retailer accepting or refusing the order.
	 */
	public static class Vendor extends Thread {
		Random rgen = new Random();

		int throwException = 1;

		/**
		 * Runs the thread.
		 */
		public void run() {
			ConnectionFactory queueConnectionFactory = null;
			Connection queueConnection = null;
			Session queueSession = null;
			Session asyncQueueSession = null;
			Destination vendorOrderQueue = null;
			Destination monitorOrderQueue = null;
			Destination storageOrderQueue = null;
			Destination vendorConfirmQueue = null;
			MessageConsumer vendorOrderQueueReceiver = null;
			MessageProducer monitorOrderQueueSender = null;
			MessageProducer storageOrderQueueSender = null;
			MapMessage orderMessage = null;
			MessageConsumer vendorConfirmQueueReceiver = null;
			VendorMessageListener listener = null;
			Message inMessage = null;
			MapMessage vendorOrderMessage = null;
			Message endOfMessageStream = null;
			Order order = null;
			int quantity = 0;

			try {
				queueConnectionFactory = SampleUtilities
						.getQueueConnectionFactory();
				queueConnection = queueConnectionFactory.createConnection();
				queueSession = queueConnection.createSession(true, 0);
				asyncQueueSession = queueConnection.createSession(true, 0);
				vendorOrderQueue = SampleUtilities.getQueue(
						vendorOrderQueueName, queueSession);
				monitorOrderQueue = SampleUtilities.getQueue(
						monitorOrderQueueName, queueSession);
				storageOrderQueue = SampleUtilities.getQueue(
						storageOrderQueueName, queueSession);
				vendorConfirmQueue = SampleUtilities.getQueue(
						vendorConfirmQueueName, queueSession);
			} catch (Exception e) {
				System.err.println("Connection problem: " + e.toString());
				System.err.println("Program assumes five "
						+ "queues named A B C D E");
				if (queueConnection != null) {
					try {
						queueConnection.close();
					} catch (JMSException ee) {
					}
				}
				System.exit(1);
			}

			try {
				/*
				 * Create receiver for vendor order queue, sender for supplier
				 * order queues, and message to send to suppliers.
				 */
				vendorOrderQueueReceiver = queueSession
						.createConsumer(vendorOrderQueue);
				monitorOrderQueueSender = queueSession
						.createProducer(monitorOrderQueue);
				storageOrderQueueSender = queueSession
						.createProducer(storageOrderQueue);
				orderMessage = queueSession.createMapMessage();

				/*
				 * Configure an asynchronous message listener to process
				 * supplier replies to inquiries for parts to fill order. Start
				 * delivery.
				 */
				vendorConfirmQueueReceiver = asyncQueueSession
						.createConsumer(vendorConfirmQueue);
				listener = new VendorMessageListener(asyncQueueSession, 2);
				vendorConfirmQueueReceiver.setMessageListener(listener);
				queueConnection.start();

				/*
				 * Process orders in vendor order queue. Use one transaction to
				 * receive order from order queue and send messages to
				 * suppliers' order queues to order components to fulfill the
				 * order placed with the vendor.
				 */
				while (true) {
					try {

						// Receive an order from a retailer.
						inMessage = vendorOrderQueueReceiver.receive();
						if (inMessage instanceof MapMessage) {
							vendorOrderMessage = (MapMessage) inMessage;
						} else {
							/*
							 * Message is an end-of-message- stream message from
							 * retailer. Send similar messages to suppliers,
							 * then break out of processing loop.
							 */
							endOfMessageStream = queueSession.createMessage();
							endOfMessageStream
									.setJMSReplyTo(vendorConfirmQueue);
							monitorOrderQueueSender.send(endOfMessageStream);
							storageOrderQueueSender.send(endOfMessageStream);
							queueSession.commit();
							break;
						}

						/*
						 * A real application would check an inventory database
						 * and order only the quantities needed. Throw an
						 * exception every few times to simulate a database
						 * concurrent-access exception and cause a rollback.
						 */
						if (rgen.nextInt(3) == throwException) {
							throw new JMSException("Simulated "
									+ "database concurrent access "
									+ "exception");
						}

						/*
						 * Record retailer order as a pending order.
						 */
						order = new Order(vendorOrderMessage);

						/*
						 * Set order number and reply queue for outgoing
						 * message.
						 */
						orderMessage.setInt("VendorOrderNumber",
								order.orderNumber);
						orderMessage.setJMSReplyTo(vendorConfirmQueue);
						quantity = vendorOrderMessage.getInt("Quantity");
						System.out.println("Vendor: Retailer " + "ordered "
								+ quantity + " "
								+ vendorOrderMessage.getString("Item"));

						// Send message to monitor supplier.
						orderMessage.setString("Item", "Monitor");
						orderMessage.setInt("Quantity", quantity);
						monitorOrderQueueSender.send(orderMessage);
						System.out.println("Vendor: ordered " + quantity + " "
								+ orderMessage.getString("Item") + "(s)");

						/*
						 * Reuse message to send to storage supplier, changing
						 * only item name.
						 */
						orderMessage.setString("Item", "Hard Drive");
						storageOrderQueueSender.send(orderMessage);
						System.out.println("Vendor: ordered " + quantity + " "
								+ orderMessage.getString("Item") + "(s)");

						// Commit session.
						queueSession.commit();
						System.out.println("  Vendor: "
								+ "committed transaction 1");
					} catch (JMSException e) {
						System.err.println("Vendor: "
								+ "JMSException occurred: " + e.toString());
						e.printStackTrace();
						queueSession.rollback();
						System.err.println("  Vendor: rolled "
								+ "back transaction 1");
					}
				}

				// Wait till suppliers get back with answers.
				listener.monitor.waitTillDone();
			} catch (JMSException e) {
				System.err.println("Vendor: Exception " + "occurred: "
						+ e.toString());
				e.printStackTrace();
			} finally {
				if (queueConnection != null) {
					try {
						queueConnection.close();
					} catch (JMSException e) {
					}
				}
			}
		}
	}

	/**
	 * The Order class represents a Retailer order placed with a Vendor. It
	 * maintains a table of pending orders.
	 */
	public static class Order {
		private static Hashtable pendingOrders = new Hashtable();

		private static int nextOrderNumber = 1;

		private static final int PENDING_STATUS = 1;

		private static final int CANCELLED_STATUS = 2;

		private static final int FULFILLED_STATUS = 3;

		int status;

		public final int orderNumber;

		public int quantity;

		// Original order from retailer
		public final MapMessage order;

		// Reply from supplier
		public MapMessage monitor = null;

		// Reply from supplier
		public MapMessage storage = null;

		/**
		 * Returns the next order number and increments the static variable that
		 * holds this value.
		 * 
		 * @return the next order number
		 */
		private static int getNextOrderNumber() {
			int result = nextOrderNumber;
			nextOrderNumber++;
			return result;
		}

		/**
		 * Constructor. Sets order number; sets order and quantity from incoming
		 * message. Sets status to pending, and adds order to hash table of
		 * pending orders.
		 * 
		 * @param order
		 *            the message containing the order
		 */
		public Order(MapMessage order) {
			this.orderNumber = getNextOrderNumber();
			this.order = order;
			try {
				this.quantity = order.getInt("Quantity");
			} catch (JMSException je) {
				System.err.println("Unexpected error. Message "
						+ "missing Quantity");
				this.quantity = 0;
			}
			status = PENDING_STATUS;
			pendingOrders.put(new Integer(orderNumber), this);
		}

		/**
		 * Returns the number of orders in the hash table.
		 * 
		 * @return the number of pending orders
		 */
		public static int outstandingOrders() {
			return pendingOrders.size();
		}

		/**
		 * Returns the order corresponding to a given order number.
		 * 
		 * @param orderNumber
		 *            the number of the requested order
		 * @return the requested order
		 */
		public static Order getOrder(int orderNumber) {
			return (Order) pendingOrders.get(new Integer(orderNumber));
		}

		/**
		 * Called by the onMessage method of the VendorMessageListener class to
		 * process a reply from a supplier to the Vendor.
		 * 
		 * @param component
		 *            the message from the supplier
		 * @return the order with updated status information
		 */
		public Order processSubOrder(MapMessage component) {
			String itemName = null;

			// Determine which subcomponent this is.
			try {
				itemName = component.getString("Item");
			} catch (JMSException je) {
				System.err.println("Unexpected exception. "
						+ "Message missing Item");
			}
			if (itemName.compareTo("Monitor") == 0) {
				monitor = component;
			} else if (itemName.compareTo("Hard Drive") == 0) {
				storage = component;
			}

			/*
			 * If notification for all subcomponents has been received, verify
			 * the quantities to compute if able to fulfill order.
			 */
			if ((monitor != null) && (storage != null)) {
				try {
					if (quantity > monitor.getInt("Quantity")) {
						status = CANCELLED_STATUS;
					} else if (quantity > storage.getInt("Quantity")) {
						status = CANCELLED_STATUS;
					} else {
						status = FULFILLED_STATUS;
					}
				} catch (JMSException je) {
					System.err
							.println("Unexpected exception: " + je.toString());
					status = CANCELLED_STATUS;
				}

				/*
				 * Processing of order is complete, so remove it from
				 * pending-order list.
				 */
				pendingOrders.remove(new Integer(orderNumber));
			}
			return this;
		}

		/**
		 * Determines if order status is pending.
		 * 
		 * @return true if order is pending, false if not
		 */
		public boolean isPending() {
			return status == PENDING_STATUS;
		}

		/**
		 * Determines if order status is cancelled.
		 * 
		 * @return true if order is cancelled, false if not
		 */
		public boolean isCancelled() {
			return status == CANCELLED_STATUS;
		}

		/**
		 * Determines if order status is fulfilled.
		 * 
		 * @return true if order is fulfilled, false if not
		 */
		public boolean isFulfilled() {
			return status == FULFILLED_STATUS;
		}
	}

	/**
	 * The VendorMessageListener class processes an order confirmation message
	 * from a supplier to the vendor.
	 * 
	 * It demonstrates the use of transactions within message listeners.
	 */
	public static class VendorMessageListener implements MessageListener {
		final SampleUtilities.DoneLatch monitor = new SampleUtilities.DoneLatch();

		private final Session session;

		int numSuppliers;

		/**
		 * Constructor. Instantiates the message listener with the session of
		 * the consuming class (the vendor).
		 * 
		 * @param qs
		 *            the session of the consumer
		 * @param numSuppliers
		 *            the number of suppliers
		 */
		public VendorMessageListener(Session qs, int numSuppliers) {
			this.session = qs;
			this.numSuppliers = numSuppliers;
		}

		/**
		 * Casts the message to a MapMessage and processes the order. A message
		 * that is not a MapMessage is interpreted as the end of the message
		 * stream, and the message listener sets its monitor state to all done
		 * processing messages.
		 * 
		 * Each message received represents a fulfillment message from a
		 * supplier.
		 * 
		 * @param message
		 *            the incoming message
		 */
		public void onMessage(Message message) {
			System.out.println("VendorMessageListener().onMessage(Message message)::"+message);
			/*
			 * If message is an end-of-message-stream message and this is the
			 * last such message, set monitor status to all done processing
			 * messages and commit transaction.
			 */
			if (!(message instanceof MapMessage)) {
				if (Order.outstandingOrders() == 0) {
					numSuppliers--;
					if (numSuppliers == 0) {
						monitor.allDone();
					}
				}
				try {
					System.out.println("!(message instanceof MapMessage)::session.commit()::::::");
					session.commit();
				} catch (JMSException je) {
				}
				return;
			}

			/*
			 * Message is an order confirmation message from a supplier.
			 */
			int orderNumber = -1;
			try {
				MapMessage component = (MapMessage) message;

				/*
				 * Process the order confirmation message and commit the
				 * transaction.
				 */
				orderNumber = component.getInt("VendorOrderNumber");
				Order order = Order.getOrder(orderNumber).processSubOrder(
						component);
				session.commit();
				System.out.println("(MapMessage) message::session.commit()::::::::");
				/*
				 * If this message is the last supplier message, send message to
				 * Retailer and commit transaction.
				 */
				if (!order.isPending()) {
					System.out.println("Vendor: Completed "
							+ "processing for order " + order.orderNumber);
					Destination replyQueue = (Destination) order.order
							.getJMSReplyTo();
					MessageProducer qs = session.createProducer(replyQueue);
					MapMessage retailerConfirmMessage = session
							.createMapMessage();
					if (order.isFulfilled()) {
						retailerConfirmMessage
								.setBoolean("OrderAccepted", true);
						System.out.println("Vendor: sent " + order.quantity
								+ " computer(s)");
					} else if (order.isCancelled()) {
						retailerConfirmMessage.setBoolean("OrderAccepted",
								false);
						System.out.println("Vendor: unable to " + "send "
								+ order.quantity + " computer(s)");
					}
					qs.send(retailerConfirmMessage);
					session.commit();
					System.out
							.println("  Vendor: committed " + "transaction 2");
				}
			} catch (JMSException je) {
				je.printStackTrace();
				try {
					session.rollback();
				} catch (JMSException je2) {
				}
			} catch (Exception e) {
				e.printStackTrace();
				try {
					session.rollback();
				} catch (JMSException je2) {
				}
			}
			System.out.println("onMessage():::::::::::END");
		}
	}

	/**
	 * The GenericSupplier class receives an item order from the vendor and
	 * sends a message accepting or refusing it.
	 */
	public static class GenericSupplier extends Thread {
		final String PRODUCT_NAME;

		final String IN_ORDER_QUEUE;

		int quantity = 0;

		/**
		 * Constructor. Instantiates the supplier as the supplier for the kind
		 * of item being ordered.
		 * 
		 * @param itemName
		 *            the name of the item being ordered
		 * @param inQueue
		 *            the queue from which the order is obtained
		 */
		public GenericSupplier(String itemName, String inQueue) {
			PRODUCT_NAME = itemName;
			IN_ORDER_QUEUE = inQueue;
		}

		/**
		 * Checks to see if there are enough items in inventory. Rather than go
		 * to a database, it generates a random number related to the order
		 * quantity, so that some of the time there won't be enough in stock.
		 * 
		 * @return the number of items in inventory
		 */
		public int checkInventory() {
			Random rgen = new Random();

			return (rgen.nextInt(quantity * 5));
		}

		/**
		 * Runs the thread.
		 */
		public void run() {
			ConnectionFactory queueConnectionFactory = null;
			Connection queueConnection = null;
			Session queueSession = null;
			Destination orderQueue = null;
			MessageConsumer queueReceiver = null;
			Message inMessage = null;
			MapMessage orderMessage = null;
			MapMessage outMessage = null;

			try {
				queueConnectionFactory = SampleUtilities
						.getQueueConnectionFactory();
				queueConnection = queueConnectionFactory.createConnection();
				queueSession = queueConnection.createSession(true, 0);
				orderQueue = SampleUtilities.getQueue(IN_ORDER_QUEUE,
						queueSession);
			} catch (Exception e) {
				System.err.println("Connection problem: " + e.toString());
				System.err.println("Program assumes five "
						+ "queues named A B C D E");
				if (queueConnection != null) {
					try {
						queueConnection.close();
					} catch (JMSException ee) {
					}
				}
				System.exit(1);
			}

			/*
			 * Create receiver for order queue and start message delivery.
			 */
			try {
				queueReceiver = queueSession.createConsumer(orderQueue);
				queueConnection.start();
			} catch (JMSException je) {
			}

			/*
			 * Keep checking supplier order queue for order request until
			 * end-of-message-stream message is received. Receive order and send
			 * an order confirmation as one transaction.
			 */
			while (true) {
				try {
					inMessage = queueReceiver.receive();
					if (inMessage instanceof MapMessage) {
						orderMessage = (MapMessage) inMessage;
					} else {
						/*
						 * Message is an end-of-message-stream message. Send a
						 * similar message to reply queue, commit transaction,
						 * then stop processing orders by breaking out of loop.
						 */
						MessageProducer queueSender = queueSession
								.createProducer((Destination) inMessage
										.getJMSReplyTo());
						queueSender.send(queueSession.createMessage());
						queueSession.commit();
						break;
					}

					/*
					 * Extract quantity ordered from order message.
					 */
					quantity = orderMessage.getInt("Quantity");
					System.out.println(PRODUCT_NAME
							+ " Supplier: Vendor ordered " + quantity + " "
							+ orderMessage.getString("Item") + "(s)");

					/*
					 * Create sender and message for reply queue. Set order
					 * number and item; check inventory and set quantity
					 * available. Send message to vendor and commit transaction.
					 */
					MessageProducer queueSender = queueSession
							.createProducer((Destination) orderMessage
									.getJMSReplyTo());
					outMessage = queueSession.createMapMessage();
					outMessage.setInt("VendorOrderNumber", orderMessage
							.getInt("VendorOrderNumber"));
					outMessage.setString("Item", PRODUCT_NAME);
					int numAvailable = checkInventory();
					if (numAvailable >= quantity) {
						outMessage.setInt("Quantity", quantity);
					} else {
						outMessage.setInt("Quantity", numAvailable);
					}
					queueSender.send(outMessage);
					System.out.println(PRODUCT_NAME + " Supplier: sent "
							+ outMessage.getInt("Quantity") + " "
							+ outMessage.getString("Item") + "(s)");
					queueSession.commit();
					System.out.println("  " + PRODUCT_NAME
							+ " Supplier: committed transaction");
				} catch (Exception e) {
					System.err.println(PRODUCT_NAME
							+ " Supplier: Exception occurred: " + e.toString());
					e.printStackTrace();
				}
			}

			if (queueConnection != null) {
				try {
					queueConnection.close();
				} catch (JMSException e) {
				}
			}
		}
	}

	/**
	 * Creates the Retailer and Vendor classes and the two supplier classes,
	 * then starts the threads.
	 * 
	 * @param quantity
	 *            the quantity specified on the command line
	 */
	public static void run_threads(int quantity) {
		Retailer r = new Retailer(quantity);
		Vendor v = new Vendor();
		GenericSupplier ms = new GenericSupplier("Monitor",
				monitorOrderQueueName);
		GenericSupplier ss = new GenericSupplier("Hard Drive",
				storageOrderQueueName);

		r.start();
		v.start();
		ms.start();
		ss.start();
		try {
			r.join();
			v.join();
			ms.join();
			ss.join();
		} catch (InterruptedException e) {
		}
	}

	/**
	 * Reads the order quantity from the command line, then calls the
	 * run_threads method to execute the program threads.
	 * 
	 * @param args
	 *            the quantity of computers being ordered
	 */
	public static void main(String[] args) {
		TransactedExample te = new TransactedExample();
		int quantity = 0;

		if (args.length != 1) {
			System.out.println("Usage: java TransactedExample " + "<integer>");
			System.out.println("Program assumes five queues "
					+ "named A B C D E");
			System.exit(1);
		}
		TransactedExample.vendorOrderQueueName = new String("queue/E");
		TransactedExample.retailerConfirmQueueName = new String("queue/F");
		TransactedExample.monitorOrderQueueName = new String("queue/G");
		TransactedExample.storageOrderQueueName = new String("queue/H");
		TransactedExample.vendorConfirmQueueName = new String("queue/I");
		quantity = (new Integer(args[0])).intValue();
		System.out.println("Quantity to be ordered is " + quantity);
		if (quantity > 0) {
			TransactedExample.run_threads(quantity);
		} else {
			System.out.println("Quantity must be positive and " + "nonzero");
		}
	}
}
