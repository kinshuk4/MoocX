/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mk.training.harness;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Executors;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.mk.training.mail.incoming.ListenerFactory;
import org.mk.training.mail.incoming.MailListener;
import org.mk.training.mail.incoming.MailReceiver;

/**
 *
 * @author veenamohitkumar
 */
public class ReceivingMain implements MailListener,ListenerFactory{

    public static void main(String args[]) throws Exception {
        
        MailReceiver reciever = MailReceiver.newSecureMailReceiver("imap.gmail.com", "imap", "org.mk.training.jca@gmail.com",
                "beginner911", "993",(ListenerFactory)new ReceivingMain());
        //reciever.setImapIdle("false");
        //MailReceiver reciever=MailReceiver.newSecureMailReceiver("pop.gmail.com","pop3", "org.mk.training.jca@gmail.com","beginner911","995",(ListenerFactory)new ReceivingMain());
        //reciever.setDispatchThreadExecutor(Executors.newFixedThreadPool(1));
        reciever.setupIncomingMail();
        System.out.println("Asynchronous::::");
        //Thread.sleep(50000);
        //System.out.println("Slept::::");
        //reciever.shutdown();
        //reciever=MailReceiver.newSecureMailReceiver("pop.gmail.com","pop3", "org.mk.training.jca@gmail.com","beginner911","995",ReceivingMain.class);
        //reciever = MailReceiver.newSecureMailReceiver("imap.gmail.com", "imap", "org.mk.training.jca@gmail.com", "beginner911", "993",ReceivingMain.class);
        //reciever.setupIncomingMail();
    }

    public void onMessage(Message message) {
        try {
            System.out.println("Thread.currentThread().getName():onMessage(Message message)::::"+Thread.currentThread().getName());
            String msgid = null;
            if (message instanceof MimeMessage) {
                MimeMessage mm = (MimeMessage) message;
                System.out.println("mm.getContentType():" + mm.getContentType());
                msgid = mm.getMessageID();
                System.out.println("mm.getMessageID():" + msgid);
                System.out.println("mm.getSender():" + mm.getSender());
                Address[] adds = mm.getReplyTo();
                for (int i = 0; i < adds.length; i++) {
                    Address address = adds[i];
                    System.out.println(i + "th address:" + address);
                }

            }
            String from = ((InternetAddress) message.getFrom()[0]).getPersonal();
            if (from == null) {
                from = ((InternetAddress) message.getFrom()[0]).getAddress();
            }
            System.out.println("FROM: " + from);
            String subject = message.getSubject();
            System.out.println("SUBJECT: " + subject);
            // -- Get the message part (i.e. the message itself) --
            Part messagePart = message;
            Object content = messagePart.getContent();
            // -- or its first body part if it is a multipart message --
            if (content instanceof Multipart) {
                messagePart = ((Multipart) content).getBodyPart(0);
                System.out.println("[ Multipart Message ]");
            }
            // -- Get the content type --
            String contentType = messagePart.getContentType();

            // -- If the content is plain text, we can print it --
            System.out.println("CONTENT:" + contentType);
            if (contentType.startsWith("text/plain")) {
                InputStream is = messagePart.getInputStream();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(is));
                String thisLine = reader.readLine();
                System.out.println("-----------------------------");
                while (thisLine != null) {
                    System.out.println("DATA::::" + thisLine);
                    thisLine = reader.readLine();
                }
            }
            System.out.println("-----------------------------");
            //Thread.sleep(60000);
            System.out.println("Done Sleeping-----------------------------");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Object createListener() {
        return new ReceivingMain();
    }

}