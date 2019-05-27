package com.revolut.tonsaito.resource;

import java.sql.SQLException;
import java.sql.Timestamp;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.revolut.tonsaito.dao.ClientDAO;
import com.revolut.tonsaito.dao.TransactionDAO;
import com.revolut.tonsaito.model.ClientModel;
import com.revolut.tonsaito.model.ExchangeModel;
import com.revolut.tonsaito.model.ResponseModel;
import com.revolut.tonsaito.rule.ExchangeRule;

@Path("/exchange")
public class ExchangeResource {

	@GET
	@Path("/history")
    @Produces(MediaType.APPLICATION_JSON)
    public Response history() throws SQLException {
		return Response.ok(TransactionDAO.getAll()).build();
    }
	
	@POST
	@Path("/money")
    @Produces(MediaType.TEXT_PLAIN)
    public Response exchangeMoney(ExchangeModel model) throws SQLException {
		ClientModel clientFrom = ClientDAO.getOne(new ClientModel.Builder().withAccount(model.getAccountFrom()).build());
		ClientModel clientTo = ClientDAO.getOne(new ClientModel.Builder().withAccount(model.getAccountTo()).build());
		ResponseModel response = ExchangeRule.validateMoneyExchange(clientFrom, clientTo, model.getAmount());
		if(response.getStatus()) {
			ClientDAO.exchange(model.getAccountFrom(), model.getAccountTo(), model.getAmount());
			TransactionDAO.insert(model.getAccountFrom(), model.getAccountTo(), model.getAmount(), new Timestamp(System.currentTimeMillis()), true, "");
			return Response.ok(response.getMessage()).build();
		} else {
			TransactionDAO.insert(model.getAccountFrom(), model.getAccountTo(), model.getAmount(), new Timestamp(System.currentTimeMillis()), false, response.getMessage());
			return Response.status(Status.BAD_REQUEST).entity(response.getMessage()).build();
		}
    }
}
