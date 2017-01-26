// HTTPMessageHeaders.java

package httpserver.com.mk.httpserver;

import java.util.*;

/**
 * This class encapsulates the actual headers of a request or response.
 */
class HTTPMessageHeaders extends Hashtable {

  /**
   * Constructs an empty HTTPMessageHeaders object.
   */
  public HTTPMessageHeaders() {

    // Create a hashtable
    super();
  }

  /**
   * Adds a header.
   */
  public void addHeader(String key, String value) {
    if (key != null && value != null) {
      put(key.toLowerCase(), value);
    } 
  } 

  /**
   * Returns a header or null if not found.
   */
  public String getHeader(String key) {
    return (String) get(key.toLowerCase());
  } 
}
