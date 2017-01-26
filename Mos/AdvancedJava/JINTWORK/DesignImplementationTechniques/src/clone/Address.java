package clone;

public class Address implements Cloneable{
	private String street=null;
	private String city=null;
	private String state=null;
	private String zip=null;
	
	public String getCity() {
		return city;
	}
	public String getState() {
		return state;
	}
	public String getStreet() {
		return street;
	}
	public String getZip() {
		return zip;
	}
	
	/*public final String getCity() {
		return city;
	}
	public final String getState() {
		return state;
	}
	public final String getStreet() {
		return street;
	}
	public final String getZip() {
		return zip;
	}*/
	/* */
	public Address(String street, String city, String state, String zip) {
		super();
		// TODO Auto-generated constructor stub
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}
	public static Address valueOf(String street, String city, String state, String zip){
		return new Address(street,city,state,zip);
		
	}
	public Object clone() {
		// TODO Auto-generated method stub
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			// will never happen because we have implmented Cloneable
			return null;
		}
		
	}
	
	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		if(this.hashCode()==arg0.hashCode())
			return true;
		else return false;
	}

	public int hashCode() {
		StringBuffer sb=new StringBuffer();
		sb.append(street);
		sb.append(city);
		sb.append(state);
		sb.append(zip);
		return sb.toString().hashCode(); 
		
	}
	
	public String toString() {
		// TODO Auto-generated method stub
		return "\nStreet :: "+street+"\n City :: "+city+"\n State :: "+state+"\n Zip :: "+zip;
	}
	
	
}
class MutableAddress extends Address{
	private String street=null;
	private String city=null;
	private String state=null;
	private String zip=null;
	public String getCity() {
		return city;
	}
	public String getState() {
		return state;
	}
	public String getStreet() {
		return street;
	}
	public String getZip() {
		return zip;
	}
	public MutableAddress(String street, String city, String state, String zip) {
		super(street, city, state, zip);
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
		// TODO Auto-generated constructor stub
	}
	public String toString() {
		// TODO Auto-generated method stub
		return "\nStreet :: "+street+"\n City :: "+city+"\n State :: "+state+"\n Zip :: "+zip;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setState(String state) {
		this.state = state;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	
}
