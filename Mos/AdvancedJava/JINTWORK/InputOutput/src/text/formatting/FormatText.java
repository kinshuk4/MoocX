package text.formatting;

import java.text.*;
import java.util.Locale;
public class FormatText {
  public static void main(String[] args) {
    NumberFormat nf = NumberFormat.getInstance( );
    for (double x = Math.PI; x < 100000; x *= 10) {
      String formattedNumber = nf.format(x);
      System.out.println(formattedNumber + "\t\t\t" + x);
    }
    NumberFormat french = NumberFormat.getInstance(Locale.FRENCH);
    for (double x = Math.PI; x < 100000; x *= 10) {
      String formattedNumber = nf.format(x);
      System.out.println(formattedNumber + "\t\t\t" + x);
    }
  }
}



