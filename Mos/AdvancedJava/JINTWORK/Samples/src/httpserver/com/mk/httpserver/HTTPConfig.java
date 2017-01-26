// HTTPConfig.java

package httpserver.com.mk.httpserver;

import java.util.*;
import java.io.*;
import java.net.*;

/**
 * This class encapsulates the HTTP configuration read in from
 * the configuration file specified as a parameter at server startup.
 */
class HTTPConfig extends HTTPConstants {

  // Class constants
  private final static int MAX_PORT = 65535;
  private final static int MIN_PORT = 0;

  // Convenience config object
  public static HTTPConfig config = null;

  // Error messages
  private HTTPLocalizedResources resources;

  // Configuration settings
  private HTTPLocalizedResources httpConfig;
  private int port;
  private String documentRoot;
  private String directoryIndex;
  private File logfile;
  private String cgiPath;
  private InetAddress bindAddress;

  /**
   * Constructs an HTTP configuration from a configuration file.
   */
  protected HTTPConfig(String configFile) throws ConfigFileException {

    // Read in message resources
    try {
      resources = new HTTPLocalizedResources("msg.httpconfig");
    } catch (MissingResourceException mre) {

      // The resource is missing, throw exception with error message
      throw new ConfigFileException(resources
        .getResourceString(RESOURCE_ERROR));
    } 

    // Read in configuration file
    try {
      httpConfig = new HTTPLocalizedResources(configFile);
    } catch (MissingResourceException mre) {

      // The resource is missing, throw exception with error message
      throw new ConfigFileException(resources
        .getResourceString("ERROR_UNABLE_TO_READ_CONFIG_FILE"));
    } 

    // The configuration file could be correctly read, now initialize
    // instance variables. Parse port
    try {
      port = Integer.parseInt(httpConfig.getResourceString("Port"));
    } catch (NumberFormatException nfe) {

      // The port was not a valid integer value
      throw new ConfigFileException(resources
        .getResourceString("ERROR_INVALID_PORT"));
    } 

    // Check the range of the port value
    if (port < MIN_PORT 
            || port > MAX_PORT) {   // The port value is out of range
      throw new ConfigFileException(resources
        .getResourceString("ERROR_INVALID_PORT"));

      // Get document root
    } 
    documentRoot = httpConfig.getResourceString("DocumentRoot");

    // Check if this is a valid and existing directory
    try {
      File dir = new File(documentRoot);
      if (!dir.isDirectory()) {
        throw new Exception();
      } 
    } catch (Exception e) {

      // Invalid document root directory
      throw new ConfigFileException(resources
        .getResourceString("ERROR_INVALID_DOCUMENTROOT"));
    } 

    // Get CGIPath where all CGI scripts are stored
    cgiPath = httpConfig.getResourceString("CGIPath");

    // Check if this is a valid and existing directory
    try {
      File dir = new File(cgiPath);
      if (!dir.isDirectory()) {
        throw new Exception();
      } 
    } catch (Exception e) {

      // Invalid document root directory
      throw new ConfigFileException(resources
        .getResourceString("ERROR_INVALID_CGIPATH"));
    } 

    // Get directory index
    directoryIndex = httpConfig.getResourceString("DirectoryIndex");

    // The directory index must not be null and not empty
    if (directoryIndex == null || directoryIndex.length() == 0) {
      throw new ConfigFileException(resources
        .getResourceString("ERROR_INVALID_DIRECTORYINDEX"));

      // Get log file
    } 
    String logfilename = httpConfig.getResourceString("LogFile");

    // The log file must be a valid file we can append entries to
    try {
      logfile = new File(logfilename);
      if (!logfile.exists()) {

        // Create log file
        FileOutputStream fos = new FileOutputStream(logfile);
        fos.close();
      } 

      // Check if the logfile is a file and we can write to it
      if (!logfile.isFile() ||!logfile.canWrite()) {
        throw new Exception();
      } 
    } catch (Exception e) {
      throw new ConfigFileException(resources
        .getResourceString("ERROR_INVALID_LOGFILE"));
    } 

    // Get bind address
    String strBindAddress = httpConfig.getResourceString("BindAddress");
    if (strBindAddress != null && strBindAddress.length() > 0) {

      // The bind address must be a valid IP address for this host
      // or an empty string
      strBindAddress = strBindAddress.trim();
      try {
        int i = 0;
        InetAddress[] localAddresses = 
          InetAddress
            .getAllByName(InetAddress.getLocalHost().getHostName());
        if (localAddresses.length > 0) {
          String localIPAddresses[] = new String[localAddresses.length];
          for (i = 0; i < localAddresses.length; i++) {
            if (localAddresses[i].getHostAddress().compareTo(strBindAddress) 
                    == 0) {
              break;
            } 
          } 

          // This will throw an exception when we have not found the address
          bindAddress = 
            InetAddress.getByName(localAddresses[i].getHostAddress());
        } else {
          throw new Exception();
        }
      } catch (Exception e) {
        throw new ConfigFileException(resources
          .getResourceString("ERROR_INVALID_BINDADDRESS"));
      } 
    } else {
      bindAddress = null;
    }
  }

  /**
   * Returns the bind address or null if the server should listen
   * on all available addresses.
   */
  public InetAddress getBindAddress() {
    return bindAddress;
  } 

  /**
   * Returns the path where CGI programs are stored on the server.
   */
  public String getCGIPath() {
    return cgiPath;
  } 

  /**
   * Returns the directory index which is the default file that is
   * requested when the requested URI points to a directory.
   */
  public String getDirectoryIndex() {
    return directoryIndex;
  } 

  /**
   * Returns the path where requested file resources are stored.
   */
  public String getDocumentRoot() {
    return documentRoot;
  } 

  /**
   * Returns the log file.
   */
  public File getLogfile() {
    return logfile;
  } 

  /**
   * Returns the port at which the server should listen for incoming requests.
   */
  public int getPort() {
    return port;
  } 

  /**
   * Initializes the HTTP configuration.
   */
  public static void initializeConfig(String configFile) 
          throws ConfigFileException {
    config = new HTTPConfig(configFile);
  } 
}
