package org.mk.training.mail.outgoing.smtp;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import java.util.UUID;
import javax.activation.DataHandler;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.w3c.dom.Node;

public class SMTPSimpleSender implements SMTPSimpleSenderMBean{
    private static final String MESSAGE_ID="Message-ID";
    private static final String REPLY_TO="Reply-To";
    private static final String IN_REPLY_TO="In-Reply-To";


	private String smtphostname = "smtp.gmail.com";

	private String username = "org.mk.training.jmx@gmail.com";

	private String password = null;

	private Object message = "Hello World from SMTPSimpleSender";

	private String messagesubject = "Just Saying HI";

	private String fromaddress = "org.mk.training.jmx@gmail.com";

	private String smtpport = "465";

	private boolean debug = true;

	private List<String> toaddresslist = new ArrayList<String>();

    private String messageId=null;

    private String replyTo=null;

    private String inReplyTo=null;

    private Properties sessionProperties;

    private String hostname;

    private boolean generateMessageId=true;

    private String contentType="text/plain";

    private byte[] bytemsg;

    Transformer xform=null;
	private SMTPSimpleSender(String smtphostname,String smtpport,String username,String password,
			String fromaddress,	String toaddress,Properties sessionProperties,boolean debug) {
		if(smtphostname!=null)
            this.smtphostname = smtphostname;
        if(username!=null)
            this.username = username;
        if(password!=null)
            this.password=password;
        if(fromaddress!=null)
            this.fromaddress = fromaddress;
		if (toaddress != null)
			this.toaddresslist.add(toaddress);
        if(smtpport!=null)
            this.smtpport = smtpport;
        if(debug==true)
            this.debug = debug;
        this.setSessionProperties(sessionProperties);
	}

    public Properties getSessionProperties() {
        return (Properties) sessionProperties.clone();
    }

