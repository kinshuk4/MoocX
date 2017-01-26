package com.transaction.basic;

import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.StandardMBean;

import org.mk.training.util.DataSourceConfig;

public class SimpleConnection {

		
	public static void main(String[] args) throws Exception {
		DriverManager.setLogWriter(new PrintWriter(System.out));
		Class.forName(DataSourceConfig.DRIVER);
		Connection con=DriverManager.getConnection(DataSourceConfig.URL,
				DataSourceConfig.USERNAME,
				DataSourceConfig.PASSWORD);
		System.out.println("con :: "+con);
		SimpleConnection.makeMbean(con);
		Thread.sleep(Integer.MAX_VALUE);
		

	}
	private static void makeMbean(Connection con) {
		try {
			MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
			ObjectName conname = new ObjectName(
					"com.transaction.isolationreadcommited:type=Connection"+con.toString());
			StandardMBean conbean = new StandardMBean(con, Connection.class,
					false);
			mbs.registerMBean(conbean, conname);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	

}
