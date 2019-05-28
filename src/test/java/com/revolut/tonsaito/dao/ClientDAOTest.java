package com.revolut.tonsaito.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

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
		assertTrue(ClientDAO.insert(TEST, ACCOUNT_TEST_NUMBER_0, BigDecimal.valueOf(0.0)));
		ClientDAO.deleteByAccountNumber(ACCOUNT_TEST_NUMBER_0);
	}
	
	@Test
	public void shouldDeleteWithoutErrors() {
		ClientDAO.insert(TEST, ACCOUNT_TEST_NUMBER_0, BigDecimal.valueOf(0.0));
		ClientDAO.deleteByAccountNumber(ACCOUNT_TEST_NUMBER_0);
	}
	
	@Test
	public void shouldExchangeWithoutErrors() {
		ClientDAO.deleteByAccountNumber(ACCOUNT_TEST_NUMBER_0);
		ClientDAO.deleteByAccountNumber(ACCOUNT_TEST_NUMBER_1);
		ClientDAO.insert(TEST, ACCOUNT_TEST_NUMBER_0, BigDecimal.valueOf(100.0));
		ClientDAO.insert(TEST, ACCOUNT_TEST_NUMBER_1, BigDecimal.valueOf(200.0));
		ClientDAO.exchange(ACCOUNT_TEST_NUMBER_0, ACCOUNT_TEST_NUMBER_1, BigDecimal.valueOf(50.0));
		assertEquals(BigDecimal.valueOf(50.0), ClientDAO.getOne(new ClientModel.Builder().withAccount(ACCOUNT_TEST_NUMBER_0).build()).getBalance());
		assertEquals(BigDecimal.valueOf(250.0), ClientDAO.getOne(new ClientModel.Builder().withAccount(ACCOUNT_TEST_NUMBER_1).build()).getBalance());
	}
	
	@Test
	public void shouldGenerateDataIfDatabaseIsEmptyWithoutErrors() {
		ClientDAO.generateData();
	}
	
	@Test
	public void shouldCountWithoutErrors() {
		assertTrue(ClientDAO.count()>0);
	}
	
	@Test
	public void shouldGetOneWithoutErrors() {
		ClientDAO.insert(TEST, ACCOUNT_TEST_NUMBER_0, BigDecimal.valueOf(0.0));
		assertTrue(ClientDAO.getOne(new ClientModel.Builder().withAccount(ACCOUNT_TEST_NUMBER_0).build()).getName().equals(TEST));
		ClientDAO.deleteByAccountNumber(ACCOUNT_TEST_NUMBER_0);
	}
	
	@Test
	public void shouldGetAllWithoutErrors() {
		ClientDAO.insert(TEST, ACCOUNT_TEST_NUMBER_0, BigDecimal.valueOf(0.0));
		assertTrue(ClientDAO.getAll(new ClientModel.Builder().withAccount(ACCOUNT_TEST_NUMBER_0).build()).size() > 0);
		ClientDAO.deleteByAccountNumber(ACCOUNT_TEST_NUMBER_0);
	}
	
}