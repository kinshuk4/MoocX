package simple.InetAddress;



import java.net.InetAddress;

/**
 * A command-line utility for performing regular and reverse DNS lookups
 */
public class DNSResolver {

  /**
   * Expects single argument containing a domain name or dotted decimal
   * IP address and outputs the domain-address mappings.
   */
  public static void main(String[] args) {
    if (args.length != 1) {
      System.err.println("Usage: DNSResolver [ <name> | <address> ]");
      System.exit(1);
    }

    // Determine if argument is an IP address or a domain name: Since
    // domain names cannot start with a number, check for that.
    boolean isReverseLookup = Character.isDigit(args[0].charAt(0));

    try {
      InetAddress[] addresses = InetAddress.getAllByName(args[0]);

      for(int a=0; a<addresses.length; a++) {
        InetAddress address = addresses[a];

        if (isReverseLookup) {
          if (address.getHostAddress().equals(address.getHostName())) {
            System.out.println("Could not reverse resolve " +
                               address.getHostAddress());
          } else {
            System.out.println(address.getHostAddress() +
                               " reverse resolves to " +
                               address.getHostName());
          }
        } else {
          System.out.println(address.getHostName() +
                             " resolves to " +
                             address.getHostAddress());
        }
      }
    } catch (java.net.UnknownHostException e) {
      System.err.println("Cannot resolve " + args[0]);
      System.exit(1);
    } catch (java.lang.SecurityException e) {
      System.err.println("A security manager has been installed and the" +
                         " 'resolve' java.net.SocketPermission has not" +
                         " been granted");
      System.exit(1);
    }
  }
}
