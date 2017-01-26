package misc;

public class ReorderVolatileTest extends Thread {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		new ReorderVolatileTest().start();
		GarbleCheck gc=new GarbleCheck();
		for(;;){
			gc.m1();
		}
	}
	public void run(){
		GarbleCheck gc=new GarbleCheck();
		for(;;){
			gc.m2();
		}
	}

}
class GarbleCheck{
	static volatile int i=0,j=0;
	public void m1(){
		i++;j++;
	}
	public void m2(){
		System.out.println("i :: "+i+" j :: "+j);
	}
}