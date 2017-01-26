package jdbc;

import java.sql.*;
import java.io.*;
import java.text.*;

public class ConnectionClass
{
	public void initialize() throws SQLException,ClassNotFoundException ,Exception{
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");

			Connection connection = DriverManager.getConnection("jdbc:racle:thin:@Alchemist:1521:Alchemy","scott","tiger");
		}catch(SQLException sqle){System.out.println("HI1");throw new Exception("JustTesting1");}
		finally{
			
			try{
				System.out.println("HI2");
				Class.forName("oracle.jdbc.driver.OracleDrivere");
			}catch(ClassNotFoundException e){throw new Exception("JustTesting");}
			finally{System.out.println("HI3");}
			

		}
			

		
	} 



};  

