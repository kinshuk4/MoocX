package misc;

//SharedVariable.java
import java.util.concurrent.*;
public class SharedVariable  {
  private int shared = 0;

	

	public static void main(String[] args) throws Exception{
		int NUM_THREADS=5000;
		

		Data d=new Data();
		for (int i=0;i<NUM_THREADS ;i++ )
		{
			MyTryFineGarbleThread t = new MyTryFineGarbleThread(d,1);
			t.start();
			
		}
		
		System.out.println("FINISHED main: shared = " + d.modify(-5000));
		Thread.sleep(5000);
		System.out.println("Shared :: "+d.getShared());
		
  } 
  
  
}
class MyTryFineGarbleThread extends Thread
{	
	private Data d=null;
	private int num=0;
	MyTryFineGarbleThread(Data d,int num){
		this.d=d;
		this.num=num;
	}

	public void run(){
		System.out.println("FINISHED thread: shared = " + d.modify(num));
	
	}
	
};
class Data
{
	private int shared=0;
	private int NUM_THREADS=5000;
	private CountDownLatch c=new CountDownLatch(NUM_THREADS); 
  
	public int modify(int amount) {
		c.countDown();
		try
		{
			c.await();
		}
		catch (InterruptedException ie)
		{
		}
		

		for (int i = 0; i < 1000; i++) {

			System.out.printf(" Before compute Thread no:: %s Shared :: %d \n",Thread.currentThread().getName() , shared);
			//shared = shared + amount;
			System.out.printf(" After compute Thread no:: %s Shared :: %d\n ",Thread.currentThread().getName() , shared+=amount);
      
		} 
		return shared;
	} 
	public int getShared(){
		return shared;
	}
};
