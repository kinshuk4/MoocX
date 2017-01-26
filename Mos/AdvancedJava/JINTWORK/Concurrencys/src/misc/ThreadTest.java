package misc;

public class ThreadTest extends Thread
{
	String message=null;
	
	public synchronized void setMessage(String s){
		message=s;
		notify();
	}

	public synchronized void run(){
		executetask();
	}
	public synchronized void executetask(){ 
		for(;;){
			try{
				if (message==null)
				{
					wait();
				}
			}catch(InterruptedException e){e.printStackTrace();}
			System.out.println(message);
			message=null;
		}
	}
}

