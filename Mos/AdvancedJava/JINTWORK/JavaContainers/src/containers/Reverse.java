package containers;


import java.util.*;

public class Reverse {
  public static void main(String[] args) {
    CompType[] a ={new CompType(2,3),new CompType(4,3),new CompType(3,3),new CompType(1,3)};    
	
    System.out.println(
      "before sorting, a = " + Arrays.asList(a));
    Arrays.sort(a, Collections.reverseOrder());
    System.out.println(
      "after sorting, a = " + Arrays.asList(a));
  }
} ///:~
