package org.mk.training.outboundtest;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

//import com.util.AppPauser;

/*1.Start the com.transaction.isolationreadserializable.DoubleIncreaseMain
 *2.http://localhost:8080/OutboundConnectorTest/XATestServlet
 *3.Follow the first two steps without throwing exception and
 * the uncomment the CostIncreaseWork(line 64) to throw exception
 */
public class XATestServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {

    public XATestServlet() {
        super();
    }
    Connection con1 = null;
    Connection con2 = null;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            System.out.println("ut.begin() ::");
            ut.begin();
            con1 = ds1.getConnection();
            System.out.println("con1 :: " + con1 + "con1.getTransactionIsolation():: " + con1.getTransactionIsolation());
            System.out.println("con1 :: " + con1 + "con1.getAutoCommit():: " + con1.getAutoCommit());
            con2 = ds2.getConnection();
            System.out.println("con2 :: " + con2 + "con2.getTransactionIsolation():: " + con2.getTransactionIsolation());
            System.out.println("con2 :: " + con2 + "con2.getAutoCommit():: " + con2.getAutoCommit());
            new CostIncreaseWork(con2, 10).run();
            Thread.sleep(8000);
            System.out.println("Done Sleeping :: *********");
            new CostIncreaseWork(con1, 10).run();
            System.out.println("ut.commit() :: ");
            ut.commit();
            //this.select(con1);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                ut.rollback();
            } catch (IllegalStateException e1) {
                e1.printStackTrace();
            } catch (SecurityException e1) {
                e1.printStackTrace();
            } catch (SystemException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                con1.close();
                con2.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    @Resource(mappedName="java:/XATitanDB")
    DataSource ds1 = null;
    @Resource(mappedName="java:/XATitanDB2")
    DataSource ds2 = null;
    @Resource
    UserTransaction ut = null;
}
