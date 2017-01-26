// HTTPRequest.java

package httpserver.com.mk.httpserver;

import java.net.*;
import java.io.*;
import java.util.*;

/**
 * Manages a single HTTP request coming from a client.
 */
class HTTPRequest extends HTTPConstants implements Runnable {

  // Localized messages
  private static HTTPLocalizedResources resources;

  // Read message resources
  static {
    try {
      resources = new HTTPLocalizedResources("msg.httprequest");
    } catch (MissingResourceException mre) {
      System.err.println(RESOURCE_ERROR);
      System.exit(1);
    } 
  } 

  // Instance variables
  private Socket socket;
  private BufferedOutputStream bos;
  private HTTPBufferedInputStream bis;
  private HTTPHandler handler;
  private HTTPMessageHeaders headers;
  private HTTPInformation info;
  private String header_method;
  private String header_uri;
  private String header_protocol;

  /**
   * Constructs an HTTPRequest.
   */
  public HTTPRequest(Socket socket) {

    // Initialize instance variables
    this.socket = socket;

    try {

      // As we need to read both character strings and then later in case
      // of a POST request also bytes, we need a special input stream that
      // can do both
      bis = new HTTPBufferedInputStream(socket.getInputStream(), 
                                        BUFFER_SIZE, HTTP_ENCODING);
      bos = new BufferedOutputStream(socket.getOutputStream(), BUFFER_SIZE);
    } catch (Exception e) {
      HTTPException httpre = 
        new HTTPException("ERROR_HTTP_GET_STREAMS" + e.getMessage(), 
                          HTTPStatus.INTERNAL_ERROR);
      handleException(httpre);
    } 

    headers = new HTTPMessageHeaders();
    info = new HTTPInformation();
  }

  /**
   * Parses the request lines until an empty line is encountered.
   */
  private void getHeaders() throws HTTPException {
    String line;
    boolean firstLine = true;

    try {

      // Read the request headers, line by line
      while ((line = bis.readLine()) != null && line.length() > 0) {
        if (firstLine) {

          // Store request string for logging
          info.requestString = line;

          // Parse the HTTP request line to determine the request method
          parseRequestLine(line);

          if (header_protocol.equalsIgnoreCase(HTTP_09)) {

            // No more headers, so just continue
            return;
          } 

          // The first line has been processed
          firstLine = false;
        } else {
          parseHeaderLine(line);
        } 
      } 
    } catch (IOException e) {
      throw new HTTPException(resources
        .getResourceString("ERROR_HTTP_READ_REQUEST"), HTTPStatus
          .INTERNAL_ERROR);
    } 
  } 

  /**
   * All exceptions are handled by this method.
   */
  private void handleException(HTTPException e) {
    int status = e.getStatus();
    info.status = status;

    try {

      // Return relevant information to the browser
      String statusString = HTTPStatus.getString(status);

      if (statusString == null) {

        // Unrecognized status
        status = HTTPStatus.INTERNAL_ERROR;
        statusString = HTTPStatus.getString(status);
      } 

      // Create HTTP response
      HTTPResponse response = new HTTPResponse();
      response.getHeaders().addHeader(HEADER_FIELD_CONTENTTYPE, 
                                      DEFAULT_MIME_TYPE);
      response.printStatus(bos, info.serverProtocol, status);
      response.printHeaders(bos);

      // Build status message and return HTML error information
      String statusMessage = new Integer(status).toString() + " " 
                             + statusString;
      StringBuffer message = new StringBuffer();
      message.append("<html>\n<head>\n<title>" + statusMessage 
                     + "</title>\n</head>");
      message.append("<body>\n<h1>" + statusMessage + "</h1>\n");
      message.append(e.getMessage() + "\n</body>\n</html>\n");
      byte bytes[] = message.toString().getBytes(HTTP_ENCODING);
      bos.write(bytes);
      bos.flush();
    } catch (Exception exception) {

      // The exception handler throws an exception, ignore
    } 

    // Log the error
    HTTPLog.logger.log(info);
  } 

