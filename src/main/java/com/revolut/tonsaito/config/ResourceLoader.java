package com.revolut.tonsaito.config;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import com.revolut.tonsaito.resource.ClientResource;
import com.revolut.tonsaito.resource.ExchangeResource;

public class ResourceLoader extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<Class<?>>();

        classes.add(ClientResource.class);
        classes.add(ExchangeResource.class);
        return classes;
    }
}