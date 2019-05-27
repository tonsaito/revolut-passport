package com.revolut.tonsaito.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

public class DBManager {
	private static final Logger LOGGER = Logger.getLogger(DBManager.class);
	static final String JDBC_DRIVER = "org.h2.Driver";
	static final String DB_URL = "jdbc:h2:./data/sample";
	static final String USER = "sa";
	static final String PASS = "revolut";
	static Connection conn = null;

	private DBManager() {
	}

	public static Connection getConn() throws ClassNotFoundException, SQLException {
		if (conn == null) {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
		}
		return conn;
	}

	public static void closeConn() throws SQLException {
		if (conn != null) {
			conn.close();
			conn = null;
		}
	}
	
	public static boolean execute(String sql) {
		Statement stmt = null;
		boolean executeReturn = false;
		try {
			stmt = getConn().createStatement();
			executeReturn = stmt.execute(sql);
			stmt.close();
			closeConn();
		} catch (SQLException se) {
			LOGGER.error(se.getMessage(), se.getCause());
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e.getCause());
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
				LOGGER.error(se2.getMessage(), se2.getCause());
			}
		}
		return executeReturn;
	}

	public static boolean executeUpdate(String sql) {
		Statement stmt = null;
		boolean executeReturn = false;
		try {
			stmt = getConn().createStatement();
			if(stmt.executeUpdate(sql) > 0) {
				executeReturn = true;
			}
			stmt.close();
			closeConn();
		} catch (SQLException se) {
			LOGGER.error(se.getMessage(), se.getCause());
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e.getCause());
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
				LOGGER.error(se2.getMessage(), se2.getCause());
			}
		}
		return executeReturn;
	}

}
