// HTTPException.java

package httpserver.com.mk.httpserver;

/**
 * This exception is thrown when an exception occurs while
 * processing an HTTP request.
 */
class HTTPException extends Exception {

  // HTTP status code
  private int status;

  /**
   * Constructs an HTTPException with the specified detail message, which
   * provides more information about why this exception has been thrown.
   */
  public HTTPException(String msg, int status) {

    // Calls the superclass constructor to construct an Exception
    // with the specified detail message
    super(msg);

    // Initialize instance variables
    this.status = status;
  }

  /**
   * Returns the HTTP status code of the exception.
   */
  public int getStatus() {
    return status;
  } 
}
