// HTTPGetHandler.java

package httpserver.com.mk.httpserver;

import java.io.*;
import java.net.*;

/**
 * This class implements the GET method. The GET method returns the
 * requested entity body in the response.
 */
class HTTPGetHandler extends HTTPHandler {

  // Requested object (file or process)
  private HTTPObject httpObject;

  /**
   * Constructs an HTTPGetHandler.
   */
  public HTTPGetHandler(HTTPInformation info, String uri, 
                        HTTPMessageHeaders headers, InetAddress address, 
                        String protocol, BufferedInputStream bis, 
                        BufferedOutputStream bos) {
    super(info, uri, headers, address, protocol, bis, bos);
  }

  /**
   * Processes a GET method by creating the requested object and returning
   * the requested status information as well as retrieving the entity body
   * that corresponds to the requested object.
   */
  public void process() throws HTTPException {
    info.requestMethod = METHOD_GET;

    // Get HTTPObject that is addressed by the URI and process it
    httpObject = HTTPObject.getHTTPObject(response, info, uri, bos, bis);

    // According to RFC 1945 we need to be backward compatible with HTTP/0.9
    if (!info.serverProtocol.equalsIgnoreCase(HTTP_09)) {
      info.status = HTTPStatus.OK;
      response.printStatus(bos, info.serverProtocol, info.status);
      if (httpObject.isHeadersGenerated()) {
        response.printHeaders(bos);
      } 
    } 

    // The headers have been written, now let the object do the rest
    // and retrieve the data in an object specific way
    httpObject.retrieve();
  } 
}
