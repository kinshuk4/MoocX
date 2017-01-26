package callbacks;

import java.applet.*;
import java.awt.*;
import java.io.Serializable;
import java.rmi.*;
import java.rmi.server.*;

public class RecieverMobile extends UnicastRemoteObject implements
                                               MobileInterface {
	public RecieverMobile()throws RemoteException{
		super();
	}
	public static void main(String [] args){
		try{
			DirectoryInterface obj = (DirectoryInterface)Naming.lookup("rmi://localhost:1099/Directory");
			ProviderInterface network=obj.returnProvider();
			network.register(args[0],new RecieverMobile());
			for (; ; )
			{
				System.out.println("Testing async call::" );
				Thread.sleep(1000);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void messageArrived(String msg) throws java.rmi.RemoteException{
		System.out.println(msg);
	
	}

}
