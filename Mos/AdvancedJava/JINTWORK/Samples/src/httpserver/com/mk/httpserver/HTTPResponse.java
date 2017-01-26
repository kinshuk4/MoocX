// HTTPResponse.java

package httpserver.com.mk.httpserver;

import java.util.*;
import java.io.*;

/**
 * Encapsulates the response headers of an HTTP request.
 */
class HTTPResponse extends HTTPConstants {
  private HTTPMessageHeaders headers;

  /**
   * Constructs an HTTPResponse object and initializes header fields.
   */
  public HTTPResponse() {

    // Construct and add message headers required for the response
    headers = new HTTPMessageHeaders();
    headers.addHeader(HEADER_FIELD_MIMEVERSION, MIME_VERSION);
    headers.addHeader(HEADER_FIELD_SERVER, SERVER_SOFTWARE);

    // The browser expects date strings in a special format
    headers.addHeader(HEADER_FIELD_DATE, new HTTPGMTTimestamp().toString());
  }

  /**
   * Returns the response headers.
   */
  public HTTPMessageHeaders getHeaders() {
    return headers;
  } 

  /**
   * Prints the response headers to the browser and adds an empty line
   * at the end to indicate that the entity will follow.
   */
  public void printHeaders(OutputStream os) throws HTTPException {
    try {

      // Print all the fields and values that are set
      for (Enumeration e = headers.keys(); e.hasMoreElements(); ) {
        String type = (String) e.nextElement();
        String header = type + ": " + headers.getHeader(type) + "\r\n";
        byte bytes[] = header.getBytes(HTTP_ENCODING);
        os.write(bytes);
        os.flush();
      } 

      // Print an additional line break
      String lineBreak = "\r\n";
      byte bytes[] = lineBreak.getBytes(HTTP_ENCODING);
      os.write(bytes);
      os.flush();
    } catch (Exception e) {

      // All possible exceptions are rethrown as an HTTPRequest exception
      throw new HTTPException(e.getMessage(), HTTPStatus.INTERNAL_ERROR);
    } 
  } 

  /**
   * Prints the status line of the response.
   */
  public void printStatus(OutputStream os, String protocol, 
                          int status) throws HTTPException {
    String line = protocol + " " + status + " " 
                  + HTTPStatus.getString(status) + "\r\n";
    try {
      byte bytes[] = line.getBytes(HTTP_ENCODING);
      os.write(bytes);
      os.flush();
    } catch (Exception e) {

      // All possible exceptions are rethrown as an HTTPRequest exception
      throw new HTTPException(e.getMessage(), HTTPStatus.INTERNAL_ERROR);
    } 
  } 
}
