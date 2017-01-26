/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mk.training.harness;

import java.lang.management.ManagementFactory;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import org.mk.training.mail.outgoing.smtp.SMTPSimpleSender;

/**
 *
 * @author veenamohitkumar
 */
public class Main {

    public static void main(String args[]) throws Exception {
        System.out.println("Type in the Password" + System.getProperties());
        SMTPSimpleSender sender = SMTPSimpleSender.newSMTPSimpleSender("in.smtp.mail.yahoo.com", "465", "mohit.riverstone@yahoo.com", "sunshine911",
                "mohit.riverstone@yahoo.com", "org.mk.training@gmail.com");
        //SMTPSimpleSender sender = SMTPSimpleSender.newSecureSMTPSimpleSender("smtp.gmail.com", "465", "org.mk.training.jmx@gmail.com", "beginner911",
          //      "org.mk.training.jmx@gmail.com", "org.mk.training@gmail.com");
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName empname = new ObjectName(
                "org.mk.training.mail:type=SMTPSimpleSender");
        mbs.registerMBean(sender, empname);

        System.out.println("response"+response);
        //sender.sendMail("HELLO", "BUGGER");
        //sender.sendMail("HELLO", "BUGGER",null,"org.mk.training.jmx@gmail.com",null);
        sender.sendMail("HELLO",response,"text/xml");
        //sender.sendMail("HELLO",wsdl,"text/xml");
        //sender.sendMail("HELLO",new StreamSource(new StringReader(baos.toString())),"text/xml");
        System.out.println("Sucessfully Sent mail to All Users");

    }
    static String response="<?xml version='1.0' encoding='UTF-8'?>" +
            "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\"><S:Body><ns3:requestOrderResponse xmlns:ns2=\"e-commerceANDm-commerce:ShoppingStandards\" xmlns:ns3=\"org.mk.training@gmail.com/MessageDefinitions\"><Order><OrderKey>9331572480</OrderKey><OrderHeader><SalesOrganization>MyEShoppingSite</SalesOrganization><DateOfPurchase>2010-03-23T13:36:12.481+05:30</DateOfPurchase><CustomerNumber>123456</CustomerNumber><PaymentMethod>CREDITCARD</PaymentMethod><PurchaseOrderNumber>654321</PurchaseOrderNumber></OrderHeader><ns2:Cart><Item><Name>Porsche</Name><ItemNumber>700700</ItemNumber><Quantity>2</Quantity><PricePerUnit>55555</PricePerUnit></Item></ns2:Cart><ns2:CreditCard><Type>VISA</Type><Number>007007007007</Number><ExpiryDate>2012-09-13T00:00:00+05:30</ExpiryDate><Name>007007</Name><Amount>111110.0</Amount><DateOfPurchase>1900-01-01T13:36:12.434+05:53</DateOfPurchase></ns2:CreditCard><description>This Example being done with Annotation mechanism</description></Order></ns3:requestOrderResponse></S:Body></S:Envelope>";
}
