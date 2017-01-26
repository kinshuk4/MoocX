// HTTPConstants.java

package httpserver.com.mk.httpserver;

/**
 * This class defines non-locale specific constants for the
 * HTTP classes.
 */
abstract class HTTPConstants {

  // Resource error string
  protected static final String RESOURCE_ERROR = 
    "Unable to read properties.";
  protected static final String VMVERSION_ERROR = 
    "This application must be run with a 1.3.0 or higher version VM.";

  // HTTP protocol constants
  protected static final String SERVER_SOFTWARE = "HTTPServer/1.0";
  protected static final String GATEWAY_VERSION = "CGI/1.1";
  protected static final String MIME_VERSION = "1.0";
  protected static final String DEFAULT_MIME_TYPE = "text/plain";
  protected static final String HTTP_09 = "HTTP/0.9";
  protected static final String HTTP_10 = "HTTP/1.0";
  protected static final String HTTP_11 = "HTTP/1.1";
  protected static final String HTTP_ENCODING = "8859_1";
  protected static final char HTTP_HEADER_FIELD_SEPERATOR = ':';
  protected static final String METHOD_GET = "GET";
  protected static final String METHOD_POST = "POST";
  protected static final String METHOD_HEAD = "HEAD";

  // Request and response header fields
  // General headers
  protected static final String HEADER_FIELD_DATE = "Date";
  protected static final String HEADER_FIELD_PRAGMA = "Pragma";

  // Header fields
  protected static final String HEADER_FIELD_CONTENTLENGTH = 
    "Content-Length";
  protected static final String HEADER_FIELD_CONTENTTYPE = "Content-Type";
  protected static final String HEADER_FIELD_REFERER = "Referer";
  protected static final String HEADER_FIELD_USERAGENT = "User-Agent";
  protected static final String HEADER_FIELD_SERVER = "Server";

  // Additional header fields
  protected static final String HEADER_FIELD_ACCEPT = "Accept";
  protected static final String HEADER_FIELD_MIMEVERSION = "MIME-Version";

  // Buffer, backlog and timeouts
  protected int BUFFER_SIZE = 8192;
}