    public void setSessionProperties(Properties sessionProperties) {
        if(sessionProperties!=null)
            this.sessionProperties = new Properties(sessionProperties);
        else
            this.sessionProperties=new Properties();
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

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public boolean isGenerateMessageId() {
        return generateMessageId;
    }

    public void setGenerateMessageId(boolean generateMessageId) {
        this.generateMessageId = generateMessageId;
    }

	public String getSmtphostname() {
		return smtphostname;
	}

	public void setSmtphostname(String smtphostname) {
		this.smtphostname = smtphostname;
	}

	public String getSmtpport() {
		return smtpport;
	}

	public void setSmtpport(String smtpport) {
		this.smtpport = smtpport;
	}

	public List<String> getToaddresslist() {
		return toaddresslist;
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
        if(contentType!=null)
            this.contentType = contentType;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

    public void sendMail(String messageSubject,Object message,String contentType) throws MessagingException {
        this.setMessagesubject(messageSubject);
        this.setMessage(message);
        this.setContentType(contentType);
		if (!this.checkInit()) {
			throw new RuntimeException(
					"One or more of required params are not present."
							+ this.toString());
		}
        this.populateProperties();
        this._sendTheMail();
	}

	public void sendMail(String messageSubject,Object message) throws MessagingException {
        this.sendMail(messageSubject, message,null);
	}

    public void sendMail(String messageSubject,Object message,String messageId,String replyTo,String inReplyTo) throws MessagingException {
        this.setMessageId(messageId);
        this.setReplyTo(replyTo);
        this.setInReplyTo(inReplyTo);
        this.sendMail(messageSubject, message);
	}
    public void sendMail(String messageSubject,Object message,String contentType,String messageId,String replyTo,String inReplyTo) throws MessagingException {
        this.setContentType(contentType);
        this.sendMail(messageSubject, message, messageId, replyTo, inReplyTo);
	}

    private void _sendTheMail() throws MessagingException{
        Authenticator auth = new SMTPAuthenticator();
		Session session = Session.getInstance(sessionProperties, auth);
		session.setDebug(this.isDebug());
        //System.out.println("Sending...."+session.);
		Message msg = new MimeMessage(session);
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
        msg.setDataHandler(new DataHandler(new ByteArrayDataSource(bytemsg,contentType)));
		
        System.out.println("Sending...."+msg.getDataHandler().getName());
        System.out.println("Sending...."+msg.getDataHandler().getClass().getName());
        System.out.println("Sending...."+msg.getDataHandler().getClass().getProtectionDomain().getCodeSource());
        this.addHeader(msg);
        msg.saveChanges();
        msg.setHeader("Content-Transfer-Encoding", "7bit");
		System.out.println("Sending....");
		Transport.send(msg);

        //Transport t=session.getTransport();
        //t.connect();
        //t.send(msg);
		System.out.println("Sent");
    }
	public synchronized boolean checkInit() {
		boolean flag = (this.fromaddress != null
				&& !this.toaddresslist.isEmpty() && this.username != null
				&& this.smtpport != null && this.password != null);
		System.out.println(flag);
		return flag;
	}

	/**
	 * SimpleAuthenticator is used to do simple authentication when the SMTP
	 * server requires it.
	 */
	private class SMTPAuthenticator extends javax.mail.Authenticator {

		public PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(username, password);
		}
	}

	@Override
	public String toString() {
		return "";
	}
    public static SMTPSimpleSender newSecureSMTPSimpleSender(String smtphostname,String smtpport,String username,String password,
			String fromaddress,	String toaddress){
        Properties props = new Properties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.ssl", "true");
        SMTPSimpleSender sender=new SMTPSimpleSender(smtphostname, smtpport, username, password, fromaddress, toaddress, props,false);
        return sender;
    }
    public static SMTPSimpleSender newSecureSMTPSimpleSender(String smtphostname,String username,String password,
			String fromaddress,	String toaddress){
        Properties props = new Properties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.ssl", "true");
        SMTPSimpleSender sender=new SMTPSimpleSender(smtphostname,null, username, password, fromaddress, toaddress, props,false);
        return sender;
    }

    public static SMTPSimpleSender newSMTPSimpleSender(String smtphostname,String smtpport,String username,String password,
			String fromaddress,	String toaddress){
        Properties props = new Properties();
		props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        SMTPSimpleSender sender=new SMTPSimpleSender(smtphostname, smtpport, username, password, fromaddress, toaddress,props,false);
        return sender;
    }

    public static SMTPSimpleSender newSMTPSimpleSender(String smtphostname,String username,String password,
			String fromaddress,	String toaddress){
        Properties props = new Properties();
		props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        SMTPSimpleSender sender=new SMTPSimpleSender(smtphostname,null, username, password, fromaddress, toaddress,props,false);
        return sender;
    }

    private void populateProperties(){
        sessionProperties.put("mail.smtp.host",smtphostname);
        sessionProperties.put("mail.smtp.socketFactory.port",smtpport);
    }
    private void addHeader(Message message) throws MessagingException{
        if(messageId!=null)
            message.addHeader(MESSAGE_ID,messageId);
        else{
            if(generateMessageId){
                String host=null;
                try {
                    InetAddress hostadd = InetAddress.getByName("localhost");
                    host=hostadd.getHostName();
                } catch (UnknownHostException ex) {
                    host="localhost";
                }
                message.addHeader(MESSAGE_ID,"<"+UUID.randomUUID().toString()+"@"+host+">");
            }
        }
        if(replyTo!=null){
            message.addHeader(REPLY_TO,replyTo);
        }
        if(inReplyTo!=null){
            message.addHeader(IN_REPLY_TO,inReplyTo);
        }
    }
    private void makeContent() throws TransformerException{

        if (message instanceof String && "text/plain".equals(contentType)) {
            bytemsg=message.toString().getBytes();
        }
        else if("text/xml".equals(contentType)||"text/html".equals(contentType)){
            if(xform==null){
                try {
                    xform = TransformerFactory.newInstance().newTransformer();
                } catch (TransformerConfigurationException ex) {
                    ex.printStackTrace();
                }
            }
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            if(message instanceof String){
                xform.transform(new StreamSource(new StringReader((String) message)),new StreamResult(baos));
            }
            else if(message instanceof Source){
                xform.transform((Source)message,new StreamResult(baos));
            }
            else if(message instanceof Node){
                xform.transform(new DOMSource((Node) message),new StreamResult(baos));
            }
            bytemsg=baos.toByteArray();
        }
    }
}


