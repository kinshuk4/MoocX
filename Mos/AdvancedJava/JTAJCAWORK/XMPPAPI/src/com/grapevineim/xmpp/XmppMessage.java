package com.grapevineim.xmpp;

import java.io.Serializable;
import javax.resource.ResourceException;

public class XmppMessage implements Serializable {

	private static final long serialVersionUID = -1;
	
	private String to;
	private String from;
	private String subject;
	private String body;
	private String threadId;
	private String packetId;
	private String type;
	private XmppConnectionSpec connectionSpec;
	
	public XmppMessage() {}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getThreadId() {
		return threadId;
	}

	public void setThreadId(String threadId) {
		this.threadId = threadId;
	}

	public String getPacketId() {
		return packetId;
	}

	public void setPacketId(String packetId) {
		this.packetId = packetId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public XmppConnectionSpec getConnectionSpec() {
		return connectionSpec;
	}

	public void setConnectionSpec(XmppConnectionSpec connectionSpec) {
		this.connectionSpec = connectionSpec;
	}

        public XmppConnection getCurrentConnectionHandle() throws ResourceException {
            throw new ResourceException("Can only be used in an XMPP MDB to use the same connection."
                    + "handle on which MDB is listening.");
        }
}
