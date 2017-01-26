package com.open.manual;

import java.util.HashMap;
import java.util.Map;

import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.OpenType;
import javax.management.openmbean.SimpleType;

public class CompositeTypeMain {

	/*Converting Address to CompositeData
	 * public class Address{
	 * 	private String street;
	 * 	private String city;
	 * 	private String state;
	 * 	private int zip;
	 * ..........
	 * ..........
	 * }
	 * */
	public static void main(String[] args) throws OpenDataException {
		Map <String , Object> p=new HashMap<String , Object>();
        p.put("street","MG Road");
        p.put("city","Bangalore");
        p.put("state","KAR");
        p.put("zip",560062);
        String typeName =
            "java.util.Map<java.lang.String, java.lang.Object>";
        String[] keys =
            new String[] {"street", "city","state","zip"};
        String[] keydescription =
            new String[] {"street", "city","state","zip"};
        OpenType[] openTypesdescvalues =
            new OpenType[] {SimpleType.STRING, SimpleType.STRING,SimpleType.STRING,SimpleType.INTEGER};
        CompositeType rowType =
            new CompositeType(typeName, typeName, keys, keydescription, openTypesdescvalues);
        CompositeDataSupport cds=new CompositeDataSupport(rowType,p);
        System.out.println("rowType.keySet() :: "+rowType.keySet());
        System.out.println("rowType.containsKey() :: "+rowType.containsKey("key"));
        System.out.println("rowType.getType :: "+rowType.getType("value"));
        System.out.println("rowType.getTypeName() :: "+rowType.getTypeName());
        
        
        //Client can traverse a map without knowing the domain specific "java.util.Map" Type
        //or any other domain specific type.
        
        CompositeData cd=(CompositeData)cds;
        System.out.println("cd.get() :: "+cd.get("street"));
        System.out.println("cd.get() :: "+cd.get("city"));
        System.out.println("cd.getCompositeType() :: "+cd.getCompositeType());
        

	}

}
