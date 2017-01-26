package com.open.manual;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.OpenType;
import javax.management.openmbean.SimpleType;
import javax.management.openmbean.TabularData;
import javax.management.openmbean.TabularDataSupport;
import javax.management.openmbean.TabularType;

import com.util.MBeanPrinter;

public class TabularTypeMain1 {
	/*Converting a list Address to TabularData
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
		Map <String , Object> addmap1=new HashMap<String , Object>();
		addmap1.put("street","MG Road");
		addmap1.put("city","Bangalore");
		addmap1.put("state","KAR");
		addmap1.put("zip",560062);
		Map <String , Object> addmap2=new HashMap<String , Object>();
		addmap2.put("street","JP Nagar");
		addmap2.put("city","Bangalore");
		addmap2.put("state","KAR");
		addmap2.put("zip",560065);

        String typeName =
            "java.util.Map<java.lang.String, java.lang.String>";
        String[] keys =
            new String[] {"street", "city","state","zip"};
        String[] keydescription =
            new String[] {"street", "city","state","zip"};
        OpenType[] openTypesdescvalues =
            new OpenType[] {SimpleType.STRING, SimpleType.STRING,SimpleType.STRING,SimpleType.INTEGER};
        CompositeType rowType =
            new CompositeType(typeName, typeName, keys, keydescription, openTypesdescvalues);
        TabularType tabularType =
            new TabularType(typeName, typeName, rowType, new String[]{"street", "city","state","zip"});
        
        TabularDataSupport tds=new TabularDataSupport(tabularType);
        CompositeDataSupport cdsforadd1=new CompositeDataSupport(rowType,addmap1);
        CompositeDataSupport cdsforadd2=new CompositeDataSupport(rowType,addmap2);
        
        tds.put(cdsforadd1);
        tds.put(cdsforadd2);
        
        TabularData td=(TabularData)tds;
        MBeanPrinter.printDetails(td);
        
        
	}
	
}
