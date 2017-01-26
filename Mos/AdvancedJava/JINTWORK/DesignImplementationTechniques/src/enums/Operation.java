package enums;
import java.io.ObjectStreamException;
import java.io.Serializable;

// Serializable, extensible typesafe enum
public abstract class Operation implements Serializable {
	private final transient String name;
	protected Operation(String name) { 
		this.name = name;
		System.out.println("NAME :: "+name+" ORDINALVALUE :: "+ordinal);
	}
//	 Perform arithmetic operation represented by this constant 
	protected abstract double eval(double x, double y);
	public static final Operation PLUS = new Operation("+") {
		protected double eval (double x, double y) { return x+y; } 
	};
	public static final Operation MINUS = new Operation("-") {
		protected double eval (double x, double y) { return x-y; } 
	};
	public static final Operation TIMES = new Operation("*") {
		protected double eval(double x, double y) { return x*y; } 
	};
	public static final Operation DIVIDE = new Operation("/") {
		protected double eval(double x, double y) { return x/y; } 
	};



	public String toString() { 	return name+ "  ORDINALVALUE :: "+ordinal; }
// Prevent subclasses from overriding Object.equals
	public final boolean equals(Object that) {
		return super.equals(that);
	}
	public final int hashCode() {
		return super.hashCode();
	}
	// The 4 declarations below are necessary for serialization 
	private static int nextOrdinal = 0; 
	private final int ordinal = nextOrdinal++; 
	private static final Operation[] VALUES ={ PLUS, MINUS, TIMES, DIVIDE };
	Object readResolve() throws ObjectStreamException {
		System.out.println("readResolve():: NAME :: "+name+" ORDINALVALUE :: "+ordinal);
		return VALUES[ordinal]; // Canonicalize
	}
}

//Subclass of extensible, serializable typesafe enum 
abstract class ExtendedOperation extends Operation { 
	ExtendedOperation(String name) { super(name); }

	public static Operation LOG = new ExtendedOperation("log") { 
		protected double eval(double x, double y) {
			return Math.log(y) / Math.log(x);
		}
	};
	
	public static Operation EXP = new ExtendedOperation("exp") { 
		protected double eval(double x, double y) {
			return Math.pow(x, y);
		}
	};

	// The 4 declarations below are necessary for serialization 
	private static int nextOrdinal = 0; 
	private final int ordinal = nextOrdinal++; 
	private static final Operation[] VALUES = { LOG, EXP }; 
	Object readResolve() throws ObjectStreamException {
		System.out.println("readResolve():: ORDINALVALUE :: "+ordinal);
		return VALUES[ordinal]; // Canonicalize
	}
}