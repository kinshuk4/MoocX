package com.transaction.isolationreadcommited;

import java.sql.Connection;

import org.mk.training.util.DataSourceConfig;

public interface ReadCommitedConstants extends DataSourceConfig {
	String SELECTSTATEMENT = "SELECT * FROM ALBUMS";

	String UPDATE = "UPDATE ALBUMS set ALBUMS.COST = ? where id = ?";

	int ISOLATION_LEVEL=Connection.TRANSACTION_READ_COMMITTED;
	
	boolean AUTOCOMMIT=false;
	
	String INSERTSTATEMENT = "INSERT INTO ALBUMS(ID, NAME , ARTIST , " +
	"ANNUM ,COST) VALUES (?,?,?,?,?)";
	
}
