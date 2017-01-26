// HTTPHandler.java

package httpserver.com.mk.httpserver;

import java.io.*;
import java.net.*;

/**
 * Encapsulates an HTTP method handler such as GET, HEAD or POST.
 */
abstract class HTTPHandler extends HTTPConstants {

  // Instance variables
  protected HTTPResponse response;
  protected HTTPMessageHeaders headers;
  protected BufferedInputStream bis;
  protected BufferedOutputStream bos;
  protected String uri;
  protected HTTPInformation info;

  /**
   * Constructs an HTTPHandler. This constructor can only be called from the
   * respective method handler class.
   */
  public HTTPHandler(HTTPInformation info, String uri, 
                     HTTPMessageHeaders headers, InetAddress address, 
                     String protocol, BufferedInputStream bis, 
                     BufferedOutputStream bos) {

    // Initialize instance variables
    this.headers = headers;
    this.info = info;
    this.bis = bis;
    this.bos = bos;
    this.uri = uri;

    // Create an HTTP response
    response = new HTTPResponse();

    // Set up information
    info.serverProtocol = protocol;
    info.accept = headers.getHeader(HEADER_FIELD_ACCEPT);
    info.contentLength = headers.getHeader(HEADER_FIELD_CONTENTLENGTH);
    info.contentType = headers.getHeader(HEADER_FIELD_CONTENTTYPE);
    info.pragma = headers.getHeader(HEADER_FIELD_PRAGMA);
    info.referer = headers.getHeader(HEADER_FIELD_REFERER);
    info.userAgent = headers.getHeader(HEADER_FIELD_USERAGENT);
    info.remoteAddr = address.getHostAddress().toString();
    info.remoteHost = address.getHostName();
  }

  /**
   * Processes the corresponding method.
   */
  public abstract void process() throws HTTPException;
}
