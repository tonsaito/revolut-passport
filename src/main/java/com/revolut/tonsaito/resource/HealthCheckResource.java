package com.revolut.tonsaito.resource;

import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/healthcheck")
public class HealthCheckResource {
	
	@GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response listAll() throws SQLException {
		return Response.ok("HEALTHCHECKSTATUSOK").build();
    }
}
