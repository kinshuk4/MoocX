package jdbc;

import java.sql.*;
import java.io.*;
import java.text.*;
//import oracle.jdbc.driver.OracleDriver;

public class CreateMovieTables {

  //static String driver = "COM.cloudscape.core.JDBCDriver";
  //static String protocol = "jdbc:cloudscape:";

  String title, leadActor, leadActress, type, dateOfRelease;
  Connection connection;
  Statement statement;


  public void initialize() throws SQLException, ClassNotFoundException {
    //Class.forName("oracle.jdbc.driver.OracleDriver");

    connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:Alchemy","scott","tiger");
  } 


  public void createTable() throws SQLException {
    statement = connection.createStatement();
    statement.executeUpdate("CREATE TABLE CATALOGUE(TITLE VARCHAR(256), LEAD_ACTOR VARCHAR(256), LEAD_ACCTRESS VARCHAR(256), TYPE VARCHAR(20))");
  } 


  public void insertData() throws SQLException, IOException {
    BufferedReader br = new BufferedReader(new FileReader("catalogue.txt"));

    try {
      while (true) {
        title = br.readLine();
        if (title == null) {
          break;
        } 
        leadActor = br.readLine();
        leadActress = br.readLine();
        type = br.readLine();

        String sqlString = "INSERT INTO CATALOGUE VALUES('" + title + "','" 
                           + leadActor + "','" + leadActress + "','" + type 
                           + "')";
        statement.executeUpdate(sqlString);
        br.readLine();
        // Read the termination line
      } 
    }
    catch (EOFException e) {}
    finally {
      statement.close();
      br.close();
    }
  } 


  public void insertPreparedData() throws SQLException, IOException {
    BufferedReader br = new BufferedReader(new FileReader("catalogue.txt"));

    PreparedStatement preparedStatement = 
      connection.prepareStatement("INSERT INTO CATALOGUE(title, lead_actor,lead_acctress,type)VALUES(?,?,?,?)");



    try {
      do {
        preparedStatement.clearParameters();
        title = br.readLine();
        
        leadActor = br.readLine();
        leadActress = br.readLine();
        type = br.readLine();




        preparedStatement.setString(1, title);
        preparedStatement.setString(2, leadActor);
        preparedStatement.setString(3, leadActress);
        preparedStatement.setString(4, type);


        preparedStatement.executeUpdate();
      } while(br.readLine() != null);
    } catch (EOFException e) {
    }  finally {
      preparedStatement.close();
      br.close();
    }
  } 


  public void insertBatchData() throws SQLException, IOException {
    BufferedReader br = new BufferedReader(new FileReader("catalogue.txt"));
    statement = connection.createStatement();

    try {
      do {
        title = br.readLine();
        if(title==null) break;
        leadActor = br.readLine();
        leadActress = br.readLine();
        type = br.readLine();


        String sqlString = "INSERT INTO CATALOGUE VALUES('" + title + "','" 
                           + leadActor + "','" + leadActress + "','" + type 
                           + "')";

        statement.addBatch(sqlString);
      } while (br.readLine() != null);

      statement.executeBatch();

    } catch (EOFException e) {
    } finally {
      statement.close();
      br.close();
    } 
  } 


  public void close() throws SQLException {
    try {
      connection.close();
    } catch (SQLException e) {
      throw e;
    } 
  } 


  public static void main(String arg[]) {
    CreateMovieTables movies = new CreateMovieTables();

    try {
      movies.initialize();
      movies.createTable();

      //movies.insertData();
      // movies.insertPreparedData();
       movies.insertBatchData();

      movies.close();
    } catch (SQLException sqlException) {
      while (sqlException != null) {
        sqlException.printStackTrace();
        sqlException = sqlException.getNextException();
      } 
    } catch (IOException ioException) {
      System.err.println(ioException.toString());
    } catch (Exception e) {
      System.err.println(e.toString());
      e.printStackTrace();
    } 
  } 
}