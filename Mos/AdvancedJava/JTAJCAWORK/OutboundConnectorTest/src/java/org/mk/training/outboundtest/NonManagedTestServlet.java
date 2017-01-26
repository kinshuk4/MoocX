package org.mk.training.outboundtest;

import java.io.IOException;
import java.sql.Connection;

import javax.annotation.Resource;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/*
 * http://localhost:8080/OutboundConnectorTest/NonManagedTestServlet
 */
public class NonManagedTestServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {

    public NonManagedTestServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {

            Connection con = ds.getConnection();
            System.out.println("con.getAutoCommit()" + con.getAutoCommit());
            System.out.println("con.getTransactionIsolation()" + con.getTransactionIsolation());
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            System.out.println("con.getTransactionIsolation() after :: " + con.getTransactionIsolation());
            System.out.println("con.getClass().getName()" + con.getClass().getName());
            System.out.println("ds.getClass().getName()" + ds.getClass().getName());
            con.setAutoCommit(false);
            con.commit();
            System.out.println("con.getAutoCommit()" + con.getAutoCommit());
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Resource(mappedName="java:/TitanDB")
    private DataSource ds;
}
