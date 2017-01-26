package org.mk.training.outboundtest;

import java.sql.Connection;

public interface ReadCommitedConstants{
	String SELECTSTATEMENT = "SELECT * FROM ALBUMS";

	String UPDATE = "UPDATE ALBUMS set ALBUMS.COST = ? where id = ?";

	int ISOLATION_LEVEL=Connection.TRANSACTION_READ_COMMITTED;
	
	boolean AUTOCOMMIT=false;
}
