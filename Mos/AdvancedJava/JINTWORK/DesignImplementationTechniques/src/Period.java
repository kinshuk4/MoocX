


import java.io.*;
import java.util.Date;

public final class Period implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4647424730390249716L;
	private   Date start;
	private   Date end;
	/**
	*	@param start the beginning of the period.
	*	@param end the end of the period; must not precede start.
	*	@throws IllegalArgument if start is after end.
	*	@throws Nu1lPointerException if start or end is null.
	*/
	public Period(Date start, Date end) {
		this.start = new Date(start.getTime()); 
		this.end	= new Date(end.getTime());
		if (this.start.compareTo(this.end) > 0)throw new IllegalArgumentException(start +" > "+ end);
	}
	public Date start () {
		return (Date) start.clone(); 
	}
	public Date end ()	{ 
		return (Date) end.clone(); 
	}
	public String toString() { return start +" - "		+ end; }
	// Remainder omitted
	//	 Immutable class that uses defensive copying 
	
	/*private void readObject(ObjectInputStream s)
	throws IOException, ClassNotFoundException
	{
		System.out.println("readObject() :: ");
		s.defaultReadObject();
		// Check that our invariants are satisfied 
		if (start.compareTo(end) > 0)
		throw new InvalidObjectException(start +" after "+ end);
	}*/
	/*private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException
	{
		s.defaultReadObject();
		//	 Defensively copy our mutable components 
		//System.out.println("Start :: "+start +" End :: "+end );
		start = new Date(start.getTime());
		end	= new Date (end.getTime());
		//Check that our invariants are satisfied 
		if (start.compareTo(end) > 0)
			throw new InvalidObjectException(start +" after "+ end);
	}*/
//	 The defensive readResolve idiom
	/*private Object readResolve() throws ObjectStreamException {
		System.out.println("readResolve() :: ");
		return new Period(start, end);
	}*/
}



