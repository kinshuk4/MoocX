package misc;

class ThreadStopTest 
{
	public static void main(String[] args) 
	{
		//Instantiate and start the Thread 
		ThreadToStop t1=new ThreadToStop("ThreadToStop");

		try{
			Thread.sleep(1000);
		}catch(InterruptedException ie ){ie.printStackTrace();}
		//t1.stop();
		t1.interrupt();
	
	}
}
