package simple.multicast;

// TestLocalMulticast.java

import java.net.*;

public class TestLocalMulticast {

  public static void main(String[] args)
    throws UnknownHostException, SocketException, java.io.IOException {

    int port = 5265; 
    // hard-coded for simplicity
    // local administrative domain address (no need to change)
    InetAddress group = InetAddress.getByName("239.255.10.10");

    // create datagram socket
    System.out.println("Binding multicast socket to " +
                       group.getHostAddress() + ":" + port + " ...");
    MulticastSocket msocket = new MulticastSocket(port);
    msocket.setSoTimeout(10000);
    msocket.setTimeToLive(1);  // restrict to local delivery

    // join multicast group
    System.out.println("Requesting multicast group membership ...");
    msocket.joinGroup(group);

    // create datagram payload + set destination to self
    String outMessage = "Hello multicast world!";
    byte[] data = outMessage.getBytes();
    DatagramPacket packet =
      new DatagramPacket(data, data.length, group, port);

    // send datagram (to ourself)
    System.out.println("Sending multicast message: " + outMessage);
    msocket.send(packet);

    // prepare to receive datagram
    packet.setData(new byte[512]);
    packet.setLength(512); // very important!

    // receive datagram (may time out)
    System.out.println("Waiting for multicast datagram ...");
    msocket.receive(packet);

    // print result
    String inMessage = new String(packet.getData(), 0, packet.getLength());
    System.out.println("Received message: " + inMessage);

    // leave multicast group
    System.out.println("Leaving multicast group ...");
    msocket.leaveGroup(group);

    msocket.close();
  }
}
