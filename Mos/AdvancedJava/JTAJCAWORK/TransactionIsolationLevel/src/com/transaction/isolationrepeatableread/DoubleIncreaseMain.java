package com.transaction.isolationrepeatableread;

import java.sql.Connection;
import java.sql.DriverManager;
import org.mk.training.util.DataSourceConfig;



public class DoubleIncreaseMain {

	
	public static void main(String[] args) throws Exception {
		Class.forName(RepeatableReadConstants.DRIVER);
		Connection con=DriverManager.getConnection(DataSourceConfig.URL,
				DataSourceConfig.USERNAME,
				DataSourceConfig.PASSWORD);
		con.setTransactionIsolation(RepeatableReadConstants.ISOLATION_LEVEL);
		con.setAutoCommit(false);
		Thread.currentThread().setName("DOUBLEWHAMMY");
		new CostIncreaseWork(con,20).run();


		
	}

}