  /**
   * Parses the header line and adds a header to the request.
   */
  private void parseHeaderLine(String header) {

    // Trim any whitespace characters
    header = header.trim();
    try {
      int colonIndex = header.indexOf(HTTP_HEADER_FIELD_SEPERATOR);
      if (colonIndex > 0) {
        String fieldName = header.substring(0, colonIndex);
        String fieldValue = header.substring(colonIndex + 1);
        headers.addHeader(fieldName, fieldValue);
      } 
    } catch (Exception e) {

      // Bad header line, ignore it or write to error log
    } 
  } 

  /**
   * Parses the first line of the request to determine URI, protocol, HTTP request method.
   */
  private void parseRequestLine(String request) throws HTTPException {

    // Remove any whitespace from both ends
    StringTokenizer st = new StringTokenizer(request.trim());

    try {

      // Read the method
      header_method = st.nextToken();

      // Read the URI
      header_uri = st.nextToken();
      header_uri = URLDecoder.decode(header_uri);
      if (header_uri.endsWith("/")) {

        // Add the default document when the URI points to a directory
        header_uri = header_uri + HTTPConfig.config.getDirectoryIndex();
      } 

      if (!st.hasMoreTokens()) {

        // When the protocol is missing, we assume it is HTTP/0.9
        header_protocol = HTTP_09;
        return;
      } else {
        header_protocol = st.nextToken();
      }
    } catch (Exception e) {

      // An error occurred while parsing the request
      throw new HTTPException(resources
        .getResourceString("ERROR_HTTP_PARSE_REQUESTLINE"), HTTPStatus
          .BAD_REQUEST);
    } 
  } 

  /**
   * Processes the HTTP request by parsing the header information first and
   * then creating the respective method object for further processing.
   */
  private void process() throws HTTPException {

    // Read in the request headers
    getHeaders();

    // Check that we have a supported protocol
    if (!(header_protocol.equalsIgnoreCase(HTTP_09) 
          || header_protocol.equalsIgnoreCase(HTTP_10) 
          || header_protocol.equalsIgnoreCase(HTTP_11))) {

      // Invalid request
      throw new HTTPException(resources
        .getResourceString("ERROR_HTTP_INVALID_PROTOCOL"), HTTPStatus
          .BAD_REQUEST);
    } 

    // Get variables required for method processing
    InetAddress address = socket.getInetAddress();

    // Check if we support the request method
    if (header_method.equals(METHOD_GET)) {
      handler = new HTTPGetHandler(info, header_uri, headers, address, 
                                   header_protocol, bis, bos);
    } else if (header_method.equals(METHOD_POST)) {
      handler = new HTTPPostHandler(info, header_uri, headers, address, 
                                    header_protocol, bis, bos);
    } else if (header_method.equals(METHOD_HEAD)) {
      handler = new HTTPHeadHandler(info, header_uri, headers, address, 
                                    header_protocol, bis, bos);
    } 

    if (handler == null) {

      // Unsupported method
      throw new HTTPException(resources
        .getResourceString("ERROR_HTTP_INVALID_PROTOCOL"), HTTPStatus
          .NOT_IMPLEMENTED);
    } 

    // Process method
    handler.process();

    // Log request
    HTTPLog.logger.log(info);
  } 

  /**
   * Processes the HTTP request in a seperate thread.
   */
  public void run() {
    try {
      process();
    } catch (HTTPException e) {
      handleException(e);
    } 
    finally {
      try {
        bos.flush();
        bos.close();
        socket.close();
      } catch (IOException e) {
        handleException(new HTTPException(resources.getResourceString("ERROR_HTTP_REQUEST_SHUTDOWN") 
                                          + e.getMessage(), HTTPStatus.INTERNAL_ERROR));
      } 
    } 
  } 
}
