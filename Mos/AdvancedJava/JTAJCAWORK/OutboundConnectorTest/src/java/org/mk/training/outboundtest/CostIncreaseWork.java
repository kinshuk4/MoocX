package org.mk.training.outboundtest;

import java.lang.management.ManagementFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.StandardMBean;

public class CostIncreaseWork implements Runnable {

    private Connection con;
    private int increaseby;

    public CostIncreaseWork(Connection con, int increaseby) {
        this.con = con;
        this.increaseby = increaseby;
    }

    public void run() {
        try {
            this.makeMbean();
            this.selectAndUpdate();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void selectAndUpdate() {
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(ReadSerializableConstants.SELECTSTATEMENT);
            ResultSet rs = stmt.executeQuery();
            System.out.println("Selected Data for update :: "
                    + Thread.currentThread().getName());
            this.printDetails(ReadSerializableConstants.SELECTSTATEMENT);
            PreparedStatement update = con.prepareStatement(ReadSerializableConstants.UPDATE);
            while (rs.next()) {
                int ID = rs.getInt("ID");
                int COST = rs.getInt("COST");
                update.setInt(1, COST + increaseby);
                update.setInt(2, ID);
                System.out.println("update.executeUpdate() :: "
                        + update.executeUpdate());
            }
            System.out.println("After update");
            this.printDetails(ReadSerializableConstants.SELECTSTATEMENT);

        } catch (SQLException e) {
            e.printStackTrace();
            /*
             * Though this is called with a transaction context the rollback
             * is not performed because we are gobling up the exception.
             * Un comment the below lines to roll back the transaction to force
             * redelivery from JMS Provider.
             */
            /*
             * throw new RuntimeException(e);
             */
        } finally {
            this.cleanup(null, stmt);
        }
    }

    private void printDetails(String sql) {
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            System.out.println("printDetails()");
            while (rs.next()) {
                int ID = rs.getInt("ID");
                String NAME = rs.getString("NAME");
                String ARTIST = rs.getString("ARTIST");
                int YEAR = rs.getInt("ANNUM");
                int COST = rs.getInt("COST");
                System.out.println("ID :" + ID + " NAME :" + NAME + " ARTIST :"
                        + ARTIST + " ANNUM :" + YEAR + " COST :" + COST);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.cleanup(null, stmt);
        }

    }

    protected void makeMbean() {
        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            ObjectName conname = new ObjectName(
                    "com.transaction.isolationreadcommited:type=Connection" + con.toString());
            StandardMBean conbean = new StandardMBean(con, Connection.class,
                    false);
            mbs.registerMBean(conbean, conname);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void cleanup(Connection con, Statement sql) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (sql != null) {
            try {
                sql.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
