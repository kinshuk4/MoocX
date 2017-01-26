package misc;
class manyThreads 
{
	public static void main(String[] args) 
	{
		
		MyThread1 c1, c2, c3;
		c1 = new MyThread1("ONE"); 
		c2 = new MyThread1("TWO");
		c3 = new MyThread1("THREE"); 
		
	}
}
class MyThread1 implements Runnable 
{
	public MyThread1(String name){
		this.name=name;	
		t=new Thread(this,name);
		t.start();
	}	

	public Thread t;
	public String name; 

	public void run(){
		for (int i=0;i<60 ;i++ )
		{
			System.out.println(name+" Runnable thread "+i);
		}
	}
}
