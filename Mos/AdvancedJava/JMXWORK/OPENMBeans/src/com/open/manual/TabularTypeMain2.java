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

public class TabularTypeMain2 {
	/*Converting a Map of String, Address to TabularData
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
		//addmap1.put("key","PEERTOPEER");
		Map <String , Object> addmap2=new HashMap<String , Object>();
		addmap2.put("street","JP Nagar");
		addmap2.put("city","Bangalore");
		addmap2.put("state","KAR");
		addmap2.put("zip",560065);
		//addmap2.put("key","GPS");

		String typeNameforadd =
            "java.util.Map<java.lang.String, java.lang.Object>";
        String[] keysforadd =
            new String[] {"street", "city","state","zip"};
        String[] keydescriptionforadd =
            new String[] {"street", "city","state","zip"};
        OpenType[] openTypesdescvaluesforadd =
            new OpenType[] {SimpleType.STRING, SimpleType.STRING,SimpleType.STRING,SimpleType.INTEGER};
        CompositeType rowTypeforadd =
            new CompositeType(typeNameforadd, typeNameforadd, keysforadd, keydescriptionforadd, openTypesdescvaluesforadd);

        String typeName =
            "java.util.Map<java.lang.String, java.lang.Object>";
        String[] keys =
            new String[] {"key", "value"};
        String[] keydescription =
            new String[] {"key", "value"};
        OpenType[] openTypesdescvalues =
            new OpenType[] {SimpleType.STRING, rowTypeforadd};
        CompositeType rowType =
            new CompositeType(typeName, typeName, keys, keydescription, openTypesdescvalues);
        TabularType tabularType =
            new TabularType(typeName, typeName, rowType, new String[]{"key","value"});
        
        TabularDataSupport tds=new TabularDataSupport(tabularType);
        CompositeDataSupport cdsforadd1=new CompositeDataSupport(rowTypeforadd,addmap1);
        CompositeDataSupport cdsforadd2=new CompositeDataSupport(rowTypeforadd,addmap2);
        Map <String , Object> mapformap1=new HashMap<String , Object>();
        Map <String , Object> mapformap2=new HashMap<String , Object>();
        mapformap1.put("key", "GPS");
        mapformap1.put("value", cdsforadd1);
        
        mapformap2.put("key", "PEERTOPEER");
        mapformap2.put("value", cdsforadd1);
        System.out.println("rowType.keySet() :: "+rowType.keySet());
        CompositeDataSupport cdsforMAP1=new CompositeDataSupport(rowType,mapformap1);
        CompositeDataSupport cdsforMAP2=new CompositeDataSupport(rowType,mapformap2);
        tds.put(cdsforMAP1);
        tds.put(cdsforMAP2);
        
        TabularData td=(TabularData)tds;
        MBeanPrinter.printDetails(td);
        
        
	}
	
}
