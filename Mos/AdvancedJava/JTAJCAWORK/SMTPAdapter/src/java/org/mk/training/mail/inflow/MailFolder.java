package org.mk.training.mail.inflow;

import java.util.Iterator;
import java.util.Properties;

import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Message;

public abstract class MailFolder implements Iterator {
	private Session session;

	private Store store;

	private Folder folder;

	protected String mailServer;

	private String folderName;

	protected String userName;

	protected String password;

	protected String port;

	private boolean debug;

	protected Properties sessionProps;

	private Message[] msgs = {};

	private int messagePosition;

	public MailFolder(MailActivationSpec spec) {
		mailServer = spec.getMailServer();
		folderName = spec.getMailFolder();
		userName = spec.getUserName();
		password = spec.getPassword();
		debug = spec.isDebug();
		port = spec.getPort();

	}

	public void open() throws Exception {
		// Get a session object
                System.out.println("sessionPropsWorks::"+sessionProps);
		session = Session.getDefaultInstance(sessionProps,null);
		session.setDebug(debug);
		// Get a store object
		store = openStore(session);
		int portint = Integer.parseInt(port);
		if (portint == 0) {
			store.connect(mailServer, userName, password);
		} else {
			store.connect(mailServer, portint, userName, password);
		}
		folder = store.getFolder(folderName);
		if (folder == null || (!this.folder.exists())) {
			MessagingException e = new MessagingException(
					"Failed to find folder: " + folderName);
			System.out.println(" folder is null:: "+e);
			throw e;
		}

		folder.open(Folder.READ_ONLY);
		msgs = getMessages(folder);
		System.out.println("msgs.length:::::::::: "+msgs.length);
	}

	protected abstract Store openStore(Session session)
			throws NoSuchProviderException;

	protected abstract void closeStore(boolean success, Store store,
			Folder folder) throws MessagingException;

	protected abstract Message[] getMessages(Folder folder)
			throws MessagingException;

	protected abstract void markMessageSeen(Message message)
			throws MessagingException;

	public void close() throws MessagingException {
		close(true);
	}

	public boolean hasNext() {
		return messagePosition < msgs.length;
	}

	public Object next() {
		try {
			Message m = msgs[messagePosition++];
			markMessageSeen(m);
			return m;
		} catch (MessagingException e) {
			close(false);
			throw new RuntimeException(e);
		}
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}

	protected void close(boolean checkSuccessful) {
		try {
			closeStore(checkSuccessful, store, folder);
		} catch (MessagingException e) {
			throw new RuntimeException("Error closing mail store", e);
		}
	}

	public static MailFolder getInstance(MailActivationSpec mailActivationSpec) {
		if ("pop3".equals(mailActivationSpec.getStoreProtocol())) {
			return new POP3MailFolder(mailActivationSpec);
		} else if ("imap".equals(mailActivationSpec.getStoreProtocol())) {
			return new IMAPMailFolder(mailActivationSpec);
		} else {
			return null;
		}
	}

}
