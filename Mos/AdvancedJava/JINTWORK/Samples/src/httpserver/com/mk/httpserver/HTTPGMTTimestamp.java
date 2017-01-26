// HTTPGMTTimestamp.java

package httpserver.com.mk.httpserver;

import java.text.*;
import java.util.*;

/**
 * Encapsulates a GMT timestamp.
 */
class HTTPGMTTimestamp {

  // Instance variable
  private Date currentTime;

  // Class variable
  private static SimpleDateFormat formatter = 
    new SimpleDateFormat("d MMM yyyy hh:mm:ss 'GMT'", Locale.US);

  /**
   * Consructs the current GMT timestamp.
   */
  public HTTPGMTTimestamp() {
    currentTime = new Date();
  }

  /**
   * Returns a formatted GMT timestamp.
   */
  public String toString() {
    return formatter.format(currentTime);
  } 
}
