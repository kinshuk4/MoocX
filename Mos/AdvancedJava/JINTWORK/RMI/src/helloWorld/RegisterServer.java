package helloWorld;



import java.rmi.*;

public class RegisterServer {

   public static void main(String args[]) {
      try {
         // Instantiate the object
         HelloServer obj = new HelloServer();
         System.out.println("Object instantiated"  +obj); 
         Naming.rebind ("/HelloServer", obj); 
         System.out.println("HelloServer bound in registry");
      } catch (Exception e) {
         System.out.println(e);
      }
   }
}
