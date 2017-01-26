// HTTPStatus.java

package httpserver.com.mk.httpserver;

import java.util.*;

/**
 * HTTP/1.0 status codes and descriptions according to RFC 1945.
 */
class HTTPStatus {

  // Informational 1xx
  // HTTP/1.0 does not define any 1xx status

  // Successful 2xx
  // This class of status code indicates that the client's request was
  // successfully received, understood, and accepted
  public final static int OK = 200;
  public final static int CREATED = 201;
  public final static int ACCEPTED = 202;
  public final static int NO_CONTENT = 204;

  // Redirection 3xx
  // This class of status code indicates that further action needs to be
  // taken by the user agent in order to fulfill the request
  public final static int MULTIPLE_CHOICES = 300;
  public final static int MOVED_PERMANENTLY = 301;
  public final static int MOVED_TEMPORARILY = 302;
  public final static int NOT_MODIFIED = 304;

  // Client error 4xx
  // The 4xx class of status code is intended for cases in which the
  // client seems to have erred
  public final static int BAD_REQUEST = 400;
  public final static int UNAUTHORIZED = 401;
  public final static int FORBIDDEN = 403;
  public final static int NOT_FOUND = 404;

  // Server error 5xx
  // Response status codes beginning with the digit "5" indicate cases in
  // which the server is aware that it has erred or is incapable of
  // performing the request
  public final static int INTERNAL_ERROR = 500;
  public final static int NOT_IMPLEMENTED = 501;
  public final static int BAD_GATEWAY = 502;
  public final static int SERVICE_UNAVAILABLE = 503;

  // Status strings
  // Informational 1xx
  // Not used

  // Successful 2xx
  private final static String OK_STRING = "OK";
  private final static String CREATED_STRING = "Created";
  private final static String ACCEPTED_STRING = "Accepted";
  private final static String NO_CONTENT_STRING = "No Content";

  // Redirection 3xx
  private final static String MULTIPLE_CHOICES_STRING = "Multiple Choices";
  private final static String MOVED_PERMANENTLY_STRING = 
    "Moved Permanently";
  private final static String MOVED_TEMPORARILY_STRING = 
    "Moved Temporarily";
  private final static String NOT_MODIFIED_STRING = "Not Modified";

  // Client Error 4xx
  private final static String BAD_REQUEST_STRING = "Bad Request";
  private final static String UNAUTHORIZED_STRING = "Unauthorized";
  private final static String FORBIDDEN_STRING = "Forbidden";
  private final static String NOT_FOUND_STRING = "Not Found";

  // Server Error 5xx
  private final static String INTERNAL_ERROR_STRING = 
    "Internal Server Error";
  private final static String NOT_IMPLEMENTED_STRING = "Not Implemented";
  private final static String BAD_GATEWAY_STRING = "Bad Gateway";
  private final static String SERVICE_UNAVAILABLE_STRING = 
    "Service Unavailable";

  // The hashtable is used to store the strings that describe an http status code
  // under the key of the http status so that it can be used for logging
  private static Hashtable strings;

  static {
    strings = new Hashtable();

    // Informational 1xx
    // Not used

    // Successful 2xx
    strings.put(new Integer(OK), OK_STRING);
    strings.put(new Integer(CREATED), CREATED_STRING);
    strings.put(new Integer(ACCEPTED), ACCEPTED_STRING);
    strings.put(new Integer(NO_CONTENT), NO_CONTENT_STRING);

    // Redirection 3xx
    strings.put(new Integer(MULTIPLE_CHOICES), MULTIPLE_CHOICES_STRING);
    strings.put(new Integer(MOVED_PERMANENTLY), MOVED_PERMANENTLY_STRING);
    strings.put(new Integer(MOVED_TEMPORARILY), MOVED_TEMPORARILY_STRING);
    strings.put(new Integer(NOT_MODIFIED), NOT_MODIFIED_STRING);

    // Client error 4xx
    strings.put(new Integer(BAD_REQUEST), BAD_REQUEST_STRING);
    strings.put(new Integer(UNAUTHORIZED), UNAUTHORIZED_STRING);
    strings.put(new Integer(FORBIDDEN), FORBIDDEN_STRING);
    strings.put(new Integer(NOT_FOUND), NOT_FOUND_STRING);

    // Server error 5xx
    strings.put(new Integer(INTERNAL_ERROR), INTERNAL_ERROR_STRING);
    strings.put(new Integer(NOT_IMPLEMENTED), NOT_IMPLEMENTED_STRING);
    strings.put(new Integer(BAD_GATEWAY), BAD_GATEWAY_STRING);
    strings.put(new Integer(SERVICE_UNAVAILABLE), 
                SERVICE_UNAVAILABLE_STRING);
  } 

  /**
   * Returns the description for the specified HTTP status code.
   */
  public static String getString(int status) {
    return (String) strings.get(new Integer(status));
  } 
}
