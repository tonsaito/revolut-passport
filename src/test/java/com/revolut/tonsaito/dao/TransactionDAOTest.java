package com.revolut.tonsaito.dao;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.junit.Test;

public class TransactionDAOTest {
	private static final String ACCOUNT_TEST_NUMBER_0 = "00000000000001";
	private static final String ACCOUNT_TEST_NUMBER_1 = "11111111111111";
	
	@Test
	public void shouldCreateEntityWithoutErrors() {
		TransactionDAO.createEntity();
	}
	
	@Test
	public void shouldInsertWithoutErrors() {
		Integer transactionId = TransactionDAO.insert(ACCOUNT_TEST_NUMBER_0, ACCOUNT_TEST_NUMBER_1, BigDecimal.valueOf(100.0), new Timestamp(System.currentTimeMillis()), true, "");
		TransactionDAO.delete(transactionId);
		assertTrue(transactionId>0);
	}
	
	@Test
	public void shouldDeleteWithoutErrors() {
		Integer transactionId = TransactionDAO.insert(ACCOUNT_TEST_NUMBER_0, ACCOUNT_TEST_NUMBER_1, BigDecimal.valueOf(100.0), new Timestamp(System.currentTimeMillis()), true, "");
		assertTrue(TransactionDAO.delete(transactionId));
	}
	
	@Test
	public void shouldGetAllWithoutErrors() {
		assertTrue(TransactionDAO.getAll().size() >= 0);
	}

}
