package simple.basic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.BufferedInputStream;

class SockClient
{
	public static void main(String[] args)
	{ 
		Socket soc = null;
		String myLine;
		try {
			soc = new Socket("localhost",8005);

			/** Reading String**/
			BufferedReader myIn = new BufferedReader(new InputStreamReader( soc.getInputStream()));
			while ((myLine = myIn.readLine()) != null)
			System.out.println(myLine);
		
			/** Reading Bytes
			BufferedInputStream bis=new BufferedInputStream(soc.getInputStream());
			byte firstbyte=(byte)bis.read();
			byte [] b=new byte[bis.available()+1];
			b[0]=firstbyte;
			for(int i=1;i<b.length;i++)b[i]=(byte)(bis.read());
			for(int i=1;i<b.length;i++)System.out.println(b[i]); **/
			
			
        } //of try
		catch (Exception e) {
			e.printStackTrace();
        }
	}
}