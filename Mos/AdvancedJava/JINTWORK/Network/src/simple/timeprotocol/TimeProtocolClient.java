// TimeProtocolClient.java

package simple.timeprotocol;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Static methods for quering network time servers using the RFC-868 protocol.
 */
public class TimeProtocolClient {

  /**
   * Prevent instantiation
   */
  private TimeProtocolClient() {}

  /**
   * Returns the number of seconds since January 1, 1900 00:00:00 UTC
   * obtained by the time server running on the given address and port.
   * 
   * @param address - the address of the time server
   * @param port - the port of the time server
   * 
   * @exception IOException - if a communications error occured while
   * contacting the time server.
   * @exception SecurityException - if the client is not permitted to
   * connect to the remote server
   */
  public static long getSecondsSinceEpoch(InetAddress address, int port) 
          throws SecurityException, IOException {

    Socket socket = new Socket(address, port);
    long result;

    try {
      BufferedInputStream bis = 
        new BufferedInputStream(socket.getInputStream(), 
                                socket.getReceiveBufferSize());

      int b1 = bis.read();
      int b2 = bis.read();
      int b3 = bis.read();
      int b4 = bis.read();

      if ((b1 | b2 | b3 | b3) < 0) {
        throw new EOFException("Server did not provide a 4-byte value");
      } 

      result = (((long) b1) << 24) + (b2 << 16) + (b3 << 8) + b4;
    } 
    finally {
      socket.close();
    } 

    return (result);
  } 

  /**
   * Returns a Date object encapsulating the current time (local timezone)
   * obtained by the time server running on the given address and port.
   * 
   * @param address - the address of the time server
   * @param port - the port of the time server
   * 
   * @exception IOException - if a communications error occured while
   * contacting the time server.
   * @exception SecurityException - if the client is not permitted to
   * connect to the remote server
   */
  public static Date getDate(InetAddress address, int port) 
          throws SecurityException, IOException {

    long millis = ((long) getSecondsSinceEpoch(address, port)) * 1000;
    return (new Date(millis - TimeProtocolConstants.EPOCH_OFFSET_MILLIS));
  } 

  /**
   * Command-line invocation of the time protocol client.  Expects the
   * time server host name or address, plus an optional port number used
   * to override the default port.  The retrieved time is printed to
   * standard out adjusted to the local timezone.
   */
  public static void main(String[] args) {

    // Parse command-line arguments
    InetAddress address = null;
    int port = TimeProtocolConstants.TCP_PORT;

    try {
      if (args.length == 1) {
        address = InetAddress.getByName(args[0]);
      } else if (args.length == 2) {
        address = InetAddress.getByName(args[0]);
        port = Integer.parseInt(args[1]);
      } else {
        System.err.println("Usage: TimeProtocolClient <server> {<port>}");
        System.exit(1);
      } 
    } catch (UnknownHostException e) {
      System.err.println("TimeProtocolClient: unknown host " 
                         + e.getMessage());
      System.exit(1);
    } catch (NumberFormatException e) {
      System.err.println("TimeProtocolClient: Invalid port number: " 
                         + e.getMessage());
      System.exit(1);
    } catch (SecurityException e) {
      System.err.println("TimeProtocolClient: permission to resolve host " 
                         + "name denied: " + e.getMessage());
      System.exit(1);
    } 

    // Retrieve current time from server and print to standard out
    try {
      System.out.println(getDate(address, port));
    } catch (java.net.UnknownHostException e) {
      System.err.println("Could not resolve host name: " + e.getMessage());
      System.exit(1);
    } catch (java.net.ConnectException e) {
      System.err.println("TimeProtocolClient: the time protocol server " 
                         + "is not running on " + address.getHostName() 
                         + ": " + port);
      System.exit(1);
    } catch (java.io.IOException e) {
      System.err.println("A communications error occured: " 
                         + e.getClass().getName() + ": " + e.getMessage());
      System.exit(1);
    } catch (SecurityException e) {
      System.err.println("The security manager refused permission to " 
                         + "connect to the remote TCP service: " 
                         + e.getMessage());
      System.exit(1);
    } 
  } 
}
