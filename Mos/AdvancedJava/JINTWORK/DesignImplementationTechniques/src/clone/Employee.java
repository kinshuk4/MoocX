package clone;

import java.io.*;
import java.math.BigInteger;
import java.util.Date;

	public final class Employee{
		private  Date birthdate;
		private  Date joindate;
		private Address address;
		private String name;
		/*public Employee(String name,Date birthdate, Date joindate,Address address) {
		 	this.name=name;
			this.birthdate =(Date) birthdate.clone(); 
			this.joindate	= (Date) joindate.clone();
				if (this.birthdate.compareTo(this.joindate) > 0)throw new IllegalArgumentException(birthdate +" > "+ joindate);
			this.address=address;
		}*/
		
		public Employee(String name,Date birthdate, Date joindate,Address address) {
			this.name=name;
			if ((birthdate.getClass()!= Date.class)||(joindate.getClass()!= Date.class)){
				//cant trust clone for subclass.
				System.out.println("Not Trusted");
				this.birthdate = new Date(birthdate.getTime()); 
				this.joindate	= new Date(joindate.getTime());
			}else{
				this.birthdate =(Date) birthdate.clone(); 
				this.joindate	= (Date) joindate.clone();
				if (this.birthdate.compareTo(this.joindate) > 0)throw new IllegalArgumentException(birthdate +" > "+ joindate);
			}
			// if address is not under your control like BigInteger,BigDecimal.
			//They are immutable themselves but permit subclassing and overriding
			if (address.getClass()!= Address.class){
				//cant trust clone for subclass.
				System.out.println("Not Trusted");
				this.address = Address.valueOf(address.getStreet(),address.getCity(),address.getState(),address.getZip()); 
				
			}else{//you know that clone for address works fine.
				this.address =(Address) address.clone(); 
			}
			//if address is under your control then you can fix it and comment the above lines.
			//and uncomment the below line.
			//this.address=address;
		}
		public String getName() {
			return name;
		}
		public Date birthDate () {
			return (Date) birthdate.clone(); 
		}
		public Date joinDate ()	{ 
			return (Date) joindate.clone(); 
		}
		public Address address(){
			return address;
		}
		public String toString() { return "BirthDate :: "+birthdate +" - "		+" JoinDate :: "+ joindate+" "+address; }
		
	}
class MutableDate extends Date{
	
	
	public Object clone(){
		System.out.println("clone call");
		return this;
	}
}
