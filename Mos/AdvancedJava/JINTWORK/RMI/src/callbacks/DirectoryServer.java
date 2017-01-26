package callbacks;

import java.io.*;
import java.rmi.*;
import java.rmi.server.*;
import java.util.Date;

public class DirectoryServer extends UnicastRemoteObject implements DirectoryInterface 
{

	ProviderInterface airtel=new Airtel();
	public DirectoryServer()throws RemoteException{
		super();
	}
	public ProviderInterface returnProvider() throws java.rmi.RemoteException{
		return airtel;
   
	}

	public static void main(String args[]) {
      try {
         // Instantiate the object
         DirectoryServer obj = new DirectoryServer();
         
         Naming.rebind ("Directory", obj); 
         System.out.println("Directory bound in registry");
      } catch (Exception e) {
         System.out.println(e);
      }
   }

}

