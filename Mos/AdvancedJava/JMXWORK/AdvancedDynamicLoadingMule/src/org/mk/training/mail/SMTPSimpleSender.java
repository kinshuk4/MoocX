package org.mk.training.mail;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.activation.DataContentHandler;
import javax.activation.DataContentHandlerFactory;
import javax.activation.DataHandler;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.management.MBeanServer;
import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.ObjectName;

import com.sun.mail.handlers.text_html;
import com.sun.mail.handlers.text_plain;
import com.util.AppPauser;
import com.util.ObjectAnalyzer;

public class SMTPSimpleSender implements SMTPSimpleSenderMBean,
		NotificationListener {
	private String smtphostname = "smtp.gmail.com";

	private String username = "org.mk.training.jmx@gmail.com";

	private String password = null;

	private String message = "Hello World from SMTPSimpleSender";

	private String messagesubject = "Just Saying HI";

	private String fromaddress = "org.mk.training.jmx@gmail.com";

	private String smtpport = "465";

	private boolean debug = false;

	private List<String> toaddresslist = new ArrayList<String>();

	public SMTPSimpleSender() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SMTPSimpleSender(String smtphostname, String username,
			String message, String messagesubject, String fromaddress,
			String toaddress, String smtpport, boolean debug) {
		super();
		// TODO Auto-generated constructor stub
		this.smtphostname = smtphostname;
		this.username = username;
		this.message = message;
		this.messagesubject = messagesubject;
		this.fromaddress = fromaddress;
		if (toaddress != null)
			this.toaddresslist.add(toaddress);
		this.smtpport = smtpport;
		this.debug = debug;
	}

	public boolean isDebug() {
		return debug;
	}

	public synchronized void setDebug(boolean debug) {
		this.debug = debug;
	}

	public String getSmtphostname() {
		return smtphostname;
	}

	public synchronized void setSmtphostname(String smtphostname) {
		this.smtphostname = smtphostname;
	}

	public String getSmtpport() {
		return smtpport;
	}

	public synchronized void setSmtpport(String smtpport) {
		this.smtpport = smtpport;
	}

	public List<String> getToaddresslist() {
		return toaddresslist;
	}

	public synchronized void addToAddress(String toaddress) {
		this.toaddresslist.add(toaddress);
	}
	
	public synchronized void removeFromToAddress(String toaddress) {
		this.toaddresslist.remove(toaddress);
	}

	public String getFromaddress() {
		return fromaddress;
	}

	public synchronized void setFromaddress(String fromaddress) {
		this.fromaddress = fromaddress;
	}

	public String getMessage() {
		return message;
	}

	public synchronized void setMessage(String message) {
		this.message = message;
	}

	public String getMessagesubject() {
		return messagesubject;
	}

	public synchronized void setMessagesubject(String messagesubject) {
		this.messagesubject = messagesubject;
	}

	public String getPassword() {
		return password;
	}

	public synchronized void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public synchronized void setUsername(String username) {
		this.username = username;
	}

	public static void main(String args[]) throws Exception {
		SMTPSimpleSender sss = new SMTPSimpleSender("smtp.gmail.com",
				"org.mk.training.jmx@gmail.com",
				"Hello World from SMTPSimpleSender", "Just Saying HI",
				"org.mk.training.jmx@gmail.com", "org.mk.training@gmail.com",
				"465", true);
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		ObjectName empname = new ObjectName(
				"org.mk.training.mail:type=SMTPSimpleSender");
		mbs.registerMBean(sss, empname);

		System.out.println("Type in the Password");
		String pass = AppPauser.readInput();
		sss.setPassword(pass);
		sss.sendTheMail();
		System.out.println("Sucessfully Sent mail to All Users");
	}

	public synchronized void sendTheMail() throws MessagingException {
		if (!this.checkInit()) {
			throw new RuntimeException(
					"One or more of required params are not present."
							+ this.toString());
		}
		java.security.Security
				.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		// Set the host smtp address
		Properties props = new Properties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", this.getSmtphostname());
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.socketFactory.port", this.getSmtpport());
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.ssl", "true");

		Authenticator auth = new SMTPAuthenticator();
		Session session = Session.getDefaultInstance(props, auth);
		session.setDebug(this.isDebug());
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
		msg.setContent(message, "text/plain");
		System.out.println("Sending....");
		Transport.send(msg);
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
		return ObjectAnalyzer.genericToString(this);
	}

	public void handleNotification(Notification arg0, Object arg1) {
		try {
			this.setMessagesubject("HELLO FROM COOLCAT JMX"+arg0.getSequenceNumber());
			this.setMessage("Calling from jmx subsystem:: Message:: "+arg0.getMessage()+" :: Source:: "+arg0.getSource()
					+" :: getSequenceNumber():: "+arg0.getSequenceNumber()+" :: TimeStamp:: "+arg0.getTimeStamp());
			
			this.sendTheMail();
			System.out.println("Notification sent");
		} catch (MessagingException e) {

			e.printStackTrace();
		}

	}
	static{
		//must be called once hence in the static block;
		DataHandler.setDataContentHandlerFactory(new MyMailContentFactory());
	}

}

class MyMailContentFactory implements DataContentHandlerFactory {
	private text_plain tp=null;
	private text_html th=null;
	public MyMailContentFactory() {
	}

	public synchronized DataContentHandler createDataContentHandler(String parm1) {
		if (parm1.equalsIgnoreCase("text/plain")){
			if (tp==null){
				tp=new com.sun.mail.handlers.text_plain();
			}
			return tp;
		}
		else if (parm1.equalsIgnoreCase("text/html")){
			if (th==null){
				th=new com.sun.mail.handlers.text_html();
			}
			return th;
		}
		else
			return null;
	}
}
