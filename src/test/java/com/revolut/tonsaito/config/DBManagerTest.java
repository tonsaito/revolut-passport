package com.revolut.tonsaito.config;


import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

public class DBManagerTest {
	
	private static final String WRONG_SQL = "SHOW TABLES@@@";
	private static final String RIGHT_SQL = "SHOW TABLES";

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
		assertTrue(DBManager.execute(RIGHT_SQL));
	}
	
	@Test
	public void executeUpdateShouldReturnFalse() throws ClassNotFoundException, SQLException{
		assertFalse(DBManager.execute(WRONG_SQL));
	}
}
