package com.revolut.tonsaito.config;

import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;

import com.revolut.tonsaito.mapper.UncaughtExceptionMapper;
import com.revolut.tonsaito.resource.ClientResource;
import com.revolut.tonsaito.resource.TransferResource;
import com.revolut.tonsaito.resource.HealthCheckResource;

public class ResourceLoaderTest {

	@Test
	public void shouldReturnAllResources() {
		Set<Class<?>> classes = new ResourceLoader().getClasses();
		assertTrue(classes.contains(TransferResource.class));
		assertTrue(classes.contains(ClientResource.class));
		assertTrue(classes.contains(HealthCheckResource.class));
		assertTrue(classes.contains(UncaughtExceptionMapper.class));
	}
}
