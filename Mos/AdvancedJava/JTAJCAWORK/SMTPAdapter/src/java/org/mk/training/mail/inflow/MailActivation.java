package org.mk.training.mail.inflow;

import java.lang.reflect.Method;
import javax.resource.spi.endpoint.MessageEndpointFactory;
import javax.resource.spi.endpoint.MessageEndpoint;
import javax.resource.spi.work.Work;
import javax.mail.Message;

import org.mk.training.mail.inflow.MailResourceAdapter;

public class MailActivation implements Comparable, Work {

	/**
	 * The MailListener.onMessage method
	 */
	public static final Method ON_MESSAGE;

	static {
		try {
			Class[] sig = { Message.class };
			ON_MESSAGE = MailListener.class.getMethod("onMessage", sig);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/** A flag indicated if the unit of work has been released */
	private boolean released;

	/** The logging trace level flag */
	private boolean trace;

	/** The time at which the next new msgs check should be performed */
	private long nextNewMsgCheckTime;

	/** The resource adapter */
	protected MailResourceAdapter ra;

	/** The activation spec for the mail folder */
	protected MailActivationSpec spec;

	/** The message endpoint factory */
	protected MessageEndpointFactory endpointFactory;

	public MailActivation(MailResourceAdapter ra,
			MessageEndpointFactory endpointFactory, MailActivationSpec spec) {
		this.ra = ra;
		this.endpointFactory = endpointFactory;
		this.spec = spec;

	}

	public long getNextNewMsgCheckTime() {
		return nextNewMsgCheckTime;
	}

	public void updateNextNewMsgCheckTime(long now) {
		nextNewMsgCheckTime = now + spec.getPollingInterval();
	}

	public int compareTo(Object obj) {
		MailActivation ma = (MailActivation) obj;
		long compareTo = nextNewMsgCheckTime - ma.getNextNewMsgCheckTime();
		return (int) compareTo;
	}

	public boolean isReleased() {
		return released;
	}

	// --- Begin Work interface
	public void release() {
		released = true;
	}

	public void run() {
		System.out.println("MailActivation.run()");
		if (released == false) {
			try {
				MailFolder mailFolder = MailFolder.getInstance(spec);
				mailFolder.open();
				while (mailFolder.hasNext()) {
					Message msg = (Message) mailFolder.next();
					deliverMsg(msg);
				}
				mailFolder.close();
			} catch (Exception e) {
				System.out.println(e);
				e.printStackTrace();
			}
		}
		System.out.println("MailActivation.run().END:: ");
	}

	// --- End Work interface

	private void deliverMsg(Message msg) {
		MessageEndpoint endpoint = null;
		try {
			endpoint = endpointFactory.createEndpoint(null);
			if (endpoint != null) {
				MailListener listener = (MailListener) endpoint;
				System.out
						.println("MailActivation.beforeonMessage():::::::::::::::::");
				listener.onMessage(msg);
				System.out
						.println("MailActivation.afteronMessage():::::::::::::::::");
			}
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			if (endpoint != null) {
				endpoint.release();
			}
		}
	}
}
