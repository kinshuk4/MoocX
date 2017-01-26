package clone;

import java.util.Calendar;
import java.util.Date;

public class CloneTest {


    /**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Date birth=new MutableDate();
		birth.setYear(77);
		Date join=new MutableDate();
		join.setYear(99);
		String name="Mohit";
		MutableAddress a=new MutableAddress("RBIMainROAD","Bangalore","KAR","560072");
		//Address a=Address.valueOf("RBIMainROAD","Bangalore","KAR","560072");
		Employee p=new Employee(name,birth ,join,a);
		System.out.println(p);
		
		join.setYear(77);
		birth.setYear(99);
		a.setState("BIHAR");
		System.out.println(p);
		
	}

}
