// HTTPObject.java

package httpserver.com.mk.httpserver;

import java.io.*;
import java.util.*;

/**
 * Abstract base class for resource objects that are requested through a URI.
 */
abstract class HTTPObject extends HTTPConstants implements Runnable {

  // Class constants
  private static final String HTTP_CGIPATH = "/cgi-bin/";

  // Resource object streams
  protected BufferedInputStream objectbis;
  protected BufferedOutputStream objectbos;

  // Browser input and output streams
  protected BufferedInputStream browserbis;
  protected BufferedOutputStream browserbos;

  // Indicate whether the response generated any headers (only used when file resources
  // are returned, as CGI programs generate their own header information)
  protected boolean headersGenerated;

  // Request information that is primarily relevant for CGI execution
  protected HTTPInformation info;

  /**
   * Constructs an HTTPObject object.
   */
  protected HTTPObject(HTTPInformation info, BufferedOutputStream bos, 
                       BufferedInputStream bis) {

    // Initialize instance variables
    this.info = info;
    this.browserbis = bis;
    this.browserbos = bos;
  }

  /**
   * Returns the requested HTTP object based on the request URI and path translation.
   */
  public static HTTPObject getHTTPObject(HTTPResponse response, 
                                         HTTPInformation info, String uri, 
                                         BufferedOutputStream bos, 
                                         BufferedInputStream bis) throws HTTPException {

    // The request URI can point to a CGI program or file resource on the server
    // First check if there are any query parameters and save them
    String requestedPath = uri;
    String queryString = "";

    int question = uri.indexOf('?');
    try {
      if (question >= 0) {
        requestedPath = uri.substring(0, question);
        if (question < uri.length() - 1) {
          queryString = uri.substring(question + 1);
        } 
      } 
    } catch (StringIndexOutOfBoundsException e) {

      // The URI is not correctly formatted
      requestedPath = uri;
      queryString = "";
    } 

    // Save them in info, they are important for CGI execution
    info.scriptName = requestedPath;
    info.queryString = queryString;

    // Translate the path from the URI to the actual path on the server
    String translatedPath;

    // Check if the requested path is a CGI script
    if (requestedPath.startsWith(HTTP_CGIPATH)) {

      // A process object is to be created
      StringBuffer fileName = 
        new StringBuffer(HTTPConfig.config.getCGIPath());

      // Find the actual executable name relative to the CGIPath
      fileName.append(requestedPath.substring(HTTP_CGIPATH.length() - 1));

      // Change the separator characters
      translatedPath = fileName.toString().replace('/', File.separatorChar);

      // Store the translated path
      info.translatedPath = translatedPath;
      return new HTTPProcessObject(translatedPath, info, bos, bis);
    } else {

      // A file object is to be created
      StringBuffer fileName = 
        new StringBuffer(HTTPConfig.config.getDocumentRoot());

      // Change all forward slashes to the appropriate separator chars
      fileName.append(requestedPath);
      translatedPath = fileName.toString().replace('/', File.separatorChar);
      info.translatedPath = translatedPath;
      return new HTTPFileObject(translatedPath, info, response, bos);
    } 
  } 

  /**
   * Returns whether headers have been generated.
   */
  public boolean isHeadersGenerated() {
    return headersGenerated;
  } 

  /**
   * Exchanges data between the browser and the resource object.
   */
  public void retrieve() {

    // Starts the thread that will write data from the object to the browser
    Thread writer = new Thread(this);
    writer.setDaemon(true);
    writer.start();

    // Write data from the browser to the object in the same thread
    // where we created and already read from the input stream
    if (browserbis != null && objectbos != null) {
      byte[] buffer = new byte[BUFFER_SIZE];

      int bytesRead = 0;
      try {
        do {

          // This has to be non-blocking as the browser does not
          // close the stream and we would wait here forever.
          int bytesavailable = browserbis.available();
          if (bytesavailable > 0) {
            bytesRead = browserbis.read(buffer, 0, bytesavailable);

            if (bytesRead != -1) {
              objectbos.write(buffer, 0, bytesRead);
              objectbos.flush();
            } 
          } 

          // Check if CGI program is done already. If all data has
          // been transferred from the CGI program, there is no
          // point in writing data to the CGI program anymore
          if (!writer.isAlive()) {
            browserbis.close();
            objectbos.close();
            bytesRead = -1;
          } 

        } while (bytesRead != -1);
      } catch (IOException ioe) {

        // Ignore and continue, incomplete input should be handled
        // by the CGI program
      } 
    } 

    // Now we wait for the thread that writes data to the browser
    // to be finished
    try {
      writer.join();
    } catch (InterruptedException ie) {

      // Ignore and continue
    } 
  } 

  /**
   * Write bytes from the resource object to the browser for rendering.
   */
  public void run() {
    byte[] fileBuffer = new byte[BUFFER_SIZE];

    int bytesRead = 0;
    try {
      do {
        bytesRead = objectbis.read(fileBuffer);

        if (bytesRead != -1) {
          browserbos.write(fileBuffer, 0, bytesRead);
          browserbos.flush();
        } 
      } while (bytesRead != -1);
    } catch (IOException ioe) {

      // We can ignore this from the server side, the user will notice
      // and will reload the page
    } 
  } 
}
