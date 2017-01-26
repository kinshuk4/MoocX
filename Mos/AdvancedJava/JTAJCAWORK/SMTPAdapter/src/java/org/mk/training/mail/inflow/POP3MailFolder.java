package org.mk.training.mail.inflow;

import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Flags.Flag;

public class POP3MailFolder extends MailFolder {

	private boolean flush;

	public POP3MailFolder(MailActivationSpec spec) {
		super(spec);
		this.flush = spec.isFlush();
		sessionProps = new Properties();
		// JavaMail doesn't implement POP3 STARTTLS
		sessionProps.setProperty("mail.imap.starttls.enable", "true");
		sessionProps.setProperty("mail.pop3.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		sessionProps.setProperty("mail.pop3.socketFactory.fallback", "false");
		sessionProps.setProperty("mail.pop3.port", port);
		sessionProps.setProperty("mail.pop3.socketFactory.port", port);
		sessionProps.setProperty("mail.pop3.host", mailServer);

	}

	protected Message[] getMessages(Folder folder) throws MessagingException {
		return folder.getMessages();
	}

	protected Store openStore(Session session) throws NoSuchProviderException {
		return session.getStore("pop3");
	}

	protected void markMessageSeen(Message message) throws MessagingException {
		message.setFlag(Flag.DELETED, true);
	}

	protected void closeStore(boolean success, Store store, Folder folder)
			throws MessagingException {
		try {
			if (folder != null && folder.isOpen()) {
				folder.close(success && flush);
			}
		} finally {
			if (store != null && store.isConnected()) {
				store.close();
			}
		}

	}

}
