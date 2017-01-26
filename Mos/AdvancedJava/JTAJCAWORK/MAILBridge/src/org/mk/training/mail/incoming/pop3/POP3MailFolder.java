package org.mk.training.mail.incoming.pop3;

import org.mk.training.mail.incoming.*;
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

    public POP3MailFolder(MailReceiver spec) {
        super(spec);
        this.flush = spec.isFlush();
        FOLDERMODE = Folder.READ_ONLY;
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
