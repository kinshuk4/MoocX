package com.transaction.isolationreadserializable;

import java.sql.Connection;

import org.mk.training.util.DataSourceConfig;

public interface ReadSerializableConstants extends DataSourceConfig {
	String SELECTSTATEMENT = "SELECT * FROM ALBUMS";

	String UPDATE = "UPDATE ALBUMS set ALBUMS.COST = ? where id = ?";

	String INSERTSTATEMENT = "INSERT INTO ALBUMS(ID, NAME , ARTIST , " +
	"ANNUM ,COST) VALUES (?,?,?,?,?)";
	
	int ISOLATION_LEVEL=Connection.TRANSACTION_SERIALIZABLE;
	
	boolean AUTOCOMMIT=false;
}
