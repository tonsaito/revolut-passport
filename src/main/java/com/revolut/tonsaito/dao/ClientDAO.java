package com.revolut.tonsaito.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.BadRequestException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.revolut.tonsaito.config.DBManager;
import com.revolut.tonsaito.model.ClientModel;

public class ClientDAO {
	private static final Logger LOGGER = Logger.getLogger(ClientDAO.class);
	public static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS CLIENT "
			+ "(id INTEGER AUTO_INCREMENT PRIMARY KEY, " + " name VARCHAR(255), "
			+ " account_number VARCHAR(255), account_balance FLOAT)";
	private static final String SQL_COUNT = "select count(*) from CLIENT";
	private static final String SQL_SELECT_ALL = "select * from CLIENT WHERE 1=1 ";
	private static final String SQL_INSERT = "INSERT INTO CLIENT(name, account_number, account_balance) values(?,?,?)";
	private static final String SQL_UPDATE_ADD = "UPDATE CLIENT SET account_balance = account_balance + ? WHERE account_number = ?";
	private static final String SQL_UPDATE_SUBTRACT = "UPDATE CLIENT SET account_balance = account_balance - ? WHERE account_number = ?";
	private static final String SQL_DELETE_BY_ID = "DELETE FROM CLIENT WHERE account_number=?";

	private ClientDAO() {
	}

	public static void createEntity() {
		DBManager.executeUpdate(SQL_CREATE_TABLE);
		System.out.println("The Table Client was created successfully!");
	}

	public static ClientModel insert(String name, String accountNumber, BigDecimal balance) {
		Integer id = 0;
		if (getOne(new ClientModel.Builder().withAccount(accountNumber).build()) == null) {
			try (Connection conn = DBManager.getConn();
					Statement statement = conn.createStatement();
					PreparedStatement ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);) {
				conn.setAutoCommit(false);

				ps.setString(1, name);
				ps.setString(2, accountNumber);
				ps.setBigDecimal(3, balance);
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
			if (id > 0) {
				return getOne(new ClientModel.Builder().withId(id).build());
			}
		} else {
			throw new BadRequestException(String.format("Client with account %s already exists", accountNumber));
		}
		return null;
	}

	public static boolean deleteByAccountNumber(String accountNumber) {
		int count = 0;
		try (Connection conn = DBManager.getConn();
				Statement statement = conn.createStatement();
				PreparedStatement ps = conn.prepareStatement(SQL_DELETE_BY_ID);) {
			conn.setAutoCommit(false);

			ps.setString(1, accountNumber);
			count = ps.executeUpdate();

			conn.commit();
			conn.setAutoCommit(true);

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e.getCause());
		}
		return (count > 0);
	}

	public static void transfer(String accountFrom, String accountTo, BigDecimal balance) {
		try (Connection conn = DBManager.getConn();
				Statement statement = conn.createStatement();
				PreparedStatement psSubtract = conn.prepareStatement(SQL_UPDATE_SUBTRACT);
				PreparedStatement psAdd = conn.prepareStatement(SQL_UPDATE_ADD);) {
			conn.setAutoCommit(false);

			psSubtract.setBigDecimal(1, balance);
			psSubtract.setString(2, accountFrom);
			psSubtract.execute();

			psAdd.setBigDecimal(1, balance);
			psAdd.setString(2, accountTo);
			psAdd.execute();

			conn.commit();
			conn.setAutoCommit(true);

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e.getCause());
		}
	}

	public static void generateData() {
		if (ClientDAO.count() == 0) {
			insert("Tony Stark", "001", BigDecimal.valueOf(1000000.00));
			insert("Bruce Wayne", "002", BigDecimal.valueOf(2500000.00f));
			insert("Peter Parker", "003", BigDecimal.valueOf(300.00));
			insert("Thanos", "004", BigDecimal.valueOf(50.00));
			insert("Dr Strange", "005", BigDecimal.valueOf(80000.00));
			System.out.println("Clients data was generated successfully!");
		}
	}

	public static int count() {
		int count = 0;
		try (Connection conn = DBManager.getConn();
				Statement statement = conn.createStatement();
				PreparedStatement ps = conn.prepareStatement(SQL_COUNT);) {
			conn.setAutoCommit(false);

			try (ResultSet rs = ps.executeQuery();) {
				while (rs.next()) {
					count = rs.getInt(1);
				}
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e.getCause());
			}

			conn.commit();
			conn.setAutoCommit(true);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e.getCause());
		}
		return count;
	}

	public static ClientModel getOne(ClientModel clientModel) {
		List<ClientModel> clientList = getAll(clientModel, 1);
		ClientModel clientModelReturn = null;
		if (!clientList.isEmpty()) {
			clientModelReturn = clientList.get(0);
		}
		return clientModelReturn;
	}

	public static List<ClientModel> getAll(ClientModel clientModel) {
		return getAll(clientModel, 0);
	}

	public static List<ClientModel> getAll(ClientModel clientModel, final Integer limit) {
		List<ClientModel> list = new ArrayList<ClientModel>();
		StringBuilder sqlBuilder = new StringBuilder(SQL_SELECT_ALL);
		int count = 1;
		if (clientModel != null) {
			if (clientModel.getId() != null) {
				sqlBuilder.append("AND ID = ?");
			}
			if (StringUtils.isNotBlank(clientModel.getName())) {
				sqlBuilder.append("AND UPPER(NAME) = UPPER(?)");
			}
			if (StringUtils.isNotBlank(clientModel.getAccount())) {
				sqlBuilder.append("AND account_number = ?");
			}
		}
		if (limit != null && limit > 0) {
			sqlBuilder.append(" LIMIT ").append(limit);
		}

		try (Connection conn = DBManager.getConn();
				Statement statement = conn.createStatement();
				PreparedStatement ps = conn.prepareStatement(sqlBuilder.toString());) {
			conn.setAutoCommit(false);

			if (clientModel != null) {
				if (clientModel.getId() != null) {
					ps.setInt(count++, clientModel.getId());
				}
				if (StringUtils.isNotBlank(clientModel.getName())) {
					ps.setString(count++, clientModel.getName());
				}
				if (StringUtils.isNotBlank(clientModel.getAccount())) {
					ps.setString(count++, clientModel.getAccount());
				}
			}

			try (ResultSet rs = ps.executeQuery();) {
				while (rs.next()) {
					list.add(new ClientModel(rs.getInt("id"), rs.getString("name"), rs.getString("account_number"),
							rs.getBigDecimal("account_balance")));
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
}
