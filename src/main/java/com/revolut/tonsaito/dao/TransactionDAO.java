package com.revolut.tonsaito.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
	public static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS TRANSACTION "
			+ "(id INTEGER AUTO_INCREMENT PRIMARY KEY, " + " account_from VARCHAR(255), "
			+ " account_to VARCHAR(255), amount DOUBLE, date TIMESTAMP, status BOOLEAN, info VARCHAR(255) NULL)";
	private static final String SQL_SELECT_ALL_BY_DATE = "select * from TRANSACTION order by date DESC";
	private static final String SQL_INSERT = "INSERT INTO TRANSACTION(account_from, account_to, amount, date, status, info) values(?,?,?,?,?,?)";
	private static final String SQL_DELETE_BY_ID = "DELETE FROM TRANSACTION WHERE id=?";

	private TransactionDAO() {
	}

	public static void createEntity() {
		DBManager.executeUpdate(SQL_CREATE_TABLE);
		System.out.println("The Table Transaction was created successfully!.");
	}

	public static List<TransactionModel> getAll() {
		List<TransactionModel> list = new ArrayList<TransactionModel>();
		try (Connection conn = DBManager.getConn();
				Statement statement = conn.createStatement();
				PreparedStatement ps = conn.prepareStatement(SQL_SELECT_ALL_BY_DATE);) {
			conn.setAutoCommit(false);

			try (ResultSet rs = ps.executeQuery();) {
				while (rs.next()) {
					list.add(new TransactionModel(rs.getInt("id"), rs.getString("account_from"),
							rs.getString("account_to"), rs.getBigDecimal("amount"), rs.getTimestamp("date"),
							rs.getBoolean("status"), rs.getString("info")));
				}
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e.getCause());
			}

			conn.commit();
			conn.setAutoCommit(true);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e.getCause());
		}
		return list;
	}

	public static Integer insert(String accountFrom, String accountTo, BigDecimal amount, Timestamp timestamp,
			Boolean status, String info) {
		Integer id = 0;
		try (Connection conn = DBManager.getConn();
				Statement statement = conn.createStatement();
				PreparedStatement ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);) {
			conn.setAutoCommit(false);

			ps.setString(1, accountFrom);
			ps.setString(2, accountTo);
			ps.setBigDecimal(3, amount);
			ps.setTimestamp(4, timestamp);
			ps.setBoolean(5, status);
			ps.setString(6, info);

			ps.execute();
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

	public static boolean delete(Integer id) {
		int count = 0;
		try (Connection conn = DBManager.getConn();
				Statement statement = conn.createStatement();
				PreparedStatement ps = conn.prepareStatement(SQL_DELETE_BY_ID);) {
			conn.setAutoCommit(false);

			ps.setInt(1, id);
			count = ps.executeUpdate();

			conn.commit();
			conn.setAutoCommit(true);

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e.getCause());
		}
		return (count > 0);
	}

}
