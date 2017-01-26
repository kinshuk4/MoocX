package simple.multicast;

// MulticastChat.java

import java.io.*;
import java.net.*;
import java.util.*;

public class MulticastChat extends Thread {

  // Identifies a JOIN multicast chat PDU
  public static final int JOIN = 1;

  // Identifies a LEAVE multicast chat PDU
  public static final int LEAVE = 2;

  // Identifies a MESSAGE multicast chat PDU
  public static final int MESSAGE = 3;

  // Chat protocol magic number (preceeds all requests)
  public static final long CHAT_MAGIC_NUMBER = 4969756929653643804L;

  // Default number of milliseconds between terminations checks
  public static final int DEFAULT_SOCKET_TIMEOUT_MILLIS = 5000;

  // Multicast socket used to send and receive multicast protocol PDUs
  protected MulticastSocket msocket;

  // Chat username
  protected String username;

  // Multicast group used
  protected InetAddress group;

  // Listener for multicast chat events
  protected MulticastChatEventListener listener;

  // Controls receive thread execution
  protected boolean isActive;

  public MulticastChat(String username, InetAddress group, int port, 
                       int ttl, 
                       MulticastChatEventListener listener) throws IOException {

    this.username = username;
    this.group = group;
    this.listener = listener;
    isActive = true;

    // create & configure multicast socket
    msocket = new MulticastSocket(port);
    msocket.setSoTimeout(DEFAULT_SOCKET_TIMEOUT_MILLIS);
    msocket.setTimeToLive(ttl);
    msocket.joinGroup(group);

    // start receive thread and send multicast join message
    start();
    sendJoin();
  }

  /**
   * Requests asynchronous termination of the receive thread,
   * and sends a multicast chat LEAVE message
   */
  public void terminate() throws IOException {
    isActive = false;
    sendLeave();
  } 

  // Issues an error message
  protected void error(String message) {
    System.err.println(new java.util.Date() + ": MulticastChat: " 
                       + message);
  } 

  // Sends a multicast chat JOIN PDU
  protected void sendJoin() throws IOException {
    ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
    DataOutputStream dataStream = new DataOutputStream(byteStream);

    dataStream.writeLong(CHAT_MAGIC_NUMBER);
    dataStream.writeInt(JOIN);
    dataStream.writeUTF(username);
    dataStream.close();

    byte[] data = byteStream.toByteArray();
    DatagramPacket packet = new DatagramPacket(data, data.length, group, 
                                               msocket.getLocalPort());
    msocket.send(packet);
  } 

  // Processes a multicast chat JOIN PDU and notifies listeners
  protected void processJoin(DataInputStream istream, InetAddress address, 
                             int port) throws IOException {
    String name = istream.readUTF();

    try {
      listener.chatParticipantJoined(name, address, port);
    } catch (Throwable e) {}
  } 

  // Sends a multicast chat LEAVE PDU
  protected void sendLeave() throws IOException {

    ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
    DataOutputStream dataStream = new DataOutputStream(byteStream);

    dataStream.writeLong(CHAT_MAGIC_NUMBER);
    dataStream.writeInt(LEAVE);
    dataStream.writeUTF(username);
    dataStream.close();

    byte[] data = byteStream.toByteArray();
    DatagramPacket packet = new DatagramPacket(data, data.length, group, 
                                               msocket.getLocalPort());
    msocket.send(packet);
  } 

  // Processes a multicast chat LEAVE PDU and notifies listeners
  protected void processLeave(DataInputStream istream, InetAddress address, 
                              int port) throws IOException {
    String username = istream.readUTF();

    try {
      listener.chatParticipantLeft(username, address, port);
    } catch (Throwable e) {}
  } 

  // Sends a multicast chat MESSAGE PDU
  public void sendMessage(String message) throws IOException {

    ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
    DataOutputStream dataStream = new DataOutputStream(byteStream);

    dataStream.writeLong(CHAT_MAGIC_NUMBER);
    dataStream.writeInt(MESSAGE);
    dataStream.writeUTF(username);
    dataStream.writeUTF(message);
    dataStream.close();

    byte[] data = byteStream.toByteArray();
    DatagramPacket packet = new DatagramPacket(data, data.length, group, 
                                               msocket.getLocalPort());
    msocket.send(packet);
  } 

  // Processes a multicast chat MESSAGE PDU and notifies listeners
  protected void processMessage(DataInputStream istream, 
                                InetAddress address, 
                                int port) throws IOException {
    String username = istream.readUTF();
    String message = istream.readUTF();

    try {
      listener.chatMessageReceived(username, address, port, message);
    } catch (Throwable e) {}
  } 

  // Loops receiving and de-multiplexing chat datagrams
  public void run() {
    byte[] buffer = new byte[65508];
    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

    while (isActive) {
      try {

        // DatagramPacket instance length MUST be reset before EVERY receive
        packet.setLength(buffer.length);
        msocket.receive(packet);

        DataInputStream istream = 
          new DataInputStream(new ByteArrayInputStream(packet.getData(), 
                packet.getOffset(), packet.getLength()));

        long magic = istream.readLong();

        if (magic != CHAT_MAGIC_NUMBER) {
          continue;

        } 
        int opCode = istream.readInt();
        switch (opCode) {
        case JOIN:
          processJoin(istream, packet.getAddress(), packet.getPort());
          break;
        case LEAVE:
          processLeave(istream, packet.getAddress(), packet.getPort());
          break;
        case MESSAGE:
          processMessage(istream, packet.getAddress(), packet.getPort());
          break;
        default:
          error("Received unexpected operation code " + opCode + " from " 
                + packet.getAddress() + ":" + packet.getPort());
        }

      } catch (InterruptedIOException e) {

        /**
         * No need to do anything since the timeout is only used to
         * force a loop-back and check of the "isActive" value
         */
      } catch (Throwable e) {
        error("Processing error: " + e.getClass().getName() + ": " 
              + e.getMessage());
      } 
    } 

    try {
      msocket.close();
    } catch (Throwable e) {}
  } 
}
