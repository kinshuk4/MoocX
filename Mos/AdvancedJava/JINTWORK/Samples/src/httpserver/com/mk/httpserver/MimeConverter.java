// MimeConverter.java

package httpserver.com.mk.httpserver;

import java.util.*;
import java.io.*;

/**
 * This class reads in the mime.properties file and returns the MIME type
 * based on the file extension of a given resource file name.
 */
class MimeConverter extends HTTPConstants {

  // Convenience converter object
  public static MimeConverter converter = null;

  // Stores the messages
  private HTTPLocalizedResources resources;

  // Stores the mime types
  private HTTPLocalizedResources mimeTypes;

  /**
   * Constructs a MimeConverter from a mime type properties file.
   */
  protected MimeConverter(String mimeFile) throws ConfigFileException {

    // Read in message resources
    try {
      resources = new HTTPLocalizedResources("msg.mimeconverter");
    } catch (MissingResourceException mre) {

      // The resource is missing, throw exception with error message
      throw new ConfigFileException(resources
        .getResourceString(RESOURCE_ERROR));
    } 

    // Read in mime type properties file
    try {
      mimeTypes = new HTTPLocalizedResources(mimeFile);
    } catch (MissingResourceException mre) {

      // The resource is missing, throw exception with error message
      throw new ConfigFileException(resources
        .getResourceString("ERROR_UNABLE_TO_READ_MIME_PROPERTIES"));
    } 
  }

  /**
   * Returns mime type based on the file extension of the passed
   * resource file name.
   */
  public String getContentType(String filename) {

    // Get file extension first
    int index = filename.lastIndexOf('.');

    // Index of first character of file extension
    int extensionIdx = index + 1;

    if (index != -1 && (extensionIdx < filename.length())) {

      // The filename has an extension
      String extension = filename.substring(extensionIdx);
      String mimeType = mimeTypes.getResourceString(extension);

      if (mimeType != null && mimeType.length() > 0) {
        return mimeType;
      } 
    } 

    return DEFAULT_MIME_TYPE;
  } 

  /**
   * Initializes the MIME type converter.
   */
  public static void initializeConverter(String mimeFile) 
          throws ConfigFileException {
    converter = new MimeConverter(mimeFile);
  } 
}
