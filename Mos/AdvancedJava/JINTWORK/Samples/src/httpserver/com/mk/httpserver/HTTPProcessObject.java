// HTTPProcessObject.java

package httpserver.com.mk.httpserver;

import java.io.*;

/**
 * Encapsulates a CGI process.
 */
class HTTPProcessObject extends HTTPObject implements Runnable {
  private Process proc;

  /**
   * Constructs an HTTPProcess object, sets up the CGI execution environment
   * and starts the process.
   */
  public HTTPProcessObject(String fileName, HTTPInformation info, 
                           BufferedOutputStream bos, 
                           BufferedInputStream bis) throws HTTPException {
    super(info, bos, bis);

    // We need to determine whether or not the file exists
    try {
      File file = new File(fileName);

      // The file must exist and must be readable
      if (!(file.exists() && file.canRead())) {
        throw new FileNotFoundException();

        // Run the executable or script in its directory
        // This is recommended as the CGI script might create files
      } 
      File pathName = file.getParentFile();

      // Get the CGI execution environment variables
      String[] env = info.getCGIEnvironment();

      // Check if this is a script file which has a first line
      // indicating the absolute path to the interpreter such as
      // #!C:\Perl\bin\Perl.exe

      // Read the first two bytes of the file
      HTTPBufferedInputStream execbis = 
        new HTTPBufferedInputStream(new FileInputStream(fileName), 
                                    BUFFER_SIZE, HTTP_ENCODING);
      byte[] scriptToken = new byte[2];
      if (scriptToken.length 
              != execbis.read(scriptToken, 0, scriptToken.length)) {
        throw new Exception();

      } 
      if (scriptToken[0] == '#' && scriptToken[1] == '!') {

        // We assume this is a script file
        String[] args = new String[2];
        args[0] = execbis.readLine();
        execbis.close();
        args[1] = fileName;
        proc = Runtime.getRuntime().exec(args, env, pathName);
      } else {

        // We assume this is an executable
        execbis.close();
        proc = Runtime.getRuntime().exec(fileName, env, pathName);
      } 

      // Get the process STDIN and STDOUT	streams
      objectbis = new BufferedInputStream(proc.getInputStream(), 
                                          BUFFER_SIZE);
      objectbos = new BufferedOutputStream(proc.getOutputStream(), 
                                           BUFFER_SIZE);

      // The process will generate its own headers as we do not know
      // what type of content it sends back
      headersGenerated = false;
    } catch (FileNotFoundException fnfe) {
      throw new HTTPException(fnfe.getMessage(), HTTPStatus.NOT_FOUND);
    } catch (Exception e) {

      // Any exception during execution
      throw new HTTPException(e.getMessage(), HTTPStatus.INTERNAL_ERROR);
    } 
  }
}
