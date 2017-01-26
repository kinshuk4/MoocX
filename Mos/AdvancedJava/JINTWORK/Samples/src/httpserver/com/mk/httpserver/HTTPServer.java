// HTTPServer.java

package httpserver.com.mk.httpserver;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * The HTTPServer main application. It starts the server as a user
 * thread and presents a console to allow graceful shutdown.
 */

public class HTTPServer extends HTTPConstants implements Runnable {

  // Localized messages and the application itself
  private static HTTPLocalizedResources resources;






  // Check if we run with the right VM and read resources
  static {
    String vers = System.getProperty("java.version");
    if (vers.compareTo("1.3.0") < 0) {
      System.err.println(VMVERSION_ERROR);

      // Exit the VM
      System.exit(1);
    } 

    try {

      // Load locale-specific messages
      resources = new HTTPLocalizedResources("msg.httpserver");
    } catch (MissingResourceException mre) {
      System.err.println(RESOURCE_ERROR);
      System.exit(1);
    } 
  } 

  /**
   * Constructs an HTTPServer object, checks the parameters and constructs the
   * utility and configuration objects.
   */
  public HTTPServer(String[] args) {

    // Check if the correct application parameters have been provided, we
    // expect the name of the properties file
    if (args != null && args.length == 1) {
      try {
        HTTPConfig.initializeConfig(args[0]);
      } catch (ConfigFileException e) {

        // An error occurred while reading the information
        // A locale-specific error message is retrieved and displayed
        System.err.println(resources.getResourceString("ERROR_HTTP_CONFIG") 
                           + e.getMessage());
        exit(ABNORMAL_TERMINATION);
      } 
    } else {

      // Write usage information and terminate
      System.err
        .println(resources.getResourceString("ERROR_INVALID_ARGUMENT"));
      exit(ABNORMAL_TERMINATION);
    } 

    // Initialize MIME converter
    try {
      MimeConverter.initializeConverter(MIME_TYPES_FILES);
    } catch (ConfigFileException e) {

      // An error occurred while reading the mime types
      System.err.println(resources.getResourceString("ERROR_MIME_CONFIG") 
                         + e.getMessage());
      exit(ABNORMAL_TERMINATION);
    } 

    // The configuration has been read, now we can initialize the logger
    try {
      HTTPLog.initializeLogger(HTTPConfig.config.getLogfile());
    } catch (IOException e) {

      // An error occurred while reading the mime types
      System.err.println(resources.getResourceString("ERROR_OPEN_LOGFILE") 
                         + e.getMessage());
      exit(ABNORMAL_TERMINATION);
    } 
  }

  /**
   * Exits the VM with a success return code.
   */
  private void exit() {
    exit(NORMAL_TERMINATION);
  } 

  /**
   * Exits the VM with a specified exit code.
   */
  private void exit(int status) {
    System.exit(status);
  } 



  /**
   * Starts the application, creates the HTTPServer and starts it as a thread
   * and executes the server console.
   */
  public static void main(String[] args) {

    // Create an application instance
    HTTPServer theApp = new HTTPServer(args);

    // Start the server
    Thread server = new Thread(theApp);
    server.setDaemon(true);
    server.start();

    // Display console to allow the user to gracefully shut down the server
    BufferedReader br = 
      new BufferedReader(new InputStreamReader(System.in));
    BufferedWriter bw = 
      new BufferedWriter(new OutputStreamWriter(System.out));

    while (true) {
      try {
        bw.write(resources.getResourceString("CONSOLE_PROMPT"));
        bw.flush();
        String input = br.readLine();

        if (input != null && input
                .compareToIgnoreCase(resources
                  .getResourceString("CONSOLE_QUIT_COMMAND")) == 0) {

          // The user requests to shut down the server
          theApp.shutdown();
          theApp.exit();
        } 
      } catch (Exception e) {

        // Shutdown when a console exception occurs
        theApp.shutdown();
        theApp.exit(ABNORMAL_TERMINATION);
      } 
    } 
  } 

  /**
   * Runs the server.
   */
  public void run() {
    ServerSocket server = null;

    try {

      // Get port to listen at for incoming requests
      int port = HTTPConfig.config.getPort();

      // Create socket and listen for incoming connections

      if (HTTPConfig.config.getBindAddress() == null) {
        server = new ServerSocket(port);
      } else {
        server = new ServerSocket(port, DEFAULT_BACKLOG, 
                                  HTTPConfig.config.getBindAddress());
      }

      // Set an infinite timeout
      server.setSoTimeout(0);

      while (true) {
        Socket socket = server.accept();

        Thread request = new Thread(new HTTPRequest(socket));
        request.setDaemon(true);
        request.start();
      } 
    } catch (Exception e) {
      System.err.println(resources.getResourceString("ERROR_HTTP_SERVER") 
                         + e.getMessage());
      exit(ABNORMAL_TERMINATION);
    } 
    finally {
      try {
        if (server != null) {
          server.close();
        } 
      } catch (IOException e) {

        // Ignore this exception
      } 
    } 
  } 

  /**
   * Shuts down the server.
   */
  public void shutdown() {

    // Closes the log
    HTTPLog.logger.close();
  } 

  private static final int ABNORMAL_TERMINATION = 1;
  private static final int DEFAULT_BACKLOG = 50;

  // Class constants
  private static final String MIME_TYPES_FILES = "mime";
  private static final int NORMAL_TERMINATION = 0;
}
