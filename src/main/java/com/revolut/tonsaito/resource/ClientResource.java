package com.revolut.tonsaito.resource;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.revolut.tonsaito.dao.ClientDAO;
import com.revolut.tonsaito.model.ClientModel;
import com.revolut.tonsaito.rule.ClientRule;

@Path("/clients")
public class ClientResource {

	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAll() throws SQLException {
		return Response.ok(ClientDAO.getAll(null)).build();
    }
	
	@POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(ClientModel clientModel) throws SQLException {
		ClientRule.validateClientModel(clientModel);
		ClientModel insertedClientModel = ClientDAO.insert(clientModel.getName(), clientModel.getAccount(), clientModel.getBalance());
		if(insertedClientModel != null) {
			return Response.status(Status.CREATED).entity(clientModel).build();
		} else {
			throw new BadRequestException(String.format("Could not complete the operation. Please, check your request."));
		}
    }
	
	@GET
	@Path("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listByName(@PathParam(value = "name") String name) throws SQLException, UnsupportedEncodingException {
		ClientModel clientModel = ClientDAO.getOne(new ClientModel.Builder().withName(URLDecoder.decode(name, "UTF-8")).build());
		if(clientModel != null) {
			return Response.ok(clientModel).build();
		} else {
			throw new NotFoundException(String.format("Client with name %s not found", name));
		}
    }
	
	@GET
	@Path("/account/{account}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listByAccount(@PathParam(value = "account") String account) throws SQLException {
		ClientModel clientModel = ClientDAO.getOne(new ClientModel.Builder().withAccount(account).build());
		if(clientModel != null) {
			return Response.ok(clientModel).build();
		} else {
			throw new NotFoundException(String.format("Client with account %s not found", account));
		}
    }
}
