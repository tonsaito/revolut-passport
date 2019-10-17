package com.revolut.tonsaito.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;

import com.revolut.tonsaito.model.ClientModel;

public class ClientDAOTest {
	private static final String ACCOUNT_TEST_NUMBER_0 = "00000000000001";
	private static final String ACCOUNT_TEST_NUMBER_1 = "11111111111111";
	private static final String TEST = "TEST";

	@Test
	public void shouldCreateEntityWithoutErrors() {
		ClientDAO.createEntity();
	}
	
	@Test
	public void shouldInsertWithoutErrors() {
		String account = generateAccount(1);
		ClientModel clientModel = ClientDAO.insert(TEST, account, BigDecimal.valueOf(0.0));
		ClientDAO.deleteByAccountNumber(account);
		assertEquals(account, clientModel.getAccount());
	}
	
	@Test
	public void shouldDeleteWithoutErrors() {
		String account = generateAccount(2);
		ClientDAO.insert(TEST, account, BigDecimal.valueOf(0.0));
		ClientDAO.deleteByAccountNumber(account);
		ClientModel clientModel = ClientDAO.getOne(new ClientModel.Builder().withAccount(account).build());
		assertNull(clientModel);
	}
	
	@Test
	public void shouldGenerateDataIfDatabaseIsEmptyWithoutErrors() {
		ClientDAO.generateData();
	}
	
	@Test
	public void shouldCountWithoutErrors() {
		String account = generateAccount(3);
		ClientDAO.insert(TEST, account, BigDecimal.valueOf(0.0));
		assertTrue(ClientDAO.count()>0);
		ClientDAO.deleteByAccountNumber(account);
	}
	
	@Test
	public void shouldGetOneWithoutErrors() {
		String account = generateAccount(4);
		ClientDAO.insert(TEST, account, BigDecimal.valueOf(0.0));
		assertTrue(ClientDAO.getOne(new ClientModel.Builder().withAccount(account).build()).getName().equals(TEST));
		ClientDAO.deleteByAccountNumber(account);
	}
	
	@Test
	public void shouldGetAllWithoutErrors() {
		String account = generateAccount(5);
		ClientDAO.insert(TEST, account, BigDecimal.valueOf(0.0));
		assertTrue(ClientDAO.getAll(new ClientModel.Builder().withAccount(account).build()).size() > 0);
		ClientDAO.deleteByAccountNumber(account);
	}
	
	private String generateAccount(Integer param) {
		return Long.toString(new Date().getTime())+param;
	}
	
	@Test
	public void shouldExchangeWithoutErrors() {
		ClientDAO.deleteByAccountNumber(ACCOUNT_TEST_NUMBER_0);
		ClientDAO.deleteByAccountNumber(ACCOUNT_TEST_NUMBER_1);
		ClientModel accountFrom = ClientDAO.insert(TEST, ACCOUNT_TEST_NUMBER_0, BigDecimal.valueOf(100.0));
		ClientModel accountTo = ClientDAO.insert(TEST, ACCOUNT_TEST_NUMBER_1, BigDecimal.valueOf(200.0));
		accountFrom.substractBalance(new BigDecimal(50.0));
		accountTo.addBalance(new BigDecimal(50.0));
		ClientDAO.updateClientsBalance(accountFrom, accountTo);
		assertEquals(BigDecimal.valueOf(50.0), ClientDAO.getOne(new ClientModel.Builder().withAccount(ACCOUNT_TEST_NUMBER_0).build()).getBalance());
		assertEquals(BigDecimal.valueOf(250.0), ClientDAO.getOne(new ClientModel.Builder().withAccount(ACCOUNT_TEST_NUMBER_1).build()).getBalance());
	}
	
}