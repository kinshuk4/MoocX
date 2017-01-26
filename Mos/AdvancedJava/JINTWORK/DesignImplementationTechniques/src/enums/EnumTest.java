package enums;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

public class EnumTest {
	public static void main(String[] args)throws Exception { 
		//System.out.println(Suit.VALUES);
		//Class.forName("Suit.class");
		ObjectOutputStream os=new ObjectOutputStream(new FileOutputStream("Temp.txt"));
		os.writeObject(Operation.DIVIDE);
		ObjectInputStream ois=new ObjectInputStream(new FileInputStream("Temp.txt"));
		Operation clubs=(Operation)ois.readObject();
		System.out.println(clubs);
		if (clubs==Operation.DIVIDE){
			System.out.println("==");
		}
		else System.out.println("not ==");
		if (clubs.equals(Operation.DIVIDE)){
			System.out.println("equal()");
		}
		else System.out.println("not equal()");
	}


}

class EnumTest2 {
	public static void main(String[] args)throws Exception { 
		
		ObjectInputStream os=new ObjectInputStream(new FileInputStream("Temp.txt"));
		Suit clubs=(Suit)os.readObject();
		System.out.println(clubs);
		if (clubs==Suit.CLUBS){
			System.out.println("==");
		}
		else System.out.println("not ==");
		if (clubs.equals(Suit.CLUBS)){
			System.out.println("equal()");
		}
		else System.out.println("not equal()");
	}


}