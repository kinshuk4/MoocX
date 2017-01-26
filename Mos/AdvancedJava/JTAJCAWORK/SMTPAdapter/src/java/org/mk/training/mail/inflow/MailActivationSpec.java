package org.mk.training.mail.inflow;

import java.io.Serializable;
import javax.resource.spi.ActivationSpec;
import javax.resource.spi.InvalidPropertyException;
import javax.resource.spi.ResourceAdapter;
import javax.resource.ResourceException;

public class MailActivationSpec implements ActivationSpec, Serializable {
	/** @since 1.0 */
	private static final long serialVersionUID = -3034364895936568423L;

	/** The resource adapter */
	private transient ResourceAdapter ra;

	/** The mail server hostname/address */
	private String mailServer = "mailhost";

	/** The mail store protocol */
	private String storeProtocol = "imap";

	/** The mail folder name */
	private String mailFolder;

	/** The message selector */
	private String messageSelector;

	/** The mail store user */
	private String userName;

	/** The mail store password */
	private String password;

	/** The new messages check delay in MS */
	private long pollingInterval = 60000;

	/** The maximum number of messages */
	private int maxMessages = 1;

	/** Enable JavaMail debugging */
	private boolean debug;

	/** flush - for pop3 flush the mailbox after checking */
	private boolean flush = true;

	private String port;

	public MailActivationSpec() {
		System.out.println("MailActivationSpec()");
	}

	public String getMailServer() {
		return mailServer;
	}

	public void setMailServer(String mailServer) {
		this.mailServer = mailServer;
	}

	public String getStoreProtocol() {
		return storeProtocol;
	}

	public void setStoreProtocol(String storeProtocol) {
		this.storeProtocol = storeProtocol;
	}

	public String getMailFolder() {
		return mailFolder;
	}

	public void setMailFolder(String mailFolder) {
		this.mailFolder = mailFolder;
	}

	public String getMessageSelector() {
		return messageSelector;
	}

	public void setMessageSelector(String messageSelector) {
		this.messageSelector = messageSelector;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getPollingInterval() {
		return pollingInterval;
	}

	public void setPollingInterval(long pollingInterval) {
		this.pollingInterval = pollingInterval;
	}

	public int getMaxMessages() {
		return maxMessages;
	}

	public void setMaxMessages(int maxMessages) {
		this.maxMessages = maxMessages;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public boolean isDebug() {
		return debug;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public void setFlush(boolean flush) {
		this.flush = flush;
	}

	public boolean isFlush() {
		return flush;
	}

	public ResourceAdapter getResourceAdapter() {
		return ra;
	}

	public void setResourceAdapter(ResourceAdapter ra) throws ResourceException {
		this.ra = ra;
	}

	public void validate() throws InvalidPropertyException {

	}

	public String toString() {
		StringBuffer tmp = new StringBuffer("MailActivationSpec(");
		tmp.append("mailServer=");
		tmp.append(mailServer);
		tmp.append(", storeProtocol=");
		tmp.append(storeProtocol);
		tmp.append(", mailFolder=");
		tmp.append(mailFolder);
		tmp.append(", pollingInterval=");
		tmp.append(pollingInterval);
		tmp.append(", messageSelector=");
		tmp.append(messageSelector);
		tmp.append(", userName=");
		tmp.append(userName);
		tmp.append(", maxMessages=");
		tmp.append(maxMessages);
		tmp.append(", debug=");
		tmp.append(debug);
		tmp.append(")");
		return tmp.toString();
	}
}
