/*
 * Copyright 2004-2005 Sun Microsystems, Inc.  All rights reserved.
 * Use is subject to license terms.
 */
package samples.connectors.mailconnector.ra.outbound;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.mail.MessagingException;
import java.util.*;

import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import javax.resource.ResourceException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.w3c.dom.Node;

/**
 * Application-level connection handle that is used by a 
 * client component to access an EIS instance.
 */
public class JavaMailConnectionImpl extends AbstractConnectionImpl {

    private Object message = "Hello World from SMTPSimpleSender";
    private String messagesubject = "Just Saying HI";
    private String fromaddress = "org.mk.training.jmx@gmail.com";
    private List<String> toaddresslist = new ArrayList<String>();
    private String contentType = "text/plain";
    private String messageId = null;
    private String replyTo = null;
    private String inReplyTo = null;
    private boolean debug = true;
    private boolean generateMessageId = true;
    private byte[] bytemsg;
    Transformer xform = null;

    public JavaMailConnectionImpl(ManagedConnectionImpl mc, MailServerSession mailserversession) {
        super(mc, mailserversession);
        this.setFromaddress(mailserversession.getFromAddress());
    }

    public String sendMail(String messageSubject, Object message, String contentType) throws MessagingException, ResourceException {
        this.setMessagesubject(messageSubject);
        this.setMessage(message);
        this.setContentType(contentType);
        if (!this.checkInit()) {
            throw new RuntimeException(
                    "One or more of required params are not present." + this.toString());
        }
        return this._sendTheMail();
    }

    public String sendMail(String messageSubject, Object message) throws MessagingException, ResourceException {
        return this.sendMail(messageSubject, message, null);
    }

    public String sendMail(String messageSubject, Object message, String messageId, String replyTo, String inReplyTo) throws MessagingException, ResourceException {
        this.setMessageId(messageId);
        this.setReplyTo(replyTo);
        this.setInReplyTo(inReplyTo);
        return this.sendMail(messageSubject, message);
    }

    public String sendMail(String messageSubject, Object message, String contentType, String messageId, String replyTo, String inReplyTo) throws MessagingException, ResourceException {
        this.setContentType(contentType);
        return this.sendMail(messageSubject, message, messageId, replyTo, inReplyTo);
    }

    private String _sendTheMail() throws MessagingException, ResourceException {
        this.checkIfValid();
        Message msg = new MyMessage(session);
        System.out.println(session.getProperties());
        InternetAddress addressFrom = new InternetAddress(this.getFromaddress());
        msg.setFrom(addressFrom);
        InternetAddress[] addressTo = null;
        addressTo = new InternetAddress[toaddresslist.size()];
        Iterator<String> i = toaddresslist.iterator();
        for (int j = 0; j < toaddresslist.size(); j++) {
            addressTo[j] = new InternetAddress(toaddresslist.get(j));
            System.out.println(addressTo[j]);
        }
        msg.setRecipients(Message.RecipientType.TO, addressTo);
        msg.setSubject(messagesubject);
        try {
            this.makeContent();
        } catch (TransformerException ex) {
            ex.printStackTrace();
        }
        ByteArrayDataSource bads = new ByteArrayDataSource(bytemsg, contentType);
        System.out.println("contentType:" + contentType + " bads.getContentType():" + bads.getContentType());
        msg.setDataHandler(new DataHandler(bads));
        System.out.println("Sending...." + msg.getDataHandler().getName());
        System.out.println("Sending...." + msg.getDataHandler().getClass().getName());
        System.out.println("Sending...." + msg.getDataHandler().getClass().getProtectionDomain().getCodeSource());
        this.addHeader(msg);
        msg.saveChanges();
        msg.setHeader("Content-Transfer-Encoding", "7bit");
        System.out.println("Sending....");
        System.out.println("msg.getContentType():" + msg.getContentType());
        transport.sendMessage(msg, addressTo);
        System.out.println("Sent");
        String id = messageId;
        messageId = null;
        return this.cleanHeader(id);
    }

    public List<String> getToaddresslist() {
        return toaddresslist;
    }

    public void setToAddress(String toaddress) {
        this.toaddresslist.clear();
        this.toaddresslist.add(toaddress);
    }

    public void addToAddress(String toaddress) {
        this.toaddresslist.add(toaddress);
    }

