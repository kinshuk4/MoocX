package com.transaction.isolationreadcommited;

import java.lang.management.ManagementFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.StandardMBean;

import com.util.AppPauser;

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
            this.commitOrRollbackChanges();
        } finally {
            this.cleanup(con, null);
        }
    }

    private void commitOrRollbackChanges() {
        try {
            System.out.println("Going to commit or rollback changes :: "
                    + Thread.currentThread().getName());
            System.out.println("Enter C(Commit)/R(Rollback)  :: ");
            String input = AppPauser.readInput();
            if (input != null && input.equalsIgnoreCase("c")) {
                con.commit();
                System.out.println("Commited  :: What else did you expect??");
            } else if (input != null && input.equalsIgnoreCase("r")) {
                con.rollback();
                System.out.println("RolledBack  :: What else did you expect??");
            } else {
                //default
                con.rollback();
                System.out.println("RolledBack  :: Default action");
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void selectAndUpdate() {
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(ReadCommitedConstants.SELECTSTATEMENT);
            ResultSet rs = stmt.executeQuery();
            System.out.println("Selected Data for update :: "
                    + Thread.currentThread().getName());
            this.printDetails(ReadCommitedConstants.SELECTSTATEMENT);
            AppPauser.waitForEnterPressed();
            PreparedStatement update = con.prepareStatement(ReadCommitedConstants.UPDATE);
            while (rs.next()) {
                AppPauser.waitForEnterPressed();
                int ID = rs.getInt("ID");
                int COST = rs.getInt("COST");
                update.setInt(1, COST + increaseby);
                update.setInt(2, ID);
                System.out.println("update.executeUpdate() :: "
                        + update.executeUpdate());
            }
            System.out.println("After update");
            this.printDetails(ReadCommitedConstants.SELECTSTATEMENT);

        } catch (SQLException e) {
            e.printStackTrace();
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
                int ANNUM = rs.getInt("ANNUM");
                int COST = rs.getInt("COST");
                System.out.println("ID :" + ID + " NAME :" + NAME + " ARTIST :"
                        + ARTIST + " ANNUM :" + ANNUM + " COST :" + COST);
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
