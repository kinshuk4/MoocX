package classesinterfaces;

class taxi extends car
{	
	/* the attributes of a car*/
	private int cabnum;
	private boolean booked;

	/* the get set methods for the attributes*/
	public void setCabnum(int cabnum){
		this.cabnum=cabnum;
	}
	public int getCabnum(){
		return cabnum;
	}
	public void setBookedornot(boolean b){
		booked=b;
	}
	public boolean getBookedornot(){
		return booked;
	}
	public void printDetails(){
		
		System.out.println("This car has been made into a taxi with model= "+getModel()+" speed= "+getSpeed()+" cabnum= "+cabnum);
		
	}

	/* the constructor for taxi*/
	taxi(String model, int speed,int cabnum){
		//super( model,speed);
		this.cabnum=cabnum;
	}


}