    public void removeFromToAddress(String toaddress) {
        this.toaddresslist.remove(toaddress);
    }

    public String getFromaddress() {
        return fromaddress;
    }

    public void setFromaddress(String fromaddress) {
        this.fromaddress = fromaddress;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        if (contentType != null) {
            this.contentType = contentType;
        }
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }

    public String getInReplyTo() {
        return inReplyTo;
    }

    public void setInReplyTo(String inReplyTo) {
        this.inReplyTo = inReplyTo;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public String getMessagesubject() {
        return messagesubject;
    }

    public void setMessagesubject(String messagesubject) {
        this.messagesubject = messagesubject;
    }

    public boolean isGenerateMessageId() {
        return generateMessageId;
    }

    public void setGenerateMessageId(boolean generateMessageId) {
        this.generateMessageId = generateMessageId;
    }

    private void makeContent() throws TransformerException {

        if (message instanceof String && "text/plain".equals(contentType)) {
            bytemsg = message.toString().getBytes();
        } else if ("text/xml".equals(contentType) || "text/html".equals(contentType)
                || contentType.startsWith("text/xml") || contentType.startsWith("text/html")) {
            if (xform == null) {
                try {
                    xform = TransformerFactory.newInstance().newTransformer();
                } catch (TransformerConfigurationException ex) {
                    ex.printStackTrace();
                }
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            if (message instanceof String) {
                xform.transform(new StreamSource(new StringReader((String) message)), new StreamResult(baos));
            } else if (message instanceof Source) {
                xform.transform((Source) message, new StreamResult(baos));
            } else if (message instanceof Node) {
                xform.transform(new DOMSource((Node) message), new StreamResult(baos));
            } else if (message instanceof InputStream) {
                xform.transform(new StreamSource((InputStream) message), new StreamResult(baos));
            }
            System.out.println("baos:" + baos);
            bytemsg = baos.toByteArray();
        }

    }

    private void addHeader(Message message) throws MessagingException {
        if (messageId != null) {
            message.addHeader(MESSAGE_ID, messageId);
        } else {
            if (generateMessageId) {
                String host = null;
                try {
                    InetAddress hostadd = InetAddress.getByName("localhost");
                    host = hostadd.getHostName();
                } catch (UnknownHostException ex) {
                    host = "localhost";
                }
                messageId = "<" + UUID.randomUUID().toString() + "@" + host + ">";
                System.out.println("messageId:" + messageId);
                message.addHeader(MESSAGE_ID, this.prepareHeaderString(messageId));
            }
        }
        if (replyTo != null) {
            message.addHeader(REPLY_TO, this.prepareHeaderString(replyTo));
        } else {
            message.addHeader(REPLY_TO, this.prepareHeaderString(fromaddress));
        }
        if (inReplyTo != null) {
            message.addHeader(IN_REPLY_TO, this.prepareHeaderString(inReplyTo));
        }
    }

    public boolean checkInit() {
        boolean flag = (this.fromaddress != null
                && !this.toaddresslist.isEmpty());
        System.out.println(flag);
        return flag;
    }
    private static final String MESSAGE_ID = "Message-ID";
    private static final String REPLY_TO = "Reply-To";
    private static final String IN_REPLY_TO = "In-Reply-To";

    private static class MyMessage extends MimeMessage {

        public MyMessage(Session session) {
            super(session);
        }

        @Override
        protected void updateMessageID() throws MessagingException {
        }
    }

    public void removeAllToAddresses() {
        toaddresslist.clear();
    }

    private String prepareHeaderString(String header) {
        String prepheader = null;
        if (header != null) {
            if (!header.startsWith("<")) {
                header = "<" + header;
            }
            if (!header.endsWith(">")) {
                header = header + ">";
            }
        }
        prepheader = header;
        return prepheader;
    }

    private String cleanHeader(String val) {
        if (val.length() < 2) {
            return null;
        }
        // find the last token, if there are more than one
        int idx = val.lastIndexOf(' ');
        if (idx > 0) {
            val = val.substring(idx + 1);
        }
        if (val.startsWith("<")) {
            val = val.substring(1);
        }
        if (val.endsWith(">")) {
            val = val.substring(0, val.length() - ">".length());
        }
        return val;
    }
}
