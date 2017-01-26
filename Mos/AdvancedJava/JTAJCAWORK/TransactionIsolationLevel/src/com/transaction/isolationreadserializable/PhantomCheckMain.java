package com.transaction.isolationreadserializable;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import com.util.AppPauser;
import org.mk.training.util.DataSourceConfig;

public class PhantomCheckMain {

		public static void main(String[] args) throws Exception {
		Class.forName(ReadSerializableConstants.DRIVER);
		Connection con=DriverManager.getConnection(DataSourceConfig.URL,
				DataSourceConfig.USERNAME,
				DataSourceConfig.PASSWORD);
		con.setAutoCommit(ReadSerializableConstants.AUTOCOMMIT);
		PreparedStatement ps=con.prepareStatement(ReadSerializableConstants.INSERTSTATEMENT);
		ps.setInt(1, 5);
		ps.setString(2, "KAILASA");
		ps.setString(3, "KAILASH KHER");
		ps.setInt(4, 2002);
		ps.setInt(5, 29);
		
		System.out.println(ps.executeUpdate());
		AppPauser.waitForEnterPressed();
		con.commit();
		ps.close();
		con.close();
	}

}
