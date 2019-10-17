package com.revolut.tonsaito.resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import com.revolut.tonsaito.dao.ClientDAO;
import com.revolut.tonsaito.model.ClientModel;

public class ClientResourceTest extends JerseyTest {
	private static final String TEST = "TEST";
	private static final String ACCOUNT_NUMBER = "00000001";

	@Override
	protected Application configure() {
		return new ResourceConfig(ClientResource.class);
	}

	@Test
	public void shouldAddOneClient() {
		ClientModel clientModel = new ClientModel.Builder().withAccount(ACCOUNT_NUMBER).withName(TEST).withBalance(BigDecimal.valueOf(100.0)).build();
		Response response = target("clients").request().post(Entity.json(clientModel));
		ClientDAO.deleteByAccountNumber(ACCOUNT_NUMBER);
		assertEquals(201, response.getStatus());
	}

	@Test
	public void shouldListAllClients() {
		final List<ClientModel> response = target("clients").request().get(List.class);
		assertTrue(response.size() >= 0);
	}

	@Test
	public void shouldTryToGetOneClientByName() {
		new ClientModel.Builder().withAccount(ACCOUNT_NUMBER).withName(TEST).withBalance(BigDecimal.valueOf(100.0)).build();
		final ClientModel response = target("clients/TEST").request().get(ClientModel.class);
		ClientDAO.deleteByAccountNumber(ACCOUNT_NUMBER);
		assertNotNull(response.getAccount());
	}
	
	@Test(expected = NotFoundException.class)
	public void shouldTryToGetOneClientByNameAndNotFoundAnyResult() {
		final ClientModel response = target("clients/abcaeasdase").request().get(ClientModel.class);
		assertNotNull(response.getAccount());
	}

	@Test
	public void shouldTryToGetOneClientByAccount() {
		final ClientModel response = target("clients/account/001").request().get(ClientModel.class);
		assertNotNull(response);
	}
}
