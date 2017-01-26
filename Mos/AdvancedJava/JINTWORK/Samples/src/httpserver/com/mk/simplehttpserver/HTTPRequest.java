// HTTPRequest.java

package httpserver.com.mk.simplehttpserver;

import java.net.*;
import java.io.*;
import java.util.*;

class HTTPRequest {
  private Socket clientconnection;
  public HTTPRequest(Socket clientconnection) {
    this.clientconnection = clientconnection;
  }
  public void process() {

    // Obtain the client connection's input and output streams
    try {
      PrintStream os = new PrintStream(clientconnection.getOutputStream());
      BufferedReader br = 
        new BufferedReader(new InputStreamReader(clientconnection
          .getInputStream()));

      // Read the HTTP request line
      String request = br.readLine().trim();

      // Parse the requested method and resource from the request line
      StringTokenizer st = new StringTokenizer(request);

      // Read in the method
      String header_method = st.nextToken();

      // Check if we support the method
      if (!header_method.equals("GET")) {
        os.print("HTTP/1.0 501 Not Implemented\r\n");
        os.flush();
        return;
      } 

      // Read in the uri
      String header_uri = st.nextToken();

      // Read the header lines
      StringBuffer responseDocument = new StringBuffer();

      // Create response
      responseDocument
        .append("If I were a real HTTP server and you asked me to send you " 
                + header_uri + "\r\n");
      responseDocument
        .append("(which is stored relative to my document root) and you told me about\r\n");
      responseDocument
        .append("yourself with the following header lines:\r\n");

      String line = null;

      // Read the rest of the header until we encounter an empty line
      while ((line = br.readLine().trim()) != null && line.length() > 0) {
        responseDocument.append(line + "\r\n");
      } 

      responseDocument
        .append("then I would open the file, read the bytes and send them right back to you.\r\n");

      // Now we return the response
      os.print("HTTP/1.0 200 OK\r\n");
      os.print("Content-length: " + responseDocument.length() + "\r\n");
      os.print("Content-type: text/plain\r\n\r\n");
      os.print(responseDocument);
      os.flush();
    } catch (Exception e) {

      // Our exception handling is lazy here. Normally we would catch the specific
      // exceptions to provide better error handling.
      System.err.println("Unable to process HTTPRequest: " 
                         + e.getMessage());
      e.printStackTrace();
    } 
    finally {
      try {
        clientconnection.close();
      } catch (IOException e) {

        // Ignore this
      } 
    } 
  } 
}

