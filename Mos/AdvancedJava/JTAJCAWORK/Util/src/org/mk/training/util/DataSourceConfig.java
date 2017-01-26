/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mk.training.util;

/**
 *
 * @author veenamohitkumar
 */
public interface DataSourceConfig {

    /*static final String USERNAME = "scott";
    static final String PASSWORD = "tiger";
    static final String URL = "jdbc:oracle:thin:@192.168.1.8:1521:oracle";
    static final String DRIVER = "oracle.jdbc.OracleDriver";
    static final String SCHEMA=null;*/

    /*static final String USERNAME = "test";
    static final String PASSWORD = "test";
    static final String URL = "jdbc:derby://localhost:1527/Test";
    //static final String URL = "jdbc:derby://localhost:1527/TrainingDB;user=nbuser;password=nbuser";
    //static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    static final String DRIVER = "org.apache.derby.jdbc.ClientDriver";
    static final String SCHEMA="APP";*/
    
    static final String USERNAME = "root";
    static final String PASSWORD = "tiger";
    static final String URL = "jdbc:mysql://localhost:3306/test";
    //static final String URL = "jdbc:mysql://192.168.1.10:3306/test";
    static final String DRIVER = "com.mysql.jdbc.Driver";
    
}
