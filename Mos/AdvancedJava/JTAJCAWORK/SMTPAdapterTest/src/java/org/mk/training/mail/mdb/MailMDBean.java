package org.mk.training.mail.mdb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.mk.training.mail.inflow.MailListener;


import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.InternetAddress;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "mailServer", propertyValue = "pop.gmail.com"),
		@ActivationConfigProperty(propertyName = "mailFolder", propertyValue = "Inbox"),
		@ActivationConfigProperty(propertyName = "storeProtocol", propertyValue = "pop3"),
		@ActivationConfigProperty(propertyName = "userName", propertyValue = "org.mk.training.jca@gmail.com"),
		@ActivationConfigProperty(propertyName = "password", propertyValue = "beginner911"),
		@ActivationConfigProperty(propertyName = "port", propertyValue = "995"),
		@ActivationConfigProperty(propertyName = "debug", propertyValue = "false")})
public class MailMDBean implements MailListener {
	public void onMessage(Message msg) {
		this.printMessage(msg);
	}
	private void printMessage(Message message) {
		try {
			// -- Get the header information --
			String from = ((InternetAddress) message.getFrom()[0])
					.getPersonal();
			if (from == null)
				from = ((InternetAddress) message.getFrom()[0]).getAddress();
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

			if (contentType.startsWith("text/plain")
					|| contentType.startsWith("text/html")) {
				InputStream is = messagePart.getInputStream();

				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is));
				String thisLine = reader.readLine();
				System.out.println("-----------------------------");
				while (thisLine != null) {
					System.out.println(thisLine);
					thisLine = reader.readLine();
				}
			}

			System.out.println("-----------------------------");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
