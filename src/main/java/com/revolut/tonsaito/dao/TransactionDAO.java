package com.revolut.tonsaito.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revolut.tonsaito.config.DBManager;
import com.revolut.tonsaito.model.TransactionModel;

public class TransactionDAO {
	private static final Logger LOGGER = Logger.getLogger(TransactionDAO.class);
	public static final String tableSQL = "CREATE TABLE IF NOT EXISTS TRANSACTION "
			+ "(id INTEGER AUTO_INCREMENT PRIMARY KEY, " + " account_from VARCHAR(255), "
			+ " account_to VARCHAR(255), amount DOUBLE, date TIMESTAMP, status BOOLEAN, info VARCHAR(255) NULL)";

	private TransactionDAO() {
	}

	public static void createEntity() {
		DBManager.executeUpdate(tableSQL);
		System.out.println("The Table Transaction was created successfully!.");
	}

	public static boolean delete(Integer id) {
		return DBManager.executeUpdate("DELETE FROM TRANSACTION WHERE id='"+id+"'");
	}

	public static Integer insert(String accountFrom, String accountTo, BigDecimal amount, Timestamp timestamp,
			Boolean status, String info) {
		return DBManager.executeUpdateReturnKey("INSERT INTO TRANSACTION(account_from, account_to, amount, date, status, info) values('"
				+ accountFrom + "','" + accountTo + "', '" + amount + "','" + timestamp + "','" + status + "','" + info
				+ "')");
	}

	public static int count() {
		String sql = "select count(*) from TRANSACTION";
		int count = 0;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = DBManager.getConn().createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				count = rs.getInt(1);
			}
			rs.close();
		} catch (SQLException se) {
			LOGGER.error(se.getMessage(), se.getCause());
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e.getCause());
		} finally {
			try {
				if (rs != null) rs.close();
			} catch (SQLException se2) {
				LOGGER.error(se2.getMessage(), se2.getCause());
			}
			try {
				if (stmt != null) stmt.close();
			} catch (SQLException se2) {
				LOGGER.error(se2.getMessage(), se2.getCause());
			}
		}
		return count;
	}

	public static List<TransactionModel> getAll() {
		List<TransactionModel> list = new ArrayList<TransactionModel>();
		String sql = "select * from TRANSACTION";
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = DBManager.getConn().createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				list.add(new TransactionModel(rs.getInt("id"), rs.getString("account_from"), rs.getString("account_to"),
						rs.getBigDecimal("amount"), rs.getTimestamp("date"), rs.getBoolean("status"),
						rs.getString("info")));
			}
			rs.close();
		} catch (SQLException se) {
			LOGGER.error(se.getMessage(), se.getCause());
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e.getCause());
		} finally {
			try {
				if (rs != null) rs.close();
			} catch (SQLException se2) {
				LOGGER.error(se2.getMessage(), se2.getCause());
			}
			try {
				if (stmt != null) stmt.close();
			} catch (SQLException se2) {
				LOGGER.error(se2.getMessage(), se2.getCause());
			}
		}
		return list;
	}

}
