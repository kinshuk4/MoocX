package org.mk.training.mail.incoming;

import java.util.Iterator;
import java.util.Properties;

import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Message;
import javax.mail.event.ConnectionEvent;
import javax.mail.event.ConnectionListener;
import org.mk.training.mail.incoming.imap.IMAPMailFolder;
import org.mk.training.mail.incoming.pop3.POP3MailFolder;

public abstract class MailFolder implements Iterator{

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
    protected int FOLDERMODE;
    private Message[] msgs = {};
    private int messagePosition;

    public MailFolder(MailReceiver spec) {
        mailServer = spec.getMailServer();
        folderName = spec.getMailFolder();
        userName = spec.getUserName();
        password = spec.getPassword();
        debug = spec.isDebug();
        port = spec.getPort();
        sessionProps = spec.getSessionProperties();
    }

    public Folder getFolder() {
        return folder;
    }

    public int getMessageLength() {
        if (msgs != null) {
            return msgs.length;
        } else {
            return 0;
        }
    }

    public void open() throws Exception {
        session = Session.getInstance(sessionProps, null);
        session.setDebug(debug);
        // Get a store object
        store = openStore(session);
        if (!store.isConnected()) {
            int portint = Integer.parseInt(port);
            if (portint == 0) {
                store.connect(mailServer, userName, password);
            } else {
                store.connect(mailServer, portint, userName, password);
                System.out.println("Store Connected:: ");
            }
        }
        folder = store.getFolder(folderName);
        if (folder == null || (!this.folder.exists())) {
            MessagingException e = new MessagingException(
                    "Failed to find folder: " + folderName);
            System.out.println(" folder is null:: " + e);
            throw e;
        }
        if (!folder.isOpen()) {
            folder.open(FOLDERMODE);
        }
        msgs = getMessages(folder);
        System.out.println("msgs.length:::::::::: " + msgs.length);
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

    public static MailFolder getInstance(MailReceiver mailActivationSpec) {
        if ("pop3".equals(mailActivationSpec.getStoreProtocol())) {
            return new POP3MailFolder(mailActivationSpec);
        } else if ("imap".equals(mailActivationSpec.getStoreProtocol())) {
            return new IMAPMailFolder(mailActivationSpec);
        } else {
            return null;
        }
    }
}
