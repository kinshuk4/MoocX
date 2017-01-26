package com.grapevineim.xmpp.ra.inbound;

import javax.resource.spi.endpoint.MessageEndpoint;
import javax.resource.spi.endpoint.MessageEndpointFactory;
import javax.resource.spi.work.Work;

import com.grapevineim.xmpp.XmppMessage;
import com.grapevineim.xmpp.XmppMessageListener;
import java.util.logging.Logger;

public class DeliveryThread implements Work {
	
	private static final Logger LOG = Logger.getLogger(DeliveryThread.class.getName());
	
	private final MessageEndpointFactory messageEndpointFactory;
	private final XmppMessage xmppMessage;

	public DeliveryThread(MessageEndpointFactory messageEndpointFactory, XmppMessage xmppMessage) {
		this.messageEndpointFactory = messageEndpointFactory;
		this.xmppMessage = xmppMessage;
	}

	public void release() {
		LOG.info("[DT] Worker Manager called release for deliveryThread ");
	}

	public void run() {
		LOG.info("[DT] WorkManager started delivery thread ");
		try {
			MessageEndpoint endpoint = null;
			try {
				LOG.info("Delivering Message...");
				if ((endpoint = messageEndpointFactory.createEndpoint(null)) != null) {
					((XmppMessageListener) endpoint).onMessage(xmppMessage);
				}
			} catch (Exception e) {
				LOG.severe("OnMessage exception"+e);
			} finally {
				if (endpoint != null) {
					endpoint.release();
				}
			}
		} catch (Exception te) {
			LOG.info("deliveryThread::run got an exception"+te);
		}
	}
}
