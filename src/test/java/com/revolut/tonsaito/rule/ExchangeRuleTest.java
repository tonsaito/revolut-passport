package com.revolut.tonsaito.rule;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.revolut.tonsaito.model.ClientModel;
import com.revolut.tonsaito.model.ResponseModel;

public class ExchangeRuleTest {

	@Before
	public void setup(){	}
	
	@Test
	public void testShouldFailWithInvalidAccountFrom() {
		ClientModel clientFrom = null;
		ClientModel clientTo = new ClientModel.Builder().withAccount("002").build();
		BigDecimal amount = new BigDecimal(333.00);
		ResponseModel response = TransferRule.validateTransfer(clientFrom, clientTo, amount);
		assertFalse(response.getStatus());
	}
	
	@Test
	public void testShouldFailWithInvalidAccountTo() {
		ClientModel clientFrom =  new ClientModel.Builder().withAccount("001").withBalance(new BigDecimal(334.0)).build();
		ClientModel clientTo = new ClientModel.Builder().withAccount("").build();
		BigDecimal amount = new BigDecimal(333.00);
		ResponseModel response = TransferRule.validateTransfer(clientFrom, clientTo, amount);
		assertFalse(response.getStatus());
	}
	
	@Test
	public void testShouldFailWithInvalidFunds() {
		ClientModel clientFrom = new ClientModel.Builder().withAccount("001").withBalance(new BigDecimal(332.0)).build();
		ClientModel clientTo = new ClientModel.Builder().withAccount("002").build();
		BigDecimal amount = new BigDecimal(333.00);
		ResponseModel response = TransferRule.validateTransfer(clientFrom, clientTo, amount);
		assertFalse(response.getStatus());
	}
	
	@Test
	public void testShouldFailWithInvalidAmountOfMoney() {
		ClientModel clientFrom = new ClientModel.Builder().withAccount("001").withBalance(new BigDecimal(332.0)).build();
		ClientModel clientTo = new ClientModel.Builder().withAccount("002").build();
		BigDecimal amount = new BigDecimal(0.0);
		ResponseModel response = TransferRule.validateTransfer(clientFrom, clientTo, amount);
		assertFalse(response.getStatus());
	}
	
	@Test
	public void testIfTheExchangeIsValid() {
		ClientModel clientFrom = new ClientModel.Builder().withAccount("001").withBalance(new BigDecimal(334.0)).build();
		ClientModel clientTo = new ClientModel.Builder().withAccount("002").build();
		BigDecimal amount = new BigDecimal(333.00);
		ResponseModel response = TransferRule.validateTransfer(clientFrom, clientTo, amount);
		assertTrue(response.getStatus());
	}
}
