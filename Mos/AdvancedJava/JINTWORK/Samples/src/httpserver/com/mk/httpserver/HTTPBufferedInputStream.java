// HTTPBufferedInputStream.java

package httpserver.com.mk.httpserver;

import java.io.*;

/**
 * This class is a buffered input stream that allows to read lines as well
 * as bytes which is useful for processing HTTP POST requests.
 */
class HTTPBufferedInputStream extends BufferedInputStream {

  // Class constants
  private final static int BUFFER_SIZE = 8192;

  // Instance variables
  private String encoding;

  /**
   * Constructs an HTTPBufferedInputStream from an InputStream with a
   * specified buffer size.
   */
  public HTTPBufferedInputStream(InputStream in, int size, 
                                 String encoding) {
    super(in, size);
    this.encoding = encoding;
  }

  /**
   * Reads a line from the stream with ISO 8859-1 encoding.
   */
  public String readLine() 
          throws UnsupportedEncodingException, IOException, 
                 IndexOutOfBoundsException {
    byte[] buffer = new byte[BUFFER_SIZE];
    int index = 0;

    // Read until a line feed '\n' (as defined in RFC 1945 as the end of a
    // line) or until -1 or until an exception is encountered
    while (true) {
      buffer[index] = (byte) read();

      // Check for end of stream
      if (buffer[index] == -1) {

        // The end of the stream has been reached
        if (index == 0) {
          return null;
        } else {
          String result = new String(buffer, 0, index, encoding);
          return result.trim();
        } 
      } else if (buffer[index] == '\n') {

        // The end of the line has been reached
        String result = new String(buffer, 0, index, encoding);
        return result.trim();
      } 

      // Check whether the buffer size needs to be increased
      index++;
      if (index >= buffer.length) {

        // Allocate new, larger buffer, initialize with current buffer
        // contents and continue
        byte[] new_buffer = new byte[buffer.length * 2];
        for (int i = 0; i < buffer.length; i++) {
          new_buffer[i] = buffer[i];
        }
        buffer = new_buffer;
      } 
    } 
  } 
}

