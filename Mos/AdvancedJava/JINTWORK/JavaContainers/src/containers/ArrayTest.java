package containers;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ArrayTest {
	public static void main(String [] args) {
		List<String> animals=new ArrayList<String>();
		animals.add("camel");
		animals.add("penguin");
		
		String nanu="nanu";
		//String xarray[]={3};
		String xarray[]=(String[])Array.newInstance(String.class, 3);
		
	}
}
