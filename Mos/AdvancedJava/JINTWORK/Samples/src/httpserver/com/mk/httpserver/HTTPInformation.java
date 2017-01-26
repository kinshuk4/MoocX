// HTTPInformation.java

package httpserver.com.mk.httpserver;

import java.io.*;
import java.util.*;

/**
 * This class encapsulates HTTP header information for a HTTP request
 * and sets up the environment strings for CGI execution.
 */
class HTTPInformation extends HTTPConstants {

  // Instance variables
  public String serverSoftware;
  public String serverName;
  public String gateway;
  public String serverProtocol = HTTP_10;
  public int serverPort;
  public String requestMethod;
  public String pathInfo;
  public String translatedPath;
  public String scriptName;
  public String queryString;
  public String remoteHost;
  public String remoteAddr;
  public String authType;
  public String remoteUser;
  public String remoteIdent;
  public String contentType;
  public String contentLength;
  public String documentRoot;
  public String accept;
  public String host;
  public String pragma;
  public String referer;
  public String userAgent;

  // The request string and http status are stored for logging purposes
  public String requestString;
  public int status;

  // CGI 1.0 required environment variables
  private final static String env_serverSoftware = "SERVER_SOFTWARE";
  private final static String env_serverName = "SERVER_NAME";
  private final static String env_gateway = "GATEWAY_INTERFACE";
  private final static String env_serverProtocol = "SERVER_PROTOCOL";
  private final static String env_serverPort = "SERVER_PORT";
  private final static String env_requestMethod = "REQUEST_METHOD";
  private final static String env_pathInfo = "PATH_INFO";
  private final static String env_translatedPath = "PATH_TRANSLATED";
  private final static String env_scriptName = "SCRIPT_NAME";
  private final static String env_queryString = "QUERY_STRING";
  private final static String env_remoteHost = "REMOTE_HOST";
  private final static String env_remoteAddr = "REMOTE_ADDR";
  private final static String env_authType = "AUTH_TYPE";
  private final static String env_remoteUser = "REMOTE_USER";
  private final static String env_remoteIdent = "REMOTE_IDENT";
  private final static String env_contentType = "CONTENT_TYPE";
  private final static String env_contentLength = "CONTENT_LENGTH";
  private final static String env_docRoot = "DOCUMENT_ROOT";

  // Important HTTP header lines
  private final static String env_accept = "HTTP_ACCEPT";
  private final static String env_host = "HTTP_HOST";
  private final static String env_pragma = "HTTP_PRAGMA";
  private final static String env_referer = "HTTP_REFERER";
  private final static String env_userAgent = "HTTP_USER_AGENT";

  /**
   * Constructs an HTTP header information object and initializes
   * known header fields.
   */
  public HTTPInformation() {

    // Initialize known header fields
    serverSoftware = SERVER_SOFTWARE;
    gateway = GATEWAY_VERSION;
    serverPort = HTTPConfig.config.getPort();
    documentRoot = HTTPConfig.config.getDocumentRoot();
  }

  /**
   * Returns the environment string array to be passed to Runtime.exec to set
   * up the environment for CGI execution.
   */
  public String[] getCGIEnvironment() {
    Hashtable env = new Hashtable();

    // Add everything to a hashtable first
    put(env, env_serverSoftware, serverSoftware);
    put(env, env_serverName, serverName);
    put(env, env_gateway, gateway);
    put(env, env_serverProtocol, serverProtocol);
    put(env, env_serverPort, new Integer(serverPort).toString());
    put(env, env_requestMethod, requestMethod);
    put(env, env_pathInfo, pathInfo);
    put(env, env_translatedPath, translatedPath);
    put(env, env_scriptName, scriptName);
    put(env, env_queryString, queryString);
    put(env, env_remoteHost, remoteHost);
    put(env, env_remoteAddr, remoteAddr);
    put(env, env_authType, authType);
    put(env, env_remoteUser, remoteUser);
    put(env, env_remoteIdent, remoteIdent);
    put(env, env_contentType, contentType);
    put(env, env_contentLength, contentLength);
    put(env, env_docRoot, documentRoot);
    put(env, env_accept, accept);
    put(env, env_host, host);
    put(env, env_pragma, pragma);
    put(env, env_referer, referer);
    put(env, env_userAgent, userAgent);

    // Construct an environment string array
    String cgi_env[] = new String[env.size()];

    int i = 0;
    for (Enumeration e = env.keys(); e.hasMoreElements(); ) {
      String key = (String) e.nextElement();
      StringBuffer sb = new StringBuffer(key);

      sb.append("=");
      sb.append((String) env.get(key));

      cgi_env[i++] = sb.toString();
    } 

    return cgi_env;
  } 

  /**
   * Helper method to put an environment field into the hashtable.
   */
  private void put(Hashtable env, String key, String value) {
    if (key != null && value != null) {
      env.put(key, value);
    } 
  } 
}
