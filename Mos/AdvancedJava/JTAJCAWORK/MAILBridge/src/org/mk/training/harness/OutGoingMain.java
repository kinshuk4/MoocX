/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mk.training.harness;

import javax.resource.spi.ManagedConnectionFactory;
import samples.connectors.mailconnector.api.JavaMailConnection;
import samples.connectors.mailconnector.api.JavaMailConnectionFactory;
import samples.connectors.mailconnector.ra.outbound.ConnectionRequestInfoImpl;
import samples.connectors.mailconnector.ra.outbound.ManagedConnectionFactoryImpl;

/**
 *
 * @author veenamohitkumar
 */
public class OutGoingMain {
    public static void main(String args[]) throws Exception {
        /*ManagedConnectionFactory mcf=new ManagedConnectionFactoryImpl("smtp.gmail.com", "465", "org.mk.training.jmx@gmail.com", "beginner911",
          "org.mk.training.jmx@gmail.com",null,true);
        JavaMailConnectionFactory confac=(JavaMailConnectionFactory) mcf.createConnectionFactory();
        JavaMailConnection con=confac.createConnection();
        con.addToAddress("org.mk.training@gmail.com");
        con.sendMail("bugger","Bugger");
        con.close();
        con.sendMail("bugger1","Bugger1");*/

        ManagedConnectionFactory mcf=new ManagedConnectionFactoryImpl();
        JavaMailConnectionFactory confac=(JavaMailConnectionFactory) mcf.createConnectionFactory();
        JavaMailConnection con=confac.createConnection(
                new ConnectionRequestInfoImpl("org.mk.training.jmx@gmail.com","beginner911","smtp.gmail.com",null,true,"465","org.mk.training.jmx@gmail.com"));
        con.addToAddress("org.mk.training@gmail.com");
        con.sendMail("bugger","Bugger");
        con.close();
        con.sendMail("bugger1","Bugger1");
    }
}
