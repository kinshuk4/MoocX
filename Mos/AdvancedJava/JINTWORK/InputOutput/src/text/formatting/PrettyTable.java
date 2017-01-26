package text.formatting;

import java.text.NumberFormat;

public class PrettyTable {
	  public static void main(String[] args) {
	    System.out.println("Degrees Radians Grads");
	    NumberFormat myFormat = NumberFormat.getInstance( );
	    myFormat.setMinimumIntegerDigits(3);
	    myFormat.setMaximumFractionDigits(2);
	    myFormat.setMinimumFractionDigits(2);
	    for (double degrees = 0.0; degrees < 360.0; degrees++) {
	      String radianString = myFormat.format(Math.PI * degrees / 180.0);
	      String gradString = myFormat.format(400 * degrees / 360);
	      String degreeString = myFormat.format(degrees);
	      System.out.println(degreeString + "  " + radianString
	       + "  " + gradString);
	    }
	  }
	}



