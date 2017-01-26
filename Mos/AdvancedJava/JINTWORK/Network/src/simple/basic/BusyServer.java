package simple.basic;

import java.io.*;
import java.net.*;
import java.util.Calendar;

class BusyServer {

    public static void main(String[] args) {
        Socket aConn = null;
        ServerSocket serv = null;
        try {
            serv = new ServerSocket(8005);
            while (true) {
                aConn = serv.accept();
                PrintWriter out = new PrintWriter(aConn.getOutputStream(), true);
                String curTime = Calendar.getInstance().getTime().toString();
                System.out.println("A client connected at " + curTime);
                for (int i = 0; i < 20; i++) {
                    curTime = Calendar.getInstance().getTime().toString();
                    out.println("Message from Server at " + curTime);
                    Thread.sleep(1000);
                }
                aConn.close();
            }
        } //of try
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
