// HTTPLog.java

package httpserver.com.mk.httpserver;

import java.io.*;
import java.util.*;

/**
 * This class logs both access and error messages.
 */
class HTTPLog extends HTTPConstants {

  // Convenience logger object
  public static HTTPLog logger = null;

  // Localized messages
  private HTTPLocalizedResources resources;

  // Instance variables
  private BufferedWriter logfileWriter;

  /**
   * Constructs an HTTPLog object.
   */
  protected HTTPLog(File logfile) throws IOException {
    try {
      resources = new HTTPLocalizedResources("msg.httplog");
    } catch (MissingResourceException mre) {

      // The resource is missing, throw exception with error message
      throw new IOException(resources.getResourceString(RESOURCE_ERROR));
    } 

    // Initialize instance variables, append to the logfile
    this.logfileWriter = 
      new BufferedWriter(new FileWriter(logfile.toString(), true));
  }

  /**
   * Flushes to file and closes the logger.
   */
  public void close() {
    try {
      logfileWriter.flush();
      logfileWriter.close();
    } catch (IOException e) {

      // Ignore exceptions when closing the logfile
    } 
  } 

  /**
   * Initializes the logger.
   */
  public static void initializeLogger(File logfile) throws IOException {
    logger = new HTTPLog(logfile);
  } 

  /**
   * Append a log entry.
   */
  public void log(HTTPInformation info) {
    String logMessage = info.remoteAddr + "-[" + new HTTPGMTTimestamp() 
                        + "]-" + info.requestString + "-" + info.status 
                        + "\r\n";
    synchronized (logfileWriter) {
      try {
        logfileWriter.write(logMessage);
      } catch (IOException e) {

        // The log could not be written, write out at least
        // a warning message to the console so that the
        // user is informed of the problem
        System.err
          .println(resources.getResourceString("ERROR_WRITING_LOG"));

      } 
    } 
  } 
}
