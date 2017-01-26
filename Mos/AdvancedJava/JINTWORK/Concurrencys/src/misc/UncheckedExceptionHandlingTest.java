package misc;

class UncheckedExceptionHandlingTest extends Thread 
{

	String [] args=null;
	UncheckedExceptionHandlingTest(String name,String [] args){
		super(name);
		this.args=args;
		this.setUncaughtExceptionHandler(new ThreadUncheckedExceptionHandler());
		this.start();
		
	}
	public static void main(String[] args) 
	{   try{
		UncheckedExceptionHandlingTest t1=new UncheckedExceptionHandlingTest("ThreadX",args);
		
		for (; ; )
		{	
			Thread.sleep(1000);
			System.out.println("Thread::"+Thread.currentThread().getName());
		}
		}catch(Exception e){
			System.out.println("Hello World!"+e);	
		}
		

	}



	public void run(){
		for (; ; )
		{	
			try{
				Thread.sleep(2000);
			}catch(InterruptedException ie){}
			System.out.println(args[0]);
			System.out.println("Stayin Alive");
		}
		
	
	}
}
