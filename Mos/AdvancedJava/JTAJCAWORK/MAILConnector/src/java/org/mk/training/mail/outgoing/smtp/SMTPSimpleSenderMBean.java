package org.mk.training.mail.outgoing.smtp;

import java.util.List;

import javax.mail.MessagingException;

public interface SMTPSimpleSenderMBean {
	public boolean isDebug();
	public void setDebug(boolean debug);
	public String getSmtphostname() ;
	public void setSmtphostname(String smtphostname);
	public String getSmtpport() ;
	public void setSmtpport(String smtpport);
	public List<String> getToaddresslist() ;
	public void addToAddress(String toaddress);
	public String getFromaddress() ;
	public void setFromaddress(String fromaddress);
	public Object getMessage();
	public void setMessage(Object message);
	public String getMessagesubject() ;
	public void setMessagesubject(String messagesubject);
	public String getPassword() ;
	public void setPassword(String password);
	public String getUsername();
	public void setUsername(String username);
	public void sendMail(String messageSubject,Object message)throws MessagingException;
	public boolean checkInit();
	public void removeFromToAddress(String toaddress) ;
}
