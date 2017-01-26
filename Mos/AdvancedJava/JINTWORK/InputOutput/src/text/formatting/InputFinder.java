package text.formatting;

import java.text.*;
import java.io.*;
public class InputFinder {
  public static void main(String[] args) {
    Number input = null;
    try {
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      NumberFormat nf = NumberFormat.getInstance( );
      while (true) {
        System.out.println("Enter a number (-1 to quit): ");
        String s = br.readLine( );
        try {
          input = nf.parse(s);
        }
        catch (ParseException ex) {
          System.out.println(s + " is not a number I understand.");
          continue;
        }
        double d = input.doubleValue( );
        if (d < 0) break;
        double root = Math.sqrt(d);
        System.out.println("The square root of " + s + " is " + root);
      }
    }
    catch (IOException ex) {System.err.println(ex);}
  }
}


