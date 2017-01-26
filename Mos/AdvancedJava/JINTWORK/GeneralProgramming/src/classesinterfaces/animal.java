package classesinterfaces;

public abstract class animal implements Steerable
{
	public int maxTurn = 135;
	public abstract void turnLeft(int deg);
	public abstract void turnRight(int deg); 

	public void animalDetails(){
		System.out.println("I am an animal but which one i dont know yet");
	}
}
