package org.mk.training.outboundtest;

import java.io.IOException;
import java.sql.Connection;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.transaction.UserTransaction;

/*
 * http://localhost:8080/OutboundConnectorTest/ManagedTestServlet
 */
public class ManagedTestServlet extends javax.servlet.http.HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection con = null;
        try {
            con = ds.getConnection();
            System.out.println("con.getAutoCommit():: " + con.getAutoCommit());
            con.setAutoCommit(true);    
            ut.begin();
            System.out.println("con.getAutoCommit():: ut.begin():: " + con.getAutoCommit());
            System.out.println("con.getTransactionIsolation()" + con.getTransactionIsolation());
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            System.out.println("con.getTransactionIsolation() after :: " + con.getTransactionIsolation());
            System.out.println("con.getClass().getName()" + con.getClass().getName());
            System.out.println("ds.getClass().getName()" + ds.getClass().getName());
            //con.commit();//will throw exception
            //con.setAutoCommit(true);//will throw exception
            ut.commit();
            //System.out.println("con.getAutoCommit()" + con.getAutoCommit());
            con.close();//will throw exception if you comment
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Resource(mappedName="java:/TitanDB")
    private DataSource ds;
    @Resource
    UserTransaction ut;
    
    
}