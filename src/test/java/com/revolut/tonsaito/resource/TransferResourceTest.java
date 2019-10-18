package com.revolut.tonsaito.resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import com.revolut.tonsaito.dao.ClientDAO;
import com.revolut.tonsaito.model.ClientModel;
import com.revolut.tonsaito.model.TransactionModel;
import com.revolut.tonsaito.model.TransferModel;

public class TransferResourceTest extends JerseyTest{
	private static final String ACCOUNT_TEST_NUMBER_0 = "00000000000001";
	private static final String ACCOUNT_TEST_NUMBER_1 = "11111111111111";
	private static final String TEST = "TEST";
	
    @Override
    protected Application configure() {
        return new ResourceConfig(TransferResource.class);
    }
    
    @Test
    public void shouldListHistoryTransfer() {
        final List<TransactionModel> response = target("transfer").request().get(List.class);
        assertTrue(response.size() >= 0);
    }
    
	@Test
	public void shouldTransferMoney() {
		TransferModel TransferModel = new TransferModel();
		TransferModel.setAccountFrom(ACCOUNT_TEST_NUMBER_0);
		TransferModel.setAccountTo(ACCOUNT_TEST_NUMBER_1);
		TransferModel.setAmount(BigDecimal.valueOf(10.0));
		ClientDAO.deleteByAccountNumber(ACCOUNT_TEST_NUMBER_0);
		ClientDAO.deleteByAccountNumber(ACCOUNT_TEST_NUMBER_1);
		ClientDAO.insert(TEST, ACCOUNT_TEST_NUMBER_0, BigDecimal.valueOf(100.0));
		ClientDAO.insert(TEST, ACCOUNT_TEST_NUMBER_1, BigDecimal.valueOf(200.0));
		Response response = target("transfer").request().post(Entity.json(TransferModel));
		assertEquals(200, response.getStatus());
		assertEquals(BigDecimal.valueOf(90.0), ClientDAO.getOne(new ClientModel.Builder().withAccount(ACCOUNT_TEST_NUMBER_0).build()).getBalance());
		assertEquals(BigDecimal.valueOf(210.0), ClientDAO.getOne(new ClientModel.Builder().withAccount(ACCOUNT_TEST_NUMBER_1).build()).getBalance());
	}
	
	@Test
	public void shouldNotTransferMoneyDueTheInvalidAmountOfMoneyTransfer() {
		TransferModel TransferModel = new TransferModel();
		TransferModel.setAccountFrom(ACCOUNT_TEST_NUMBER_0);
		TransferModel.setAccountTo(ACCOUNT_TEST_NUMBER_1);
		TransferModel.setAmount(BigDecimal.valueOf(0.0));
		ClientDAO.deleteByAccountNumber(ACCOUNT_TEST_NUMBER_0);
		ClientDAO.deleteByAccountNumber(ACCOUNT_TEST_NUMBER_1);
		ClientDAO.insert(TEST, ACCOUNT_TEST_NUMBER_0, BigDecimal.valueOf(100.0));
		ClientDAO.insert(TEST, ACCOUNT_TEST_NUMBER_1, BigDecimal.valueOf(200.0));
		Response response = target("transfer").request().post(Entity.json(TransferModel));
		assertEquals(400, response.getStatus());
	}
	
	@Test
	public void shouldNotTransferMoneyDueTheInvalidAccountTransfer() {
		TransferModel TransferModel = new TransferModel();
		TransferModel.setAccountFrom(ACCOUNT_TEST_NUMBER_1);
		TransferModel.setAccountTo(ACCOUNT_TEST_NUMBER_1);
		TransferModel.setAmount(BigDecimal.valueOf(10.0));
		ClientDAO.deleteByAccountNumber(ACCOUNT_TEST_NUMBER_0);
		ClientDAO.deleteByAccountNumber(ACCOUNT_TEST_NUMBER_1);
		ClientDAO.insert(TEST, ACCOUNT_TEST_NUMBER_0, BigDecimal.valueOf(100.0));
		ClientDAO.insert(TEST, ACCOUNT_TEST_NUMBER_1, BigDecimal.valueOf(200.0));
		Response response = target("transfer").request().post(Entity.json(TransferModel));
		assertEquals(400, response.getStatus());
	}
	
	@Test
	public void shouldNotTransferMoneyDueTheInvalidFromAccount() {
		TransferModel TransferModel = new TransferModel();
		TransferModel.setAccountFrom(ACCOUNT_TEST_NUMBER_0);
		TransferModel.setAccountTo(ACCOUNT_TEST_NUMBER_1);
		TransferModel.setAmount(BigDecimal.valueOf(10.0));
		ClientDAO.deleteByAccountNumber(ACCOUNT_TEST_NUMBER_0);
		ClientDAO.deleteByAccountNumber(ACCOUNT_TEST_NUMBER_1);
		ClientDAO.insert(TEST, ACCOUNT_TEST_NUMBER_1, BigDecimal.valueOf(200.0));
		Response response = target("transfer").request().post(Entity.json(TransferModel));
		assertEquals(400, response.getStatus());
	}
	
	@Test
	public void shouldNotTransferMoneyDueTheInvalidToAccount() {
		TransferModel TransferModel = new TransferModel();
		TransferModel.setAccountFrom(ACCOUNT_TEST_NUMBER_0);
		TransferModel.setAccountTo(ACCOUNT_TEST_NUMBER_1);
		TransferModel.setAmount(BigDecimal.valueOf(10.0));
		ClientDAO.deleteByAccountNumber(ACCOUNT_TEST_NUMBER_0);
		ClientDAO.deleteByAccountNumber(ACCOUNT_TEST_NUMBER_1);
		ClientDAO.insert(TEST, ACCOUNT_TEST_NUMBER_0, BigDecimal.valueOf(100.0));
		Response response = target("transfer").request().post(Entity.json(TransferModel));
		assertEquals(400, response.getStatus());
	}
	
	@Test
	public void shouldNotTransferMoneyDueTheInvalidFunds() {
		TransferModel TransferModel = new TransferModel();
		TransferModel.setAccountFrom(ACCOUNT_TEST_NUMBER_1);
		TransferModel.setAccountTo(ACCOUNT_TEST_NUMBER_1);
		TransferModel.setAmount(BigDecimal.valueOf(1000.0));
		ClientDAO.deleteByAccountNumber(ACCOUNT_TEST_NUMBER_0);
		ClientDAO.deleteByAccountNumber(ACCOUNT_TEST_NUMBER_1);
		ClientDAO.insert(TEST, ACCOUNT_TEST_NUMBER_0, BigDecimal.valueOf(100.0));
		ClientDAO.insert(TEST, ACCOUNT_TEST_NUMBER_1, BigDecimal.valueOf(200.0));
		Response response = target("transfer").request().post(Entity.json(TransferModel));
		assertEquals(400, response.getStatus());
	}
}
