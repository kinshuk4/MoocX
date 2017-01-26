package misc;

import java.io.*;
public class ThreadTestMain 
{
	public static void main(String[] args) 
	{
		BufferedReader in = new BufferedReader (new InputStreamReader(System.in));
		String s;
		ThreadTest t=new ThreadTest();
		t.start();
		//while(true){
			try {
				while((s = in.readLine())!= null){
					System.out.println(s);
					t.setMessage(s);
					
				}
			}catch(IOException e) {e.printStackTrace();}
		//}

	}
}
