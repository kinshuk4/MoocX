package classesinterfaces;

 class car implements Steerable
{
	/* the get set methods for the attributes*/
	public void setSpeed(int speed){
		this.speed=speed;
	}
	public int getSpeed(){
		return speed;
	}
	public void setModel(String model){
		this.model=model;
	}
	public String getModel(){
		return model;
	}
	public void setGearType(GearType gear){
		this.gear=gear;
	}
	public GearType getGearType(){
		return gear;
	}
	public void printDetails(){
		System.out.println("This car has not been utilized yet: Detail of model="+model +" speed="+speed);
		
	}
	
	/* the attributes of a car*/
	public String model;
	public int speed;
	public GearType gear;
	public static int tax=0;

	/* the constructor for cars*/
	protected car (String model, int speed){
	this.model=model;
	this.speed=speed;
	}

	protected car (String model, int speed,GearType gear){
	this.model=model;
	this.speed=speed;
	this.gear=gear;
	}
	
	protected car (){
	
	}

	/* methods from the Interface*/
	public void turnLeft(int deg){
		if (deg < Steerable.maxTurn)
		{
			System.out.println("This car is MODEL="+model+" speed="+speed+" turning LEFT by "+deg +" degrees");
		}
		else System.out.println("This car is MODEL="+model+" speed="+speed+" cannot turn more than "+ Steerable.maxTurn+" degrees");
	}

	public void turnRight(int deg){
		if (deg < Steerable.maxTurn)
		{
			System.out.println("This car is MODEL="+model+" speed="+speed+" turning RIGHT by "+deg +" degrees");
		}
		else System.out.println("This car is MODEL="+model+" speed="+speed+" cannot turn more than "+ Steerable.maxTurn+" degrees");
	}

	/*To test static methods*/
	public static void increaseAnnualTax(int inctax){
		tax +=inctax;
	}

	public static int getAnnualTax(){
		return tax;
	}
	public static void setAnnualTax(int inctax){
		tax =inctax;
	}

	public String toString(){
		return "model :"+model+" "+" Speed :"+speed+" "+" GearType :"+gear;
	}
	//out parameters

	public void getOutParameters(String mdel ,int speedy){
		mdel=this.model;
		speedy=this.speed;
	}

	public void getOutParameters2(GearType ge){
		ge=this.gear;
		System.out.println(ge);
		//t.type=this.gear;

		
	}
}
