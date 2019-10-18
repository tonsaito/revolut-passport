package com.revolut.tonsaito.rule;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import javax.ws.rs.BadRequestException;

import org.junit.Before;
import org.junit.Test;

import com.revolut.tonsaito.model.ClientModel;
import com.revolut.tonsaito.model.ResponseModel;
import com.revolut.tonsaito.model.TransferModel;

public class ExchangeRuleTest {

	@Before
	public void setup(){	}
	
	@Test
	public void testShouldValidateTransferModelWithoutErrors() {
		TransferModel model = new TransferModel();
		model.setAccountFrom("001");
		model.setAccountTo("002");
		model.setAmount(new BigDecimal(10));
		TransferRule.validateTransferModel(model);
	}
	
	@Test(expected = BadRequestException.class)
	public void testValidateTransferModelShouldFailWithInvalidPayload() {
		TransferRule.validateTransferModel(null);
	}
	
	@Test(expected = BadRequestException.class)
	public void testValidateTransferModelShouldFailWithInvalidAccountFrom() {
		TransferModel model = new TransferModel();
		model.setAccountFrom("");
		model.setAccountTo("002");
		model.setAmount(new BigDecimal(10));
		TransferRule.validateTransferModel(model);
	}
	
	@Test(expected = BadRequestException.class)
	public void testValidateTransferModelShouldFailWithInvalidAccountTo() {
		TransferModel model = new TransferModel();
		model.setAccountFrom("001");
		model.setAccountTo("");
		model.setAmount(new BigDecimal(10));
		TransferRule.validateTransferModel(model);
	}
	
	@Test
	public void testShouldValidateAccountsWithoutErrors() {
		ClientModel clientFrom = new ClientModel.Builder().withAccount("001").build();
		ClientModel clientTo = new ClientModel.Builder().withAccount("002").build();
		TransferRule.validateAccounts(clientFrom, clientTo);
	}
	
	@Test(expected = BadRequestException.class)
	public void testValidateAccountsShouldFailWithInvalidAccountFrom() {
		ClientModel clientFrom = null;
		ClientModel clientTo = new ClientModel.Builder().withAccount("002").build();
		TransferRule.validateAccounts(clientFrom, clientTo);
	}
	
	@Test(expected = BadRequestException.class)
	public void testValidateAccountsShouldFailWithInvalidAccountTo() {
		ClientModel clientFrom =  new ClientModel.Builder().withAccount("001").withBalance(new BigDecimal(334.0)).build();
		ClientModel clientTo = new ClientModel.Builder().withAccount("").build();
		TransferRule.validateAccounts(clientFrom, clientTo);
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
