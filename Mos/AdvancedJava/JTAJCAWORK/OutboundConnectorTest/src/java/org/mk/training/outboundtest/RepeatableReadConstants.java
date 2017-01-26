package org.mk.training.outboundtest;

import java.sql.Connection;

public interface RepeatableReadConstants {
	String SELECTSTATEMENT = "SELECT * FROM ALBUMS";

	String SELECTFORUPDATESTATEMENT = "SELECT * FROM ALBUMS FOR UPDATE NOWAIT";
	
	String UPDATE = "UPDATE ALBUMS set ALBUMS.COST = ? where id = ?";
	
	String INSERTSTATEMENT = "INSERT INTO ALBUMS(ID, NAME , ARTIST , " +
	"ANNUM ,COST) VALUES (?,?,?,?,?)";
	
	int ISOLATION_LEVEL=Connection.TRANSACTION_SERIALIZABLE;
	
	boolean AUTOCOMMIT=false;
}
