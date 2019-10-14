package com.revolut.tonsaito.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

public class DBManager {
	private static BasicDataSource ds = new BasicDataSource();
	private static final Logger LOGGER = Logger.getLogger(DBManager.class);
	static final String JDBC_DRIVER = "org.h2.Driver";
	static final String DB_URL = "jdbc:h2:./data/revolut";
	static final String USER = "sa";
	static final String PASS = "revolut";

	static {
        ds.setUrl(DB_URL);
        ds.setUsername(USER);
        ds.setPassword(PASS);
        ds.setMinIdle(5);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(100);
    }
	
	private DBManager() {
	}

	public static Connection getConn() throws ClassNotFoundException, SQLException {
		return ds.getConnection();
	}

	public static void closeConn(Connection conn) throws SQLException {
		if (conn != null) {
			conn.close();
		}
	}

	public static boolean execute(final String sql) {
		boolean executeResult = false;
		try (Connection conn = DBManager.getConn();
				Statement statement = conn.createStatement();
				PreparedStatement ps = conn.prepareStatement(sql);) {
			conn.setAutoCommit(false);

			executeResult = ps.execute();

			conn.commit();
			conn.setAutoCommit(true);

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e.getCause());
		}
		return executeResult;
	}

	public static boolean executeUpdate(final String sql) {
		int count = 0;
		try (Connection conn = DBManager.getConn();
				Statement statement = conn.createStatement();
				PreparedStatement ps = conn.prepareStatement(sql);) {
			conn.setAutoCommit(false);

			count = ps.executeUpdate();

			conn.commit();
			conn.setAutoCommit(true);

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e.getCause());
		}
		return (count > 0);
	}

	public static Integer executeUpdateReturnId(String sql) {
		Integer id = 0;
		try (Connection conn = DBManager.getConn();
				Statement statement = conn.createStatement();
				PreparedStatement ps = conn.prepareStatement(sql);) {
			conn.setAutoCommit(false);

			ps.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
			try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					id = generatedKeys.getInt(1);
				} else {
					throw new SQLException("Creating entity failed, no ID obtained.");
				}
			}

			conn.commit();
			conn.setAutoCommit(true);

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e.getCause());
		}
		return id;
	}

	public static Integer executeUpdateReturnKeyOld(String sql) {
		Connection conn = null;
		Statement stmt = null;
		Integer executeReturn = 0;
		try {
			conn = getConn();
			stmt = conn.createStatement();
			stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
			try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					executeReturn = generatedKeys.getInt(1);
				} else {
					throw new SQLException("Creating entity failed, no ID obtained.");
				}
			}
			stmt.close();
			closeConn(conn);
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
