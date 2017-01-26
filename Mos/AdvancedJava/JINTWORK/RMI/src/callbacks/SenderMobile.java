package callbacks;

import java.io.Serializable;
import java.rmi.*;
import java.rmi.server.*;

public class SenderMobile{

	public static void main(String [] args){
		try{
			DirectoryInterface obj = (DirectoryInterface)Naming.lookup("rmi://localhost:1099/Directory");
			ProviderInterface network=obj.returnProvider();
			network.sendMessage(args[0],"Hi");
	

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}