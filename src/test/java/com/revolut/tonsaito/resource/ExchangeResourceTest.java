package com.revolut.tonsaito.resource;

import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import com.revolut.tonsaito.model.TransactionModel;

public class ExchangeResourceTest extends JerseyTest{
	
    @Override
    protected Application configure() {
        return new ResourceConfig(ExchangeResource.class);
    }
    
    @Test
    public void shouldListHistoryExchange() {
        final List<TransactionModel> response = target("exchange/history").request().get(List.class);
        assertTrue(response.size() >= 0);
    }
}
