package org.mk.training.mail.incoming.imap;

import javax.mail.event.ConnectionEvent;
import org.mk.training.mail.incoming.*;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Flags.Flag;

public class IMAPMailFolder extends MailFolder {

    public IMAPMailFolder(MailReceiver spec) {
        super(spec);
        FOLDERMODE = Folder.READ_WRITE;
    }

    protected Message[] getMessages(Folder folder) throws MessagingException {
        if (folder.getUnreadMessageCount() > 0) {
            int newCount = folder.getUnreadMessageCount();
            int messageCount = folder.getMessageCount();
            // folder.getMessages indexes from 1 and uses an inclusive range
            // (ffs)
            return folder.getMessages(messageCount - newCount + 1, messageCount);
        } else {
            return new Message[0];
        }
    }

    protected Store openStore(Session session) throws NoSuchProviderException {
        return session.getStore("imap");
    }

    protected void markMessageSeen(Message message) throws MessagingException {
        message.setFlag(Flag.SEEN, true);
    }

    protected void closeStore(boolean success, Store store, Folder folder)
            throws MessagingException {
        try {
            if (folder != null && folder.isOpen()) {
                folder.close(success);
            }
        } finally {
            if (store != null && store.isConnected()) {
                store.close();
            }
        }
    }
}
