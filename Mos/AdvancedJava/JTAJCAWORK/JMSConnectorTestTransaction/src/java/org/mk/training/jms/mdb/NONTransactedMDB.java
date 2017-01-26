package org.mk.training.jms.mdb;

import java.sql.Connection;
import java.sql.SQLException;
import javax.annotation.Resource;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

/*
 * 1.Start the com.transaction.isolationreadserializable.DoudleIncreaseMain
 * 2.start org.mk.training.jms.simple.legacy.SimpleTopicPublisher to send message
 * 3.Follow the first two steps without throwing exception and
 * the uncomment the CostIncreaseWork(line 64) to throw exception.
 * The behavior looks similar but are very very different.
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
    @ActivationConfigProperty(propertyName = "destination", propertyValue = "topic/SportsTopic")})
@TransactionManagement(TransactionManagementType.BEAN)
public class NONTransactedMDB implements MessageListener {

    public void onMessage(Message message) {
        System.out.println("NONTransactedMDB().onMessage():::::: " + message);
        if (message instanceof TextMessage) {
            try {
                this.init();
                ut.begin();
                new CostIncreaseWork(con, 10).run();
                ut.commit();
            } catch (RuntimeException e) {
                System.out.println("RuntimeException");
                e.printStackTrace();
                try {
                    ut.rollback();
                } catch (IllegalStateException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (SecurityException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (SystemException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            } catch (NotSupportedException e) {
                System.out.println("NotSupportedException");
                e.printStackTrace();
            } catch (SystemException e) {
                System.out.println("SystemException");
                e.printStackTrace();
            } catch (RollbackException e) {
                System.out.println("RollbackException");
                e.printStackTrace();
            } catch (HeuristicMixedException e) {
                System.out.println("HeuristicMixedException");
                e.printStackTrace();
            } catch (HeuristicRollbackException e) {
                System.out.println("HeuristicRollbackException");
                e.printStackTrace();
            } finally {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @Resource(mappedName = "java:/XATitanDB")
    DataSource ds1 = null;
    Connection con = null;
    @Resource
    UserTransaction ut = null;

    private void init() {
        try {
            con = ds1.getConnection();
            System.out.println("con.getAutoCommit():: " + con.getAutoCommit());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
