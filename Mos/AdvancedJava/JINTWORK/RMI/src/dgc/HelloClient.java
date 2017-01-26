package dgc;

import java.rmi.*;

public class HelloClient {

   public static void main(String args[]) {
      if (System.getSecurityManager() == null)
         System.setSecurityManager(new RMISecurityManager());
		
      try {
         HelloInterface obj = (HelloInterface) Naming.lookup("/HelloServer");
         
         for(int i = 0; i < 100; i++) {
            MsgInterface msg= obj.getMsg();
         }            
      } catch (Exception e) {
         System.out.println("HelloClient exception: " + e);
      }
   }
}

