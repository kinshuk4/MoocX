// HTTPHeadHandler.java

package httpserver.com.mk.httpserver;

import java.io.*;
import java.net.*;

/**
 * This class implements the HEAD method. The HEAD method is identical to
 * GET except that the server must not return any entity body in the
 * response.
 */
class HTTPHeadHandler extends HTTPHandler {

  // Requested object (file or process)
  private HTTPObject httpObject;

  /**
   * Constructs an HTTPHeadHandler.
   */
  public HTTPHeadHandler(HTTPInformation info, String uri, 
                         HTTPMessageHeaders headers, InetAddress address, 
                         String protocol, BufferedInputStream bis, 
                         BufferedOutputStream bos) {
    super(info, uri, headers, address, protocol, bis, bos);
  }

  /**
   * Processes a HEAD method by creating the requested object and returning
   * the requested status information.
   */
  public void process() throws HTTPException {
    info.requestMethod = METHOD_HEAD;

    // Get HTTPObject that is addressed by the URI and process it
    httpObject = HTTPObject.getHTTPObject(response, info, uri, bos, bis);

    // For a HEAD method we only return header information
    info.status = HTTPStatus.OK;
    response.printStatus(bos, info.serverProtocol, info.status);
    if (httpObject.isHeadersGenerated()) {
      response.printHeaders(bos);
    } 
  } 
}
