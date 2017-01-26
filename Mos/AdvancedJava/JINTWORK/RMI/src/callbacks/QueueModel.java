package callbacks;

import java.util.*;
import java.rmi.*;

public class QueueModel
{
	QueueModel(String number,List messageQueue,MobileInterface ref){
		this.mobilenumber=number;
		this.messageQueue=messageQueue;
		this.ref=ref;
	}
	private String mobilenumber;
	private List messageQueue;
	private MobileInterface ref;
	private boolean pending=false;

	public void setMobileNumber(String number){
		this.mobilenumber=number;
	}
	public void setMessageQueue(List messageQueue){
		this.messageQueue=messageQueue;
	}
	public void setMobileReference(MobileInterface ref){
		this.ref=ref;
	}

	public String getMobileNumber(){
		return mobilenumber;
	}
	public List getMessageQueue(){
		return messageQueue;
	}
	public MobileInterface getMobileReference(){
		return ref;
	}


	synchronized public void addMessage(String message){
		messageQueue.add(message);
		pending=true;
	}

	synchronized public void sendPendingMessages(){
		List messages=this.getMessageQueue();
		Iterator i=messages.iterator();
		try
		{
			System.out.println("QueueModel before :: "+ this);
			while (i.hasNext())
			{
			
				this.getMobileReference().messageArrived((String)i.next());
				i.remove();
			}
			pending=false;
			System.out.println("QueueModel after :: "+ this);

		}catch (RemoteException e)
		{
				//bloody bugger buzzed off;
		}
	}
	
	public boolean messageIsPending(){
		return pending;
	}
	public String toString(){
		return mobilenumber+"  "+ref+"  "+messageQueue;
	}

};  
