package org.mk.training.jms.mdb;

import java.sql.Connection;



public interface ReadSerializableConstants extends CommonConstants {
	String SELECTSTATEMENT = "SELECT * FROM ALBUMS";

	String UPDATE = "UPDATE ALBUMS set ALBUMS.COST = ? where id = ?";

	String INSERTSTATEMENT = "INSERT INTO ALBUMS(ID, NAME , ARTIST , " +
	"ANNUM ,COST) VALUES (?,?,?,?,?)";
	
	int ISOLATION_LEVEL=Connection.TRANSACTION_SERIALIZABLE;
	
	boolean AUTOCOMMIT=false;
}
