package org.mk.training.mail.mdb;

import com.grapevineim.xmpp.XmppConnection;
import com.grapevineim.xmpp.XmppConnectionFactory;
import com.grapevineim.xmpp.XmppMessage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.annotation.Resource;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.mk.training.mail.incoming.MailListener;
import org.mk.training.mail.util.ContentTypes;
import samples.connectors.mailconnector.api.JavaMailConnection;
import samples.connectors.mailconnector.api.JavaMailConnectionFactory;

@MessageDriven(activationConfig = {
    //@ActivationConfigProperty(propertyName = "mailServer", propertyValue = "pop.gmail.com"),
    @ActivationConfigProperty(propertyName = "mailServer", propertyValue = "imap.gmail.com"),
    @ActivationConfigProperty(propertyName = "mailFolder", propertyValue = "Inbox"),
    //@ActivationConfigProperty(propertyName = "storeProtocol", propertyValue = "pop3"),
    @ActivationConfigProperty(propertyName = "storeProtocol", propertyValue = "imap"),
    @ActivationConfigProperty(propertyName = "userName", propertyValue = "org.mk.training.jca@gmail.com"),
    @ActivationConfigProperty(propertyName = "password", propertyValue = "beginner911"),
    @ActivationConfigProperty(propertyName = "secureExchange", propertyValue = "true"),
    //@ActivationConfigProperty(propertyName = "port", propertyValue = "995"),
    @ActivationConfigProperty(propertyName = "port", propertyValue = "993"),
    //@ActivationConfigProperty(propertyName = "imapIdle", propertyValue = "false"),
    @ActivationConfigProperty(propertyName = "debug", propertyValue = "false")})
public class MailMessageDrivenBean implements MailListener {

    @Resource(mappedName = "java:eis/ra/smtpConnectionFactory")
    JavaMailConnectionFactory mailconfac;
    
    public void onMessage(Message msg) {
        this.printMessage(msg);
    }

    private void printMessage(Message message) {
        try {
            MimeMessage mmg = (MimeMessage) message;
            Address[] addrs = mmg.getFrom();
            System.out.println("From :" + addrs[0]);
            String subject = message.getSubject();
            System.out.println("SUBJECT: " + subject);
            String[] msgids = message.getHeader("Message-Id");
            String msgid = msgids[0];
            System.out.println("msgid: " + msgid);
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
            StringBuilder fullcontent = new StringBuilder();
            if (ContentTypes.TYPE.TEXTXML.match(contentType) || ContentTypes.TYPE.TEXTHTML.match(contentType)
                    || ContentTypes.TYPE.TEXTPLAIN.match(contentType)) {
                InputStream is = messagePart.getInputStream();

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(is));
                String thisLine = reader.readLine();
                System.out.println("-----------------------------");
                while (thisLine != null) {
                    System.out.println("DATA::::" + thisLine);
                    fullcontent.append(thisLine);
                    thisLine = reader.readLine();
                }
            }
            System.out.println("-----------------------------");
            JavaMailConnection con = mailconfac.createConnection();
            con.addToAddress(addrs[0].toString());
            con.sendMail("Just Testing", "ECHOED:" + fullcontent.toString(), "text/plain", null, null, msgid);
            con.close();
            /*if (message instanceof MimeMessage) {
            MimeMessage mmg = (MimeMessage) message;
            Address[] addrs = mmg.getFrom();
            System.out.println("From :" + addrs[0]);
            String mid = mmg.getMessageID();
            System.out.println("MessagedId :" + mid);
            System.out.println("mmg.getSender() :" + mmg.getSender());
            //Address[] raddrs=mmg.getReplyTo();
            Address[] raddrs = mmg.getReplyTo();
            String replyto = null;
            if (raddrs[0] instanceof InternetAddress) {
            InternetAddress ia = (InternetAddress) raddrs[0];
            replyto = ia.getAddress();
            }
            System.out.println("Replyto :" + replyto);


            }
            Message msg = new MimeMessage(mailsession);
            System.out.println("mailsession.getProperties():"+mailsession.getProperties());
            InternetAddress addressFrom = new InternetAddress("org.mk.training.jca@gmail.com");
            msg.setFrom(addressFrom);
            InternetAddress[] addressTo = null;
            addressTo = new InternetAddress[1];
            addressTo[0] = new InternetAddress("org.mk.training.jmx@gmail.com");
            System.out.println(addressTo[0]);
            msg.setRecipients(Message.RecipientType.TO, addressTo);
            msg.setSubject("Just Testing");
            msg.setContent("Just Testing", "text/plain");
            //Transport.send(msg);
            //mailsession.getTransport().send(msg);
            Transport t=mailsession.getTransport();
            t.connect();
            t.sendMessage(msg,addressTo);
             */
            //SMTPSimpleSender sender = SMTPSimpleSender.newSecureSMTPSimpleSender("smtp.gmail.com", "465", "org.mk.training.jca@gmail.com", "beginner911",
            //  "org.mk.training.jca@gmail.com", "org.mk.training.jmx@gmail.com");
            //sender.sendMail("HELLO",new StreamSource(new StringReader(response)),"text/xml",null,null,msgid);
            //sender.sendMail(null,response,"text/xml",null,null,msgid);
            //sender.sendMail("HELLO", "BUGGER",null,"org.mk.training.jmx@gmail.com",null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    static String response = "<?xml version='1.0' encoding='UTF-8'?>"
            + "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\"><S:Body><ns3:requestOrderResponse xmlns:ns2=\"http://e-commerceANDm-commerce/ShoppingStandards\" xmlns:ns3=\"http://org.mk.traininggmail.com/MessageDefinitions\"><Order><OrderKey>9331572480</OrderKey><OrderHeader><SalesOrganization>MyEShoppingSite</SalesOrganization><DateOfPurchase>2010-03-23T13:36:12.481+05:30</DateOfPurchase><CustomerNumber>123456</CustomerNumber><PaymentMethod>CREDITCARD</PaymentMethod><PurchaseOrderNumber>654321</PurchaseOrderNumber></OrderHeader><ns2:Cart><Item><Name>Porsche</Name><ItemNumber>700700</ItemNumber><Quantity>2</Quantity><PricePerUnit>55555</PricePerUnit></Item></ns2:Cart><ns2:CreditCard><Type>VISA</Type><Number>007007007007</Number><ExpiryDate>2012-09-13T00:00:00+05:30</ExpiryDate><Name>007007</Name><Amount>111110.0</Amount><DateOfPurchase>1900-01-01T13:36:12.434+05:53</DateOfPurchase></ns2:CreditCard><description>This Example being done with Annotation mechanism</description></Order></ns3:requestOrderResponse></S:Body></S:Envelope>";
}
