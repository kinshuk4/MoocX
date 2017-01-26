package misc;

class ThreadUncheckedExceptionHandler implements java.lang.Thread.UncaughtExceptionHandler 
{
	
	
	public void uncaughtException(Thread t,Throwable e){
		System.out.println("Got you::"+t);
		e.printStackTrace();
		System.out.println(t.isAlive());
		for (; ; )
		{
			try
			{
				System.out.println("Thread::"+t+" "+t.isAlive());
				Thread.sleep(1000);	
			}
			catch (InterruptedException ie)
			{
				ie.printStackTrace();
			}
		}
		
		
		
	}
}
	