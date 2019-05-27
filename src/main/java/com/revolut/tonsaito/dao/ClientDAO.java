package com.revolut.tonsaito.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.revolut.tonsaito.config.DBManager;
import com.revolut.tonsaito.model.ClientModel;

public class ClientDAO {
	public static final String tableSQL = "CREATE TABLE IF NOT EXISTS CLIENT " 
			+ "(id INTEGER AUTO_INCREMENT PRIMARY KEY, " + " name VARCHAR(255), "
			+ " account_number VARCHAR(255), account_balance FLOAT)";
	
	private ClientDAO() {}

	public static void createEntity() {
		DBManager.executeUpdate(tableSQL);
		System.out.println("The Table Client was created successfully!");
	}
	public static void insert(String name, String accountNumber, BigDecimal balance) {
		DBManager.executeUpdate("INSERT INTO CLIENT(name, account_number, account_balance) values('" + name + "','"
				+ accountNumber + "', '" + balance + "')");
	}
	
	public static void exchange(String accountFrom, String accountTo, BigDecimal balance) {
		DBManager.executeUpdate("UPDATE CLIENT SET account_balance = account_balance - "+balance+" WHERE account_number = '"+accountFrom+"'");
		DBManager.executeUpdate("UPDATE CLIENT SET account_balance = account_balance + "+balance+" WHERE account_number = '"+accountTo+"'");
	}

	public static void generateData() {
		if(ClientDAO.count() == 0) {
			insert("Tony Stark", "001", new BigDecimal(1000000.00));
			insert("Bruce Wayne", "002", new BigDecimal(2500000.00f));
			insert("Peter Parker", "003", new BigDecimal(300.00));
			insert("Thanos", "004", new BigDecimal(50.00));
			insert("Dr Strange", "005", new BigDecimal(80000.00));
			System.out.println("Clients data was generated successfully!");
		}
	}
	
	public static int count(){
		String sql = "select count(*) from CLIENT";
		int count = 0;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = DBManager.getConn().createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()){
                count = rs.getInt(1);
            }
			rs.close();
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			}
		}
		return count;
	}
	
	public static ClientModel getOne(ClientModel clientModel){
		ClientModel clientModelReturn = new ClientModel();
		Statement stmt = null;
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder("select * from CLIENT WHERE 1=1");
		if(clientModel != null) {
			if(StringUtils.isNotBlank(clientModel.getName())) {
				sql.append(" AND UPPER(NAME) = UPPER('").append(clientModel.getName()).append("')");
			}
			if(StringUtils.isNotBlank(clientModel.getAccount())) {
				sql.append(" AND account_number = '").append(clientModel.getAccount()).append("'");
			}
		}
		sql.append(" LIMIT 1");
		try {
			stmt = DBManager.getConn().createStatement();
			rs = stmt.executeQuery(sql.toString());
			while (rs.next()) {
				clientModelReturn = new ClientModel(rs.getInt("id"), rs.getString("name"), rs.getString("account_number"), rs.getBigDecimal("account_balance"));
			}
			rs.close();
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			}
		}
		return clientModelReturn;
	}

	public static List<ClientModel> getAll(ClientModel clientModel){
		List<ClientModel> list = new ArrayList<ClientModel>();
		Statement stmt = null;
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder("select * from CLIENT WHERE 1=1");
		if(clientModel != null) {
			if(StringUtils.isNotBlank(clientModel.getName())) {
				sql.append(" AND UPPER(NAME) = UPPER('").append(clientModel.getName()).append("')");
			}
			if(StringUtils.isNotBlank(clientModel.getAccount())) {
				sql.append(" AND account_number = '").append(clientModel.getAccount()).append("'");
			}
		}
		try {
			stmt = DBManager.getConn().createStatement();
			rs = stmt.executeQuery(sql.toString());
			while (rs.next()) {
				list.add(new ClientModel(rs.getInt("id"), rs.getString("name"), rs.getString("account_number"), rs.getBigDecimal("account_balance")));
			}
			rs.close();
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			}
		}
		return list;
	}

}
