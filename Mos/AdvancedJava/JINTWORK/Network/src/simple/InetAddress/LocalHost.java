package simple.InetAddress;



import java.net.InetAddress;

/**
 * A command-line utility that prints the local host name and address
 * obtained by InetAddress.getLocalHost(), and performs a reverse
 * lookup on the address to obtain the name.
 */
public class LocalHost {

  /**
   * No command-line arguments are used.
   */
  public static void main(String[] args) {

    try {
      InetAddress address = InetAddress.getLocalHost();
      System.out.println("Local address:  " + address.getHostAddress());
      System.out.println("Local hostname: " + address.getHostName());
    } catch (java.net.UnknownHostException e) {
      System.err.println("Cannot determine local host name and address:" +
                         e.getMessage());
      System.exit(1);
    }
  }
}
