package callbacks;

import java.io.*;
import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

public class Airtel extends UnicastRemoteObject implements ProviderInterface 
{

	Map mobiles=new HashMap();
	public Airtel()throws RemoteException{
		super();
	}
	public void register(String number,MobileInterface ref) throws java.rmi.RemoteException{
		if (mobiles.containsKey(number))
		{
			//rejoin
			QueueModel mobileinfo=(QueueModel)mobiles.get(number);
			mobileinfo.setMobileReference(ref);
			if (mobileinfo.messageIsPending())
				mobileinfo.sendPendingMessages();
			
		}
		else {

			//first time
			QueueModel mobileinfo=new QueueModel(number,new ArrayList(),ref);
			mobiles.put(number,mobileinfo);
		}
		
	}
	public void sendMessage(String number,String message) throws java.rmi.RemoteException{
		if (mobiles.containsKey(number))
		{
			

			QueueModel mobileinfo=(QueueModel)mobiles.get(number);
			mobileinfo.addMessage(message);
			mobileinfo.sendPendingMessages();
		}
		
		
	}

	
}

