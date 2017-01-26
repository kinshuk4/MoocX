package simple.basic;

import java.io.*;
import java.net.*;
import java.util.Calendar;

public class MtServer implements Runnable  {
  Socket sockInst;

  public MtServer(Socket insock) {
         sockInst = insock;
    }

  public void run()  {
	try {
         PrintWriter out = new PrintWriter( sockInst.getOutputStream(), true);
         String threadName = Thread.currentThread().getName();
         String curTime = Calendar.getInstance().getTime().toString();

         System.out.println(threadName + ": A client connected at " + curTime);
         for (int i=0; i< 60; i++)  {
           curTime = Calendar.getInstance().getTime().toString();
           out.println(threadName + ": Message from Server at " + curTime);
           Thread.sleep(1000);
           }
          sockInst.close();
        } //of try
       catch (Exception e)  { 
          e.printStackTrace();
        }

  }
  public static void main(String[] args) {
      int threadCounter = 1;
      ServerSocket serv = null;
      Socket aConn = null;
       try {
  	     serv = new ServerSocket(8005);
            while(true)   {
             aConn = serv.accept();
             new Thread(new MtServer(aConn), "Thread #" + threadCounter++).start();
             }
            }
        catch(Exception e) {
              e.printStackTrace();
           }
      }
}