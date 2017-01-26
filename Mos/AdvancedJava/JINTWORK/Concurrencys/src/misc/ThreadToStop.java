package misc;

import java.util.*;

class ThreadToStop extends Thread 
{
	//logic specific flag;
	boolean moreworktodo=true;
	List<String> somelist=new LinkedList<String>();
	ThreadToStop(String name){
		super(name);
		this.start();
	}

	public void run(){
			//this.doMoreWorkIsInterrupted()
			//this.doMoreWorkInterrupted();
			//this.doMoreWorkSleep();
			this.doSomeWork();
	
	
	}

	public void doMoreWorkIsInterrupted(){
		// "isInterrupted()" checks whether the flag is true or not.
		while (!Thread.currentThread().isInterrupted()&& moreworktodo)
		{
			System.out.println("Some UseLess work");
			synchronized(somelist){
				// Stop method could leave the list in an inconsistent state;
				//exposed for some other thread to discover the damaged object
			
				// ***** however the interrupt method for finish the work and then 
				// go down.
				somelist.add(new String());
			}
		}
		System.out.println(Thread.currentThread().isInterrupted());
	
	}
	public void doMoreWorkInterrupted(){
		// "interrupted()" checks whether the flag is true or not.
		//if true clears it as well;
		while (!Thread.interrupted()&& moreworktodo)
		{
			System.out.println("Some UseLess work");
			synchronized(somelist){
				// Stop method could leave the list in an inconsistent state;
				//exposed for some other thread to discover the damaged object
			
				// ***** however the interrupt method for finish the work and then 
				// go down.
				somelist.add(new String());
			}
		}
		System.out.println(Thread.interrupted());
	
	}

	public void doMoreWorkSleep(){
		
		while (moreworktodo)
		{
			
			System.out.println("Some UseLess work");
			synchronized(somelist){
				// Stop method could leave the list in an inconsistent state;
				//exposed for some other thread to discover the damaged object
			
				// ***** however the interrupt method for finish the work and then 
				// go down.
				somelist.add(new String());
				try{
					Thread.sleep(10000);
				}catch(InterruptedException ie){
					// if interrupted from sleep or wait the flag is cleared quite like
					// "interrupted()" method
					ie.printStackTrace();
					// break cause some body wants the thread to end.
					break;

				}
			}
			
		}
		System.out.println(Thread.currentThread().isInterrupted());
		
	}
	public void doSomeWork(){
		/*
		 * if thread comes around to check this flag it closes down because
		 * the while loop is broken due to condition(Some one calls "interrupt()").
		 */
		while (!Thread.interrupted())
		{
			System.out.println("Some UseLess work");
			try{
				/*
				 * If the thread is sleeping then an InterruptedException is throw.
				 * The Exception Handler block interprets the InterruptedException
				 * as some one requesting a close down and breaks the loop. 
				 */
				Thread.sleep(10000);
			}catch(InterruptedException ie){
				ie.printStackTrace();
				/*
				 * The Exception handler block interprets the Exception Occuring 
				 * as a hint to close down and breaks the loop. 
				 */
				break;
			}
			
		}
	}

}
