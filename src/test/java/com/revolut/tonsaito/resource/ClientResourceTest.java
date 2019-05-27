package com.revolut.tonsaito.resource;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import com.revolut.tonsaito.model.ClientModel;

public class ClientResourceTest extends JerseyTest{
	
    @Override
    protected Application configure() {
        return new ResourceConfig(ClientResource.class);
    }
    
    @Test
    public void shouldListAllClients() {
        final List<ClientModel> response = target("client/all").request().get(List.class);
        assertTrue(response.size() >= 0);
    }
    
    @Test
    public void shouldTryToGetOneClientByName() {
        final ClientModel response = target("client/name/John").request().get(ClientModel.class);
        assertNotNull(response);
    }
    
    @Test
    public void shouldTryToGetOneClientByAccount() {
        final ClientModel response = target("client/account/001").request().get(ClientModel.class);
        assertNotNull(response);
    }
}
