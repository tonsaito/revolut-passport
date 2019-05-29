package com.revolut.tonsaito.resource;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

public class HealthCheckResourceTest extends JerseyTest {

	@Override
	protected Application configure() {
		return new ResourceConfig(HealthCheckResource.class);
	}

	@Test
	public void shouldCallHealthCheckStatusWithoutError() {
		assertEquals("HEALTHCHECKSTATUSOK", target("healthcheck").request().get(String.class));
	}

}
