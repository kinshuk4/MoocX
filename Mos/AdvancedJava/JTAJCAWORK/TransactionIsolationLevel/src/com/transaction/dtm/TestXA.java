package com.transaction.dtm;

import java.sql.*;


import javax.sql.*;
import javax.transaction.xa.*;



import oracle.jdbc.xa.OracleXid;
import oracle.jdbc.xa.OracleXAException;
//import oracle.jdbc.xa.client.*;
import oracle.jdbc.xa.client.OracleXADataSource;

public class TestXA {

    OracleXADataSource xaDSLocal = null;
    OracleXADataSource xaDSRemote = null;

    public TestXA() {
        // Create two XA data sources. Normally, the application server
        // would do this for you, or your program would use JNDI
        try {

            xaDSLocal = new OracleXADataSource();
            xaDSLocal.setURL("jdbc:oracle:thin:@localhost:1521:beginner");
            xaDSLocal.setUser("scott");
            xaDSLocal.setPassword("tiger");
            // xaDSLocal.setLogWriter(new PrintWriter(System.out));
            xaDSRemote = new OracleXADataSource();
            xaDSRemote.setURL("jdbc:oracle:thin:@localhost:1521:beginner");
            xaDSRemote.setUser("scott");
            xaDSRemote.setPassword("tiger");
            // xaDSRemote.setLogWriter(new PrintWriter(System.out));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        new TestXA().process();
    }

    public void process() throws SQLException {
        boolean allOk = true;
        Connection connLocal = null;
        Connection connRemote = null;
        int rows = 0;
        Statement stmt = null;
        XAConnection xaConnLocal = null;
        XAConnection xaConnRemote = null;
        XAResource xarLocal = null;
        XAResource xarRemote = null;
        Xid xidLocal = null;
        Xid xidRemote = null;
        try {
            xaConnLocal = xaDSLocal.getXAConnection("scott", "tiger");
            xaConnRemote = xaDSRemote.getXAConnection();
            connLocal = xaConnLocal.getConnection();
            connRemote = xaConnRemote.getConnection();
            connLocal.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            connRemote.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            xarLocal = xaConnLocal.getXAResource();
            System.out.println("XAResource:: " + xarLocal.getClass().getName());
            System.out.println("XAResource:: "
                    + xarLocal.getClass().getProtectionDomain().getCodeSource());
            System.out.println("xaConnection:: "
                    + xaConnLocal.getClass().getName());
            System.out.println("Connection:: " + connLocal.getClass().getName());

            xarRemote = xaConnRemote.getXAResource();
            // Create the Xids
            // Create the global ID
            byte[] globalTransactionId = new byte[64];
            globalTransactionId[0] = (byte) 1;
            // Create the local branch ID
            byte[] branchQualifierLocal = new byte[64];
            branchQualifierLocal[0] = (byte) 1;
            xidLocal = new OracleXid(0x1234, globalTransactionId,
                    branchQualifierLocal);
            // Create the remote branch ID
            byte[] branchQualifierRemote = new byte[64];
            branchQualifierRemote[0] = (byte) 2;
            xidRemote = new OracleXid(0x1234, globalTransactionId,
                    branchQualifierRemote);
            // Start the local branch of the distributed transaction
            xarLocal.start(xidLocal, XAResource.TMNOFLAGS);
            // Perform DML
            long cart = System.currentTimeMillis();
            stmt = connLocal.createStatement();
            rows = stmt.executeUpdate("insert into cart values " + " ( "
                    + Long.toString(cart)
                    + ", to_date( '20010317', 'YYYYMMDD' ) )");
            System.out.println(rows + " carts inserted");
            rows = stmt.executeUpdate("insert into item values " + " ( "
                    + Long.toString(cart + 1) + ", " + Long.toString(cart)
                    + ", 1, 'St. Patrick''s Day Banner', 19.99 )");
            System.out.println(rows + " items inserted");
            rows = stmt.executeUpdate("insert into item values " + " ( "
                    + Long.toString(cart + 2) + ", " + Long.toString(cart)
                    + ", 12, '4 Leaf Clover Party Hats', 4.49 )");
            System.out.println(rows + " items inserted");
            stmt.close();
            stmt = null;
            // End the local branch of the distributed transaction
            xarLocal.end(xidLocal, XAResource.TMSUCCESS);
            // Start the remote branch of the distributed transaction
            xarRemote.start(xidRemote, XAResource.TMNOFLAGS);
            System.out.println(xidRemote + " :::::::: " + XAResource.TMNOFLAGS);
            stmt = connRemote.createStatement();
            rows = stmt.executeUpdate("insert into shipping values " + " ( "
                    + Long.toString(cart) + ", 'Don Bales', "
                    + " '137 Universal Drive', null, 'Oracle', 'AZ', 'USA', "
                    + " '11130', 'UPS', 'Next Day' )");
            System.out.println(rows + " shippings inserted");
            rows = stmt.executeUpdate("insert into billing values " + " ( "
                    + Long.toString(cart) + ", 'Don Bales', "
                    + " 'Visa', to_date( '25250101', 'YYYYMMDD' ), 79.87 )");
            System.out.println(rows + " billings inserted");
            stmt.close();
            stmt = null;
            // End the remote branch of the distributed transaction
            xarRemote.end(xidRemote, XAResource.TMSUCCESS);
            // Prepare both branches for a two-phase commit
            int local = xarLocal.prepare(xidLocal);
            int remote = xarRemote.prepare(xidRemote);
            if (local == XAResource.XA_OK) {
                System.out.println("local XA_OK");
            } else if (local == XAResource.XA_RDONLY) {
                System.out.println("local XA_RDONLY");
            }
            if (remote == XAResource.XA_OK) {
                System.out.println("remote XA_OK");
            } else if (remote == XAResource.XA_RDONLY) {
                System.out.println("remote XA_RDONLY");
            }
            allOk = true;
            if (!((local == XAResource.XA_OK) | (local == XAResource.XA_RDONLY))) {
                allOk = false;
            }
            if (!((remote == XAResource.XA_OK) | (remote == XAResource.XA_RDONLY))) {
                allOk = false;
            }
            // Commit or roll back
            if (local == XAResource.XA_OK) {
                if (allOk) {
                    System.out.println("commit Local");
                    xarLocal.commit(xidLocal, false);
                } else {
                    System.out.println("rollback Local");
                    xarLocal.rollback(xidLocal);
                }
            }
            if (remote == XAResource.XA_OK) {
                if (allOk) {
                    System.out.println("commit Remote");
                    xarRemote.commit(xidRemote, false);
                } else {
                    System.out.println("rollback Remote");
                    xarRemote.rollback(xidRemote);
                }
            }
            // This is the end of the distributed transaction
            // Close the resources
            connLocal.close();
            connLocal = null;
            connRemote.close();
            connRemote = null;
            xaConnLocal.close();
            xaConnLocal = null;
            xaConnRemote.close();
            xaConnRemote = null;
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        } catch (XAException e) {
            System.err.println("XA Error: "
                    + ((OracleXAException) e).getOracleError());
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ignore) {
                }
            }
            if (connLocal != null) {
                try {
                    connLocal.close();
                } catch (SQLException ignore) {
                }
            }
            if (connRemote != null) {
                try {
                    connRemote.close();
                } catch (SQLException ignore) {
                }
            }
            if (xaConnLocal != null) {
                try {
                    xaConnLocal.close();
                } catch (SQLException ignore) {
                }
            }
            if (xaConnRemote != null) {
                try {
                    xaConnRemote.close();
                } catch (SQLException ignore) {
                }
            }
        }
    }
}
