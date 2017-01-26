package com.transaction.dtm;

import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;
import com.mysql.jdbc.jdbc2.optional.MysqlXid;
import java.sql.*;


import javax.sql.*;
import javax.transaction.xa.*;

public class TestXAFor_MYSQL {

    MysqlXADataSource xaDSLocal = null;
    MysqlXADataSource xaDSRemote = null;

    public TestXAFor_MYSQL() {
        // Create two XA data sources. Normally, the application server
        // would do this for you, or your program would use JNDI
        //try {

        xaDSLocal = new MysqlXADataSource();
        xaDSLocal.setURL("jdbc:mysql://localhost:3306/test");
        xaDSLocal.setUser("root");
        xaDSLocal.setPassword("tiger");
        // xaDSLocal.setLogWriter(new PrintWriter(System.out));
        xaDSRemote = new MysqlXADataSource();
        xaDSRemote.setURL("jdbc:mysql://192.168.1.10:3306/test");
        xaDSRemote.setUser("root");
        xaDSRemote.setPassword("tiger");
        // xaDSRemote.setLogWriter(new PrintWriter(System.out));
        //} catch (SQLException e) {
        //  System.err.println(e.getMessage());
        //e.printStackTrace();
        //}
    }

    public static void main(String[] args) throws Exception {
        new TestXAFor_MYSQL().process();
    }

    public void process() throws SQLException {
        boolean allOk = true;
        Connection connLocal = null;
        Connection connRemote = null;
        int rows = 0;
        PreparedStatement pstmt = null;
        XAConnection xaConnLocal = null;
        XAConnection xaConnRemote = null;
        XAResource xarLocal = null;
        XAResource xarRemote = null;
        Xid xidLocal = null;
        Xid xidRemote = null;
        try {
            System.out.println("XATransaction::Starting... ");
            xaConnLocal = xaDSLocal.getXAConnection();
            xaConnRemote = xaDSRemote.getXAConnection();
            connLocal = xaConnLocal.getConnection();
            connRemote = xaConnRemote.getConnection();
            System.out.println("connLocal:" + connLocal);
            System.out.println("connRemote:" + connRemote);
            connLocal.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            connRemote.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            xarLocal = xaConnLocal.getXAResource();
            xarRemote = xaConnRemote.getXAResource();

            // Create the Xids
            // Create the global ID
            byte[] globalTransactionId = new byte[64];
            globalTransactionId[0] = (byte) 1;
            // Create the local branch ID
            byte[] branchQualifierLocal = new byte[64];
            branchQualifierLocal[0] = (byte) 1;
            xidLocal = new MysqlXid(globalTransactionId,
                    branchQualifierLocal, 0x1234);
            // Create the remote branch ID
            byte[] branchQualifierRemote = new byte[64];
            branchQualifierRemote[0] = (byte) 2;
            xidRemote = new MysqlXid(globalTransactionId,
                    branchQualifierRemote, 0x1234);
            // Start the local branch of the distributed transaction
            xarLocal.start(xidLocal, XAResource.TMNOFLAGS);
            // Perform DML
            long cart = System.currentTimeMillis();
            pstmt = connLocal.prepareStatement("insert into cart values (?,?)");
            pstmt.setLong(1, cart);
            pstmt.setTimestamp(2, new Timestamp(new java.util.Date().getTime()));
            rows = pstmt.executeUpdate();
            System.out.println(rows + " carts inserted");
            pstmt = connLocal.prepareStatement("insert into item values (?,?,?,?,?)");
            pstmt.setLong(1, cart + 1);
            pstmt.setLong(2, cart);
            pstmt.setInt(3, 1);
            pstmt.setString(4, "St. Patrick''s Day Banner");
            pstmt.setInt(5, 19);
            rows = pstmt.executeUpdate();
            System.out.println(rows + " items inserted");
            pstmt.setLong(1, cart + 2);
            pstmt.setLong(2, cart);
            pstmt.setInt(3, 12);
            pstmt.setString(4, "4 Leaf Clover Party Hats");
            pstmt.setInt(5, 4);
            rows = pstmt.executeUpdate();
            System.out.println(rows + " items inserted");
            pstmt.close();
            pstmt = null;
            // End the local branch of the distributed transaction
            xarLocal.end(xidLocal, XAResource.TMSUCCESS);
            // Start the remote branch of the distributed transaction
            xarRemote.start(xidRemote, XAResource.TMNOFLAGS);
            System.out.println(xidRemote + " :::::::: " + XAResource.TMNOFLAGS);
            pstmt = connRemote.prepareStatement("insert into shipping values (?,?,?,?,?,?,?,?,?,?)");
            pstmt.setLong(1, cart);
            pstmt.setString(2, "Don Bales");
            pstmt.setString(3, "137 Universal Drive");
            pstmt.setString(4, "");
            pstmt.setString(5, "Oracle");
            pstmt.setString(6, "AZ");
            pstmt.setString(7, "USA");
            pstmt.setString(8, "11130");
            pstmt.setString(9, "UPS");
            pstmt.setString(10, "Next Day");
            rows = pstmt.executeUpdate();
            System.out.println(rows + " shippings inserted");
            pstmt = connRemote.prepareStatement("insert into billing values (?,?,?,?,?)");
            pstmt.setLong(1, cart);
            pstmt.setString(2, "Don Bales");
            pstmt.setString(3, "VISA");
            pstmt.setTimestamp(4, new Timestamp(new java.util.Date().getTime()));
            pstmt.setInt(5, 79);
            rows = pstmt.executeUpdate();
            System.out.println(rows + " billings inserted");
            pstmt.close();
            pstmt = null;
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
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        } catch (XAException e) {
            e.printStackTrace();
        } finally {
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
