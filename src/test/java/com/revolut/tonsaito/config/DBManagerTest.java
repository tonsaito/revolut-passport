package com.revolut.tonsaito.config;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

public class DBManagerTest {
	
	private static final String WRONG_SQL = "SHOW TABLES@@@";
	private static final String RIGHT_SQL = "SHOW TABLES";
	private static final String TABLE_SQL = "CREATE TABLE IF NOT EXISTS TEST1 (id INTEGER AUTO_INCREMENT PRIMARY KEY, value VARCHAR(1))";
	private static final String DROP_TABLE_SQL = "DROP TABLE IF EXISTS TEST1";

	@Before
	public void setup() {
		
	} 
	
	@Test
	public void getConnShouldReturnAConnection() throws ClassNotFoundException, SQLException{
		assertNotNull(DBManager.getConn());
	}
	
	@Test
	public void closeConnShouldCloseTheConnection() throws ClassNotFoundException, SQLException{
		DBManager.closeConn();
		assertNull(DBManager.conn);
	}
	
	@Test
	public void executeShouldReturnTrue() throws ClassNotFoundException, SQLException{
		assertTrue(DBManager.execute(RIGHT_SQL));
	}
	
	@Test
	public void executeShouldReturnFalse() throws ClassNotFoundException, SQLException{
		assertFalse(DBManager.execute(WRONG_SQL));
	}
	
	@Test
	public void executeUpdateShouldReturnTrue() throws ClassNotFoundException, SQLException{
		DBManager.executeUpdate(DROP_TABLE_SQL);
		DBManager.executeUpdate(TABLE_SQL);
		assertTrue(DBManager.executeUpdate("INSERT INTO TEST1(value) values(1) "));
	}
	
	@Test
	public void executeUpdateShouldReturnFalse() throws ClassNotFoundException, SQLException{
		assertFalse(DBManager.executeUpdate(WRONG_SQL));
	}
	
	@Test
	public void executeUpdateReturnKeyShouldReturnKey() throws ClassNotFoundException, SQLException{
		DBManager.executeUpdate(DROP_TABLE_SQL);
		DBManager.executeUpdate(TABLE_SQL);
		assertTrue(DBManager.executeUpdateReturnKey("INSERT INTO TEST1(value) values(1) ") > 0);
	}
}
