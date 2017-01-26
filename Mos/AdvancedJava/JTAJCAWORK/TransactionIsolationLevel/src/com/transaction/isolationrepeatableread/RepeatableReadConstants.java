package com.transaction.isolationrepeatableread;

import java.sql.Connection;
import org.mk.training.util.DataSourceConfig;


public interface RepeatableReadConstants extends DataSourceConfig {
	String SELECTSTATEMENT = "SELECT * FROM ALBUMS";

	String SELECTFORUPDATESTATEMENT = "SELECT * FROM ALBUMS FOR UPDATE NOWAIT";
	
	String UPDATE = "UPDATE ALBUMS set ALBUMS.COST = ? where id = ?";
	
	String INSERTSTATEMENT = "INSERT INTO ALBUMS(ID, NAME , ARTIST , " +
	"ANNUM ,COST) VALUES (?,?,?,?,?)";
	
	int ISOLATION_LEVEL=Connection.TRANSACTION_SERIALIZABLE;
	
	boolean AUTOCOMMIT=false;
}
