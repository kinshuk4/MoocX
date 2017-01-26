package simple.basic;

import java.io.PrintWriter;
import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.Calendar;
import java.io.BufferedOutputStream;

class SockServer {

	public static void main(String[] args) {
		ServerSocket serv = null;
		Socket aConn = null;   	
		try {
			serv = new ServerSocket(8005);
			while(true)   {
				aConn = serv.accept();

				/** Writing String **/
				PrintWriter out = new PrintWriter( aConn.getOutputStream(), true);
				String curTime = Calendar.getInstance().getTime().toString();
				out.println("Connected to Server at " + curTime);
				System.out.println("A client connected at " + curTime);
				 
				/** Writing Bytes 
				byte [] b=new byte[1000];
				for (int i=0;i<1000 ;i++ )b[i]=(byte)i;
				BufferedOutputStream bos=new BufferedOutputStream(aConn.getOutputStream());
				for (int i=0;i<b.length ;i++ )bos.write(b[i]);**/

				aConn.close();
			}

        } //of try
		catch (IOException e)  { 
			e.printStackTrace();
        }
	}
}