package classesinterfaces;

class test 
{
	public boolean RIGHT=true;
	public boolean LEFT=false;
	public GearType type=new GearType("manual");
	public static void main(String[] args) 
	{
                System.out.println("Thread.currentThread().getName():"+Thread.currentThread().getName());
		//trying out parameters
		test t=new test();
		String model="junk";
		int speed=0;
		//GearType type=new GearType("Manual");
		car maruti=new car("Maruti",160,new GearType("Tiptronic"));
		maruti.getOutParameters(model,speed);
		System.out.println("Out parameters "+model+" "+speed);
		//System.out.println(maruti);
		maruti.getOutParameters2(t.type);
		System.out.println("Out parameters GT"+t.type);
		System.out.println(maruti);

		try{
		//car maruti=new car("Maruti",160);
		//car c=new car("BMW",200);

		taxi cititaxi=new taxi("Indica",165,1001);
		PC police=new PC("Gypsy",165,1001);
		System.out.println(cititaxi.getModel());
		System.out.println(cititaxi.speed);
		
		System.out.println(police.getModel());
		System.out.println(police.speed);
		cow cowcart=new cow();
		System.out.println("The class of " + cowcart +
                            " is " + cowcart.getClass());
		cowcart.getClass().newInstance();

		test t1=new test();
		t1.showDetails(cititaxi);
		t1.turnVehicle(cowcart,t1.LEFT,170);

		//static test
		//maruti.setAnnualTax(100);
		System.out.println("TAX= "+cititaxi.getAnnualTax());
		

		}catch(Exception e){e.printStackTrace();}
		
		
	}
	public void showDetails(car c)throws Exception{
		/*if (c instanceof taxi)
		{
			((taxi)c).printDetails();
		}*/
		c.printDetails();
		
	}

	public void turnVehicle(Steerable s,boolean b , int deg){
		System.out.println("The class of " + s +
                            " is " + s.getClass().getName()	);

		if (b==RIGHT)
		{
			s.turnRight(deg);
		}
		else s.turnLeft(deg);
		
		
	}
	
	

}
