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
import com.revolut.tonsaito.rule.TransferRule;

@Path("/transfer")
public class TransferResource {

	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response history() throws SQLException {
		return Response.ok(TransactionDAO.getAll()).build();
    }
	
	@POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response exchangeMoney(ExchangeModel model) throws SQLException {
		ClientModel clientFrom = ClientDAO.getOne(new ClientModel.Builder().withAccount(model.getAccountFrom()).build());
		ClientModel clientTo = ClientDAO.getOne(new ClientModel.Builder().withAccount(model.getAccountTo()).build());
		ResponseModel response = TransferRule.validateTransfer(clientFrom, clientTo, model.getAmount());
		if(response.getStatus()) {
			ClientDAO.transfer(model.getAccountFrom(), model.getAccountTo(), model.getAmount());
			TransactionDAO.insert(model.getAccountFrom(), model.getAccountTo(), model.getAmount(), new Timestamp(System.currentTimeMillis()), true, "");
			return Response.ok(new ResponseModel(true, response.getMessage())).build();
		} else {
			TransactionDAO.insert(model.getAccountFrom(), model.getAccountTo(), model.getAmount(), new Timestamp(System.currentTimeMillis()), false, response.getMessage());
			return Response.status(Status.BAD_REQUEST).entity(new ResponseModel(false, response.getMessage())).build();
		}
    }
}
