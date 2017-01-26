package containers;

import java.util.*;

public class CompType implements Comparable {
  int i;
  int j;
  public CompType(int n1, int n2) {
    i = n1;
    j = n2;
  }
  public String toString() {
    return "[i = " + i + ", j = " + j + "]";
  }
  public int compareTo(Object rv) {
    int rvi = ((CompType)rv).i;
    return (i < rvi ? -1 : (i == rvi ? 0 : 1));
  }
  
  
  public static void main(String[] args) {
    CompType[] a ={new CompType(2,3),new CompType(4,3),new CompType(3,3),new CompType(1,3)};
    
    System.out.println(
      "before sorting, a = " + Arrays.asList(a));
    Arrays.sort(a);
    System.out.println(
      "after sorting, a = " + Arrays.asList(a));
  }
} 