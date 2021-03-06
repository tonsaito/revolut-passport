package com.revolut.tonsaito.config;

import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;

import com.revolut.tonsaito.mapper.BadRequestExceptionMapper;
import com.revolut.tonsaito.mapper.InternalServerErrorExceptionMapper;
import com.revolut.tonsaito.mapper.UncaughtExceptionMapper;
import com.revolut.tonsaito.resource.ClientResource;
import com.revolut.tonsaito.resource.HealthCheckResource;
import com.revolut.tonsaito.resource.TransferResource;

public class ResourceLoaderTest {

	@Test
	public void shouldReturnAllResources() {
		Set<Class<?>> classes = new ResourceLoader().getClasses();
		assertTrue(classes.contains(TransferResource.class));
		assertTrue(classes.contains(ClientResource.class));
		assertTrue(classes.contains(HealthCheckResource.class));
		assertTrue(classes.contains(UncaughtExceptionMapper.class));
		assertTrue(classes.contains(BadRequestExceptionMapper.class));
		assertTrue(classes.contains(InternalServerErrorExceptionMapper.class));
	}
}
