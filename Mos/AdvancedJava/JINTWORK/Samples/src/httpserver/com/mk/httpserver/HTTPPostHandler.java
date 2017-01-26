// HTTPPostHandler.java

package httpserver.com.mk.httpserver;

import java.net.*;
import java.io.*;

/**
 * This class implements the POST method. The POST method sends the entity body
 * that is enclosed in the request to the process object and returns the output
 * of the process object in the response.
 */
class HTTPPostHandler extends HTTPHandler {

  // Requested process object
  private HTTPObject object;

  /**
   * Constructs an HTTPPostHandler.
   */
  public HTTPPostHandler(HTTPInformation info, String uri, 
                         HTTPMessageHeaders headers, InetAddress address, 
                         String protocol, BufferedInputStream bis, 
                         BufferedOutputStream bos) {
    super(info, uri, headers, address, protocol, bis, bos);
  }

  /**
   * Processes a POST method by creating the requested object and returning
   * the requested status information as well as retrieving the entity body
   * from the process object.
   */
  public void process() throws HTTPException {
    info.requestMethod = METHOD_POST;

    // Get HTTPObject that is addressed by the URI
    object = HTTPObject.getHTTPObject(response, info, uri, bos, bis);

    // HTTP/0.9 did not know about POST so we do not need to check the version
    info.status = HTTPStatus.OK;
    response.printStatus(bos, info.serverProtocol, info.status);
    if (object.isHeadersGenerated()) {
      response.printHeaders(bos);

      // The headers have been written, now let the object do the rest
    } 
    object.retrieve();
  } 
}
