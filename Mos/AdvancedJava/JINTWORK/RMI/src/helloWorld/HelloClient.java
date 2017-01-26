package helloWorld;



import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.rmi.*;

public class HelloClient {

   public static void main(String args[]) {
      
      try {
         HelloInterface obj = (HelloInterface) Naming.lookup("rmi://localhost:1099/HelloServer");
         String message = obj.sayHello();
         System.out.println(message);
        
         
      } catch (Exception e) {
         System.out.println("HelloClient exception: " +e);
      }
   }
}
