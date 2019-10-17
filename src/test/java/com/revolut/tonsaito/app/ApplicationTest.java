package com.revolut.tonsaito.app;

import static org.junit.Assert.assertTrue;

import java.net.MalformedURLException;

import javax.servlet.ServletException;

import org.apache.catalina.LifecycleException;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import com.revolut.tonsaito.resource.HealthCheckResource;

public class ApplicationTest extends JerseyTest{
	
	@Override
	protected javax.ws.rs.core.Application configure() {
		return new ResourceConfig(HealthCheckResource.class);
	}

	@Test
	public void shouldStartStopApplicationWithoutErrors() throws MalformedURLException, ServletException, LifecycleException, InterruptedException{
		Application application = new Application();
		String response = "";
		application.initDbConfigurations();
		application.start();
		response = target("healthcheck").request().get(String.class);
	    Thread.sleep(5000);
		application.stop();
		assertTrue(response.equals("{\"message\":\"UP\",\"status\":true}"));
	}
}
