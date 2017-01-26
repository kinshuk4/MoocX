package helloWorld;




import java.rmi.*;
import java.rmi.server.*;
import java.util.Date;

public class HelloServer extends UnicastRemoteObject implements HelloInterface{


	
	/*public HelloServer() throws RemoteException {
     UnicastRemoteObject.exportObject(this);  
   }*/

   public HelloServer() throws RemoteException{
       super();// Call the superclass constructor to export this object
   }

   public String sayHello() throws RemoteException{
       return "Hello World, the current system time is " + new Date();
    }
}
