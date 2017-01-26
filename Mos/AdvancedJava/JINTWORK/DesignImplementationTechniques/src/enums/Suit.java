package enums;
import java.io.ObjectStreamException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Suit implements java.io.Serializable,Comparable{
	//	 Ordinal of next suit to be created 
	private static int nextOrdinal = 0;

	// Assign an ordinal to this suit
	private final int ordinal = nextOrdinal++;

	private final String name;
	private Suit(String name) { 
		System.out.println("Suit() "+name+" :: ORDINALVALUE :: "+ordinal);
		this.name = name; 
		
	} 
	public String toString() 
	{ 
		return name+ "  ORDINALVALUE :: "+ordinal; 
	}
	public static final Suit CLUBS	= new Suit("clubs");
	public static final Suit DIAMONDS = new Suit("diamonds");
	//public static final Suit CLUBS	= new Suit("clubs");
	public static final Suit HEARTS	= new Suit("hearts");
	public static final Suit SPADES	= new Suit("spades");
	private Object readResolve() throws ObjectStreamException {
		System.out.println("readResolve() :: ORDINALVALUE :: "+ordinal);
		 return PRIVATE_VALUES[ordinal]; // Canonicalize 

	}
	public int compareTo(Object o) {
		return ordinal - ((Suit)o).ordinal;
	}

	private static final Suit[] PRIVATE_VALUES ={ CLUBS, DIAMONDS, HEARTS, SPADES };
	public static final List VALUES =Collections.unmodifiableList(Arrays.asList(PRIVATE_VALUES));

}
