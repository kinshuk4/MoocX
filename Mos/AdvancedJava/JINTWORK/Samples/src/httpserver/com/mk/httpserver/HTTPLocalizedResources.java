// HTTPLocalizedResources.java

package httpserver.com.mk.httpserver;

import java.util.*;

/**
 * This class encapsulates localized messages
 */
class HTTPLocalizedResources {
  private ResourceBundle resources;

  /**
   * Constructs a HTTPLocalizedResources object from a properties file
   */
  public HTTPLocalizedResources(String propertiesFile) 
          throws MissingResourceException {

    // Load the localized messages from the properties file
    resources = ResourceBundle.getBundle(propertiesFile);
  }

  /**
   * Returns localized message with given key.
   */
  public String getResourceString(String res) {
    String str;
    try {
      str = resources.getString(res);
    } catch (MissingResourceException mre) {
      str = null;
    } 
    return str;
  } 
}
