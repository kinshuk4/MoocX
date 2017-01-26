//TimeProtocolServer.java

package simple.timeprotocol;

import java.io.*;
import java.net.*;
import java.util.*;

// A network time server supporting RFC-868 over TCP.
public class TimeProtocolServer extends Thread {

  // The TCP server socket used to receive connection requests
  protected ServerSocket server = null;

  // Used to control termination of the accept thread
  protected boolean isActive = true;

  // Server socket timeout milliseconds (used to check termination)
  protected int socketTimeoutMillis = 5000;

  /**
   * Construct a new time protocol server bound to the standard port
   * 
   * @see TimeProtocolConstants#TCP_PORT
   * 
   * @exception IOException - if the TCP socket could not be created
   * @exception SecurityException - if the permission to create a TCP
   * socket is denied.
   */
  public TimeProtocolServer() throws SecurityException, IOException {
    this(TimeProtocolConstants.TCP_PORT);
  }

  /**
   * Constructs a new time protocol server bound to the specified port.
   * 
   * @param port - the TCP port number used to bind
   * 
   * @exception IOException - if the TCP socket could not be created
   * @exception SecurityException - if the permission to create a TCP
   * socket is denied.
   */
  public TimeProtocolServer(int port) 
          throws SecurityException, IOException {
    server = new ServerSocket(port);
    server.setSoTimeout(socketTimeoutMillis);
    start();
  }

  // Logs an error to System.err
  public void error(String message, Throwable e) {
    System.err.println(new Date() + ": TimeProtocolServer(" 
                       + server.getLocalPort() + "): error: " + message 
                       + ": " + e.getClass().getName() + ": " 
                       + e.getMessage());
  } 

  // Logs an information message to System.out
  public void info(String message) {
    System.out.println(new Date() + ": TimeProtocolServer(" 
                       + server.getLocalPort() + "): info: " + message);
  } 

  // Requests asynchronous termination of the server
  public void terminate() {
    isActive = false;
  } 

  /**
   * Processes a new connection.  Typically, this task would be performed
   * in a separate thread but since we don't read anything from the client
   * and computation is trivial, processing can be performed in the same
   * thread.
   */
  protected void process(Socket socket) {
    if (socket == null) {
      return;

    } 
    try {
      info("Connection from " + socket.getInetAddress().getHostName() + ":" 
           + socket.getPort());

      BufferedOutputStream bos = 
        new BufferedOutputStream(socket.getOutputStream(), 
                                 socket.getSendBufferSize());

      // number of seconds since January 1, 1900 0:0:0 UTC
      long resultSecs = 
        ((System.currentTimeMillis() + TimeProtocolConstants.EPOCH_OFFSET_MILLIS) 
         / 1000);

      bos.write((int) ((resultSecs >> 24) & 0xFF));
      bos.write((int) ((resultSecs >> 16) & 0xFF));
      bos.write((int) ((resultSecs >> 8) & 0xFF));
      bos.write((int) (resultSecs & 0xFF));

      bos.close();   // very important; needed to flush stream contents
    } catch (java.io.IOException e) {
      error("I/O Error", e);
    } 
    finally {
      try {
        socket.close();
      } catch (IOException e) {
        error("Error closing socket", e);
      } 
    } 
  } 

  // Server socket accept thread; loops until termination
  public void run() {
    info("Accepting connections on TCP port " + server.getLocalPort() 
         + "...");

    while (isActive) {
      Socket socket = null;

      try {
        socket = server.accept();
        process(socket);
      } catch (java.io.InterruptedIOException e) {

        // Used to periodically check for termination
      } catch (IOException e) {
        error("I/O Error", e);

      } catch (SecurityException e) {
        error("An unauthorized client has attempted to connect", e);

      } catch (Throwable e) {
        error("Unexpected exception", e);
      } 
    } 

    try {
      server.close();
    } catch (IOException e) {
      error("Error closing server socket", e);
    } 

    info("server thread terminated");
  } 

  /**
   * Prints usage to System.err and exits with error code 1
   */
  public static void usageExit() {
    System.err.println("Usage: TimeProtocolServer { <port> }");
    System.exit(1);
  } 

  /**
   * Command-line utility to create a time protocol server.  Invocation
   * accepts a single optional argument specifying the port number to be
   * used in binding.
   */
  public static void main(String[] args) {

    // Parse command-line arguments

    int port = 0;

    if (args.length == 0) {
      port = TimeProtocolConstants.TCP_PORT;
    } else if (args.length == 1) {
      try {
        port = Integer.parseInt(args[0]);
      } catch (NumberFormatException e) {
        usageExit();
      } 
    } else {
      usageExit();
    } 

    // Start time protocol server

    TimeProtocolServer server = null;
    try {
      server = new TimeProtocolServer(port);

    } catch (java.net.BindException e) {
      System.err.println("The server could not bind to port " + port 
                         + " (port may already be used): " 
                         + e.getMessage());
      if (port < 1024) {
        System.err.println("Warning: On UNIX systems user-level " 
                           + "processes cannot bind to ports below 1024");
      } 
    } catch (java.io.IOException e) {
      System.err.println(e.getMessage());

    } catch (SecurityException e) {
      System.err.println("Permission to bind to port " + port 
                         + " denied (check the java.security.policy): " 
                         + e.getMessage());
    } 

    if (server == null) {
      System.exit(1);

      // Join the server thread since we don't have anything else to do

    } 
    try {
      server.join();
    } catch (InterruptedException e) {
      System.err.println("Error while joined to server thread: " 
                         + e.getMessage());
      System.exit(1);
    } 
  } 
}
