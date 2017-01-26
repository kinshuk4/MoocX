// HTTPFileObject.java

package httpserver.com.mk.httpserver;

import java.io.*;

/**
 * Encapsulates a requested file resource object.
 */
class HTTPFileObject extends HTTPObject {

  /**
   * Constructs an HTTPFileObject.
   */
  public HTTPFileObject(String fileName, HTTPInformation info, 
                        HTTPResponse response, 
                        BufferedOutputStream bos) throws HTTPException {

    // We do not have a browser input stream since a file object
    // is only returned for a GET request and does not require
    // additional input from the browser
    super(info, bos, null);

    // Determine if the file exists and set up response header information
    try {
      File file = new File(fileName);

      // The file must exist and must be readable
      if (!(file.exists() && file.canRead())) {
        throw new FileNotFoundException();

        // Only create an input stream if the HTTP method is not HEAD
      } 
      if (!info.requestMethod.equalsIgnoreCase(METHOD_HEAD)) {
        objectbis = new BufferedInputStream(new FileInputStream(fileName), 
                                            BUFFER_SIZE);
        objectbos = null;
      } 

      // Setup response headers Content-length and Content-type
      response.getHeaders().addHeader(HEADER_FIELD_CONTENTLENGTH, 
                                      Long.toString(file.length()));
      response.getHeaders()
        .addHeader(HEADER_FIELD_CONTENTTYPE, 
                   MimeConverter.converter.getContentType(fileName));
      headersGenerated = true;
    } catch (Exception e) {

      // Any exception means that we cannot retrieve the file or
      // file information
      throw new HTTPException(e.getMessage(), HTTPStatus.NOT_FOUND);
    } 
  }
}
