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
import com.revolut.tonsaito.model.ExchangeModel;
import com.revolut.tonsaito.model.TransactionModel;

public class ExchangeResourceTest extends JerseyTest{
	private static final String ACCOUNT_TEST_NUMBER_0 = "00000000000001";
	private static final String ACCOUNT_TEST_NUMBER_1 = "11111111111111";
	private static final String TEST = "TEST";
	
    @Override
    protected Application configure() {
        return new ResourceConfig(ExchangeResource.class);
    }
    
    @Test
    public void shouldListHistoryExchange() {
        final List<TransactionModel> response = target("exchange/history").request().get(List.class);
        assertTrue(response.size() >= 0);
    }
    
	@Test
	public void shouldExchangeMoney() {
		ExchangeModel exchangeModel = new ExchangeModel();
		exchangeModel.setAccountFrom(ACCOUNT_TEST_NUMBER_0);
		exchangeModel.setAccountTo(ACCOUNT_TEST_NUMBER_1);
		exchangeModel.setAmount(BigDecimal.valueOf(10.0));
		ClientDAO.deleteByAccountNumber(ACCOUNT_TEST_NUMBER_0);
		ClientDAO.deleteByAccountNumber(ACCOUNT_TEST_NUMBER_1);
		ClientDAO.insert(TEST, ACCOUNT_TEST_NUMBER_0, BigDecimal.valueOf(100.0));
		ClientDAO.insert(TEST, ACCOUNT_TEST_NUMBER_1, BigDecimal.valueOf(200.0));
		Response response = target("exchange/money").request().post(Entity.json(exchangeModel));
		assertEquals(200, response.getStatus());
		assertEquals(BigDecimal.valueOf(90.0), ClientDAO.getOne(new ClientModel.Builder().withAccount(ACCOUNT_TEST_NUMBER_0).build()).getBalance());
		assertEquals(BigDecimal.valueOf(210.0), ClientDAO.getOne(new ClientModel.Builder().withAccount(ACCOUNT_TEST_NUMBER_1).build()).getBalance());
	}
	
	@Test
	public void shouldNotExchangeMoneyDueTheInvalidAmountOfMoneyTransfer() {
		ExchangeModel exchangeModel = new ExchangeModel();
		exchangeModel.setAccountFrom(ACCOUNT_TEST_NUMBER_0);
		exchangeModel.setAccountTo(ACCOUNT_TEST_NUMBER_1);
		exchangeModel.setAmount(BigDecimal.valueOf(0.0));
		ClientDAO.deleteByAccountNumber(ACCOUNT_TEST_NUMBER_0);
		ClientDAO.deleteByAccountNumber(ACCOUNT_TEST_NUMBER_1);
		ClientDAO.insert(TEST, ACCOUNT_TEST_NUMBER_0, BigDecimal.valueOf(100.0));
		ClientDAO.insert(TEST, ACCOUNT_TEST_NUMBER_1, BigDecimal.valueOf(200.0));
		Response response = target("exchange/money").request().post(Entity.json(exchangeModel));
		assertEquals(400, response.getStatus());
	}
	
	@Test
	public void shouldNotExchangeMoneyDueTheInvalidAccountExchange() {
		ExchangeModel exchangeModel = new ExchangeModel();
		exchangeModel.setAccountFrom(ACCOUNT_TEST_NUMBER_1);
		exchangeModel.setAccountTo(ACCOUNT_TEST_NUMBER_1);
		exchangeModel.setAmount(BigDecimal.valueOf(10.0));
		ClientDAO.deleteByAccountNumber(ACCOUNT_TEST_NUMBER_0);
		ClientDAO.deleteByAccountNumber(ACCOUNT_TEST_NUMBER_1);
		ClientDAO.insert(TEST, ACCOUNT_TEST_NUMBER_0, BigDecimal.valueOf(100.0));
		ClientDAO.insert(TEST, ACCOUNT_TEST_NUMBER_1, BigDecimal.valueOf(200.0));
		Response response = target("exchange/money").request().post(Entity.json(exchangeModel));
		assertEquals(400, response.getStatus());
	}
	
	@Test
	public void shouldNotExchangeMoneyDueTheInvalidFromAccount() {
		ExchangeModel exchangeModel = new ExchangeModel();
		exchangeModel.setAccountFrom(ACCOUNT_TEST_NUMBER_0);
		exchangeModel.setAccountTo(ACCOUNT_TEST_NUMBER_1);
		exchangeModel.setAmount(BigDecimal.valueOf(10.0));
		ClientDAO.deleteByAccountNumber(ACCOUNT_TEST_NUMBER_0);
		ClientDAO.deleteByAccountNumber(ACCOUNT_TEST_NUMBER_1);
		ClientDAO.insert(TEST, ACCOUNT_TEST_NUMBER_1, BigDecimal.valueOf(200.0));
		Response response = target("exchange/money").request().post(Entity.json(exchangeModel));
		assertEquals(400, response.getStatus());
	}
	
	@Test
	public void shouldNotExchangeMoneyDueTheInvalidToAccount() {
		ExchangeModel exchangeModel = new ExchangeModel();
		exchangeModel.setAccountFrom(ACCOUNT_TEST_NUMBER_0);
		exchangeModel.setAccountTo(ACCOUNT_TEST_NUMBER_1);
		exchangeModel.setAmount(BigDecimal.valueOf(10.0));
		ClientDAO.deleteByAccountNumber(ACCOUNT_TEST_NUMBER_0);
		ClientDAO.deleteByAccountNumber(ACCOUNT_TEST_NUMBER_1);
		ClientDAO.insert(TEST, ACCOUNT_TEST_NUMBER_0, BigDecimal.valueOf(100.0));
		Response response = target("exchange/money").request().post(Entity.json(exchangeModel));
		assertEquals(400, response.getStatus());
	}
	
	@Test
	public void shouldNotExchangeMoneyDueTheInvalidFunds() {
		ExchangeModel exchangeModel = new ExchangeModel();
		exchangeModel.setAccountFrom(ACCOUNT_TEST_NUMBER_1);
		exchangeModel.setAccountTo(ACCOUNT_TEST_NUMBER_1);
		exchangeModel.setAmount(BigDecimal.valueOf(1000.0));
		ClientDAO.deleteByAccountNumber(ACCOUNT_TEST_NUMBER_0);
		ClientDAO.deleteByAccountNumber(ACCOUNT_TEST_NUMBER_1);
		ClientDAO.insert(TEST, ACCOUNT_TEST_NUMBER_0, BigDecimal.valueOf(100.0));
		ClientDAO.insert(TEST, ACCOUNT_TEST_NUMBER_1, BigDecimal.valueOf(200.0));
		Response response = target("exchange/money").request().post(Entity.json(exchangeModel));
		assertEquals(400, response.getStatus());
	}
}
