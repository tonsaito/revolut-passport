package com.revolut.tonsaito.config;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import com.revolut.tonsaito.mapper.BadRequestExceptionMapper;
import com.revolut.tonsaito.mapper.NotFoundExceptionMapper;
import com.revolut.tonsaito.mapper.UncaughtExceptionMapper;
import com.revolut.tonsaito.resource.ClientResource;
import com.revolut.tonsaito.resource.TransferResource;
import com.revolut.tonsaito.resource.HealthCheckResource;

public class ResourceLoader extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<Class<?>>();

        classes.add(ClientResource.class);
        classes.add(TransferResource.class);
        classes.add(HealthCheckResource.class);
        classes.add(NotFoundExceptionMapper.class);
        classes.add(UncaughtExceptionMapper.class);
        classes.add(BadRequestExceptionMapper.class);
        return classes;
    }
}