// ConfigFileException.java

package httpserver.com.mk.httpserver;

/**
 * This exception is thrown when an HTTPServer configuration file
 * cannot be read in properly so that all required parameters are
 * initialized.
 */

class ConfigFileException extends Exception {

  /**
   * Constructs a ConfigFileException with the specified detail message,
   * which provides more information about why this exception has been
   * thrown.
   */
  public ConfigFileException(String msg) {
    super(msg);
  }
}