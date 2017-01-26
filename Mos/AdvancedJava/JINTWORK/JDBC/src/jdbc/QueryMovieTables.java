package jdbc;

import java.sql.*;
import java.io.*;

public class QueryMovieTables {

  static String driver = "oracle.jdbc.driver.OracleDriver";


  Connection connection;

  public void initialize() throws SQLException, ClassNotFoundException {
    Class.forName(driver);
	//("jdbc:oracle:thin:@Alchemist:1521:Alchemy","scott","tiger")
	String protocol="jdbc:oracle:thin:@Alchemist:1521:Alchemy,scott,tiger";
    connection = DriverManager.getConnection(protocol);
    connection.setAutoCommit(false);
  } 

  public void queryAll() throws SQLException {
    System.out.println("Query All");
    Statement statement = connection.createStatement();

    String sqlString = "SELECT * FROM CATALOGUE";

    ResultSet rs = statement.executeQuery(sqlString);

    while (rs.next()) {
      System.out.println("     " + rs.getString("TITLE") + ", " 
                         + rs.getString("LEAD_ACTOR") + ", " 
                         + rs.getString("LEAD_ACCTRESS") + ", " 
                         + rs.getString("TYPE"));
    } 
  } 

  public void getMetaData() throws SQLException {
    System.out.println("MetaData of ResultSet");
    Statement statement = connection.createStatement();

    String sqlString = "SELECT * FROM CATALOGUE";

    ResultSet rs = statement.executeQuery(sqlString);

    ResultSetMetaData metaData = rs.getMetaData();

    int noColumns = metaData.getColumnCount();

    for (int i = 1; i < noColumns + 1; i++) {
      System.out.println(metaData.getColumnName(i) + " " 
                         + metaData.getColumnType(i));
    } 
  } 


  public void queryByScrollableResultSet() throws SQLException {
    System.out
      .println("Checks whether DatabaseMetadata supports following methods");

    DatabaseMetaData metaData = connection.getMetaData();

    System.out.println("metaData.supportsGroupBy() :" 
                       + metaData.supportsGroupBy());

    System.out
      .println(metaData
        .supportsResultSetType(ResultSet.TYPE_SCROLL_INSENSITIVE));

    System.out
      .println(metaData
        .supportsResultSetConcurrency(ResultSet.TYPE_SCROLL_INSENSITIVE, 
                                      ResultSet.CONCUR_READ_ONLY));

    System.out.println("Query by Scrollable ResultSet");

    Statement statement = 
      connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
                                 ResultSet.CONCUR_READ_ONLY);

    String sqlString = "SELECT * FROM CATALOGUE";

    ResultSet rs = statement.executeQuery(sqlString);

    while (rs.next()) {
      System.out.println("     " + rs.getString("TITLE") + ", " 
                         + rs.getString("LEAD_ACTOR") + ", " 
                         + rs.getString("LEAD_ACCTRESS") + ", " 
                         + rs.getString("TYPE"));
    } 

    rs.previous();

    while (rs.next()) {
      System.out.println("     " + rs.getString("TITLE") + ", " 
                         + rs.getString("LEAD_ACTOR") + ", " 
                         + rs.getString("LEAD_ACCTRESS") + ", " 
                         + rs.getString("TYPE"));
    } 
  } 

  public void testScrollable() throws SQLException {
    boolean supports;

    DatabaseMetaData md = connection.getMetaData();

    supports = md.supportsResultSetType(ResultSet.TYPE_FORWARD_ONLY);

    if (supports) {
      System.out.println("TYPE_FORWARD_ONLY - Supports");
    } else {
      System.out.println("TYPE_FORWARD_ONLY - Does not support");
    } 

    supports = md.supportsResultSetType(ResultSet.TYPE_SCROLL_INSENSITIVE);
    if (supports) {
      System.out.println("TYPE_SCROLL_INSENSITIVE - Supports");

    } else {
      System.out.println("TYPE_SCROLL_INSENSITIVE - Does not support");
    } 

    supports = md.supportsResultSetType(ResultSet.TYPE_SCROLL_SENSITIVE);

    if (supports) {
      System.out.println("TYPE_SCROLL_SENSITIVE - Supports");
    } else {
      System.out.println("TYPE_SCROLL_SENSITIVE - Does not support");
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
    QueryMovieTables movies = new QueryMovieTables();

    try {
      movies.initialize();
      movies.queryAll();
      movies.getMetaData();
      movies.queryByScrollableResultSet();
      movies.testScrollable();
      movies.close();

    } catch (SQLException sqlException) {
      while (sqlException != null) {
        System.err.println(sqlException.toString());
        sqlException = sqlException.getNextException();
		sqlException.printStackTrace();
      } 
    } catch (Exception e) {
      System.err.println(e.toString());
      e.printStackTrace();
    } 
  } 
}

