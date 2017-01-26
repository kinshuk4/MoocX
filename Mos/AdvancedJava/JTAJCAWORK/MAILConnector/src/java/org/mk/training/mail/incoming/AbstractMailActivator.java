package org.mk.training.mail.incoming;

import org.mk.training.mail.incoming.imap.IMAPMailFolder;
import com.sun.mail.imap.IMAPFolder;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.concurrent.CompletionService;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Flags.Flag;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.event.MessageCountAdapter;
import javax.mail.event.MessageCountEvent;
import org.mk.training.mail.util.ContentTypes;

public abstract class AbstractMailActivator implements Comparable, Runnable {

    /** A flag indicated if the unit of work has been released */
    private boolean released;
    /** The time at which the next new msgs check should be performed */
    private long nextNewMsgCheckTime;
    /** The activation mailSpec for the mail folder */
    protected MailReceiver mailSpec;
    private MailFolder mailFolder;
    Executor dispatchExecutor;
    CompletionService<Void> cs;

    public AbstractMailActivator(MailReceiver mailSpec) {
        this.mailSpec = mailSpec;
    }

    public long getNextNewMsgCheckTime() {
        return nextNewMsgCheckTime;
    }

    public void updateNextNewMsgCheckTime(long now) {
        nextNewMsgCheckTime = now + mailSpec.getPollingInterval();
    }

    protected MailReceiver getMailSpec() {
        return mailSpec;
    }

    public int compareTo(Object obj) {
        AbstractMailActivator ma = (AbstractMailActivator) obj;
        long compareTo = nextNewMsgCheckTime - ma.getNextNewMsgCheckTime();
        return (int) compareTo;
    }

    public boolean isReleased() {
        return released;
    }

    protected void shutdown() {
        System.out.println("Shutting Down-----------------------------");
        released = true;
        Executor exec = mailSpec.getListenerThreadExecutor();
        if (exec instanceof ExecutorService) {
            System.out.println("Releasing");
            ExecutorService service = (ExecutorService) exec;
            service.shutdownNow();
        }
        exec = mailSpec.getDispatchThreadExecutor();
        if (exec instanceof ExecutorService) {
            System.out.println("Releasing");
            ExecutorService service = (ExecutorService) exec;
            service.shutdownNow();
        }
        System.out.println("Shutting Down Mail Folder-----------------------------");
        if (this.checkWebConnectivity()) {
            if (mailFolder != null) {
                try {
                    mailFolder.close();
                } catch (MessagingException ex) {
                    ex.printStackTrace();
                }
            }
        }
        System.out.println("Shutdown complete-----------------------------");
    }

    public void run() {
        dispatchExecutor = this.mailSpec.getDispatchThreadExecutor();
        cs = new ExecutorCompletionService<Void>(dispatchExecutor);
        System.out.println("MailActivation.run().START:: ");

        while (!Thread.currentThread().isInterrupted() && !this.isReleased()) {
            try {
                System.out.println("Getting Folder Instance::::" + Thread.currentThread().getName());
                mailFolder = MailFolder.getInstance(mailSpec);
                System.out.println("About to open folder::::" + Thread.currentThread().getName());
                mailFolder.open();
                if (mailFolder.getMessageLength() > 0) {
                    this.poll();
                    this.closeFolder();
                } else if (mailSpec.isImapIdle() && mailFolder instanceof IMAPMailFolder) {
                    mailFolder.getFolder().addMessageCountListener(new MessageCountListener());
                    this.imapIdle();
                } else {
                    this.poll();
                    this.waitForInterval();
                    this.closeFolder();
                }
            } catch (Exception ex) {
                Logger.getLogger(AbstractMailActivator.class.getName()).log(Level.SEVERE, null, ex);
                if (!this.isReleased()) {
                    this.waitForInterval();
                }
            }
        }
        System.out.println("MailActivation.run().END:: ");
    }

    private void poll() {
        System.out.println("POLL:" + mailFolder.getFolder().getClass().getName());
        if (this.isReleased() && mailFolder.getMessageLength() == 0) {
            return;
        }
        boolean interrupted = false;
        try {
            while (mailFolder.hasNext()) {
                Message msg = (Message) mailFolder.next();
                System.out.println("Before Delivery::::::::");
                cs.submit(new MessageDelivererWorker(msg), null);
                System.out.println("After Delivery:::::::::");
            }
            for (int i = 0; i < mailFolder.getMessageLength(); i++) {
                cs.take();
            }
        } catch (InterruptedException ie) {
            interrupted = true;
        } finally {
            if (interrupted) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void imapIdle() {
        System.out.println("IDLE:" + Thread.currentThread().getName());
        if (this.isReleased()) {
            return;
        }
        try {
            IMAPFolder f = (IMAPFolder) mailFolder.getFolder();
            f.idle();
            System.out.println("Waking from IDLE:" + Thread.currentThread().getName());
        } catch (MessagingException ex) {
            ex.printStackTrace();
            this.poll();
        }
    }

    private void waitForInterval() {
        this.updateNextNewMsgCheckTime(System.currentTimeMillis());
        long now = System.currentTimeMillis();
        long nextTime = this.getNextNewMsgCheckTime();
        long sleepMS = nextTime - now;
        System.out.println("Going to sleep for " + sleepMS + " millis.");
        if (sleepMS > 0) {
            try {
                Thread.sleep(sleepMS);
                System.out.println("Waking from sleep ");
            } catch (InterruptedException ex) {
                System.out.println("Waking from sleep " + ex);
                Thread.currentThread().interrupt();
            }
        }
    }

    private void closeFolder() {
        try {
            mailFolder.close();
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }

    protected abstract void deliverMsg(Message msg);

    class MessageCountListener extends MessageCountAdapter {

        public void messagesAdded(MessageCountEvent ev) {
            boolean interrupted = false;
            Message[] msgs = ev.getMessages();
            System.out.println("Got " + msgs.length + " new messages" + Thread.currentThread().getName());
            // Just dump out the new messages
            for (int i = 0; i < msgs.length; i++) {
                try {
                    System.out.println("-----");
                    System.out.println("Message "
                            + msgs[i].getMessageNumber() + ":");
                    msgs[i].setFlag(Flag.SEEN, true);
                    System.out.println("Before Delivery::::::::");
                    cs.submit(new MessageDelivererWorker(msgs[i]), null);
                    System.out.println("After Delivery:::::::::");
                } catch (MessagingException mex) {
                    mex.printStackTrace();
                }
            }
            System.out.println("mailFolder.getMessageLength():::::::::" + mailFolder.getMessageLength());
            System.out.println("msgs.length:::::::::" + msgs.length);
            System.out.println("before cs.take():::::::::" + Thread.currentThread().getName());
            for (int i = 0; i < msgs.length; i++) {
                try {
                    cs.take();
                } catch (InterruptedException ex) {
                    interrupted = true;
                }
            }
            //closeFolder();
            System.out.println("closedFolder():::::::::" + Thread.currentThread().getName());
            if (interrupted) {
                Thread.currentThread().interrupt();
            }
        }
    }

    class MessageDelivererWorker implements Runnable {

        Message message;

        public MessageDelivererWorker(Message message) {
            this.message = message;
        }

        public void run() {

            deliverMsg(message);
        }
    }

    private boolean checkWebConnectivity() {
        boolean isup = false;
        Enumeration<NetworkInterface> interfaces = null;
        try {
            interfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException ex) {
            Logger.getLogger(AbstractMailActivator.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (interfaces.hasMoreElements()) {
            NetworkInterface interf = interfaces.nextElement();
            try {
                if (interf.isUp() && !interf.isLoopback()) {
                    isup = true;
                }
            } catch (SocketException ex) {
                Logger.getLogger(AbstractMailActivator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return isup;
    }
}
