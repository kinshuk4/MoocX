package httpserver.com.mk.simplehttpserver;

import java.net.*;

class SimpleHTTPServer {
  public static void main(String[] args) {
    ServerSocket server;
    Socket clientconnection;
    HTTPRequest request;

    try {
      server = new ServerSocket(1234);

      while (true) {
        clientconnection = server.accept();
        request = new HTTPRequest(clientconnection);
        request.process();
      } 
    } catch (Exception e) {
      System.err.println("Unable to start SimpleHTTPServer: " 
                         + e.getMessage());
      e.printStackTrace();
    } 
  } 
}