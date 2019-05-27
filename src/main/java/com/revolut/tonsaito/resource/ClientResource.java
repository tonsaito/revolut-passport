package com.revolut.tonsaito.resource;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.revolut.tonsaito.dao.ClientDAO;
import com.revolut.tonsaito.model.ClientModel;

@Path("/client")
public class ClientResource {

	@GET
	@Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAll() throws SQLException {
		return Response.ok(ClientDAO.getAll(null)).build();
    }
	
	@GET
	@Path("/name/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listByName(@PathParam(value = "name") String name) throws SQLException, UnsupportedEncodingException {
		return Response.ok(ClientDAO.getOne(new ClientModel.Builder().withName(URLDecoder.decode(name, "UTF-8")).build())).build();
    }
	
	@GET
	@Path("/account/{account}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listByAccount(@PathParam(value = "account") String account) throws SQLException {
		return Response.ok(ClientDAO.getOne(new ClientModel.Builder().withAccount(account).build())).build();
    }
}
