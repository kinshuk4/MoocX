package org.mk.training.jms.mdb;

import java.sql.Connection;
import java.sql.SQLException;
import javax.annotation.Resource;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.TextMessage;
import javax.sql.DataSource;

/*
 * 1.Start the com.transaction.isolationreadserializable.DoudleIncreaseMain
 * 2.start org.mk.training.jms.simple.legacy.SimpleQueueSender to send message
 * 3.Follow the first two steps without throwing exception and
 * the uncomment the CostIncreaseWork(line 64) to throw exception.
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType",
    propertyValue = "javax.jms.Queue"),
    @ActivationConfigProperty(propertyName = "destination",
    propertyValue = "queue/MusicQueue")})
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class TransactedMDB implements MessageListener {

    public void onMessage(Message message) {
        System.out.println("TransactedMDB().onMessage():::::: " + message);
        if (message instanceof TextMessage) {
            try {
                this.init();
                new CostIncreaseWork(con, 10).run();
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
    private DataSource ds;
    Connection con = null;

    private void init() {
        try {
            con = ds.getConnection();
            System.out.println("ds1.getConnection()::::" + con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
