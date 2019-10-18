package com.revolut.tonsaito.resource;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

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
import com.revolut.tonsaito.model.ResponseModel;
import com.revolut.tonsaito.model.TransferModel;
import com.revolut.tonsaito.rule.TransferRule;

import de.jkeylockmanager.manager.KeyLockManager;
import de.jkeylockmanager.manager.implementation.lockstripe.StripedKeyLockManager;

@Path("/transfer")
public class TransferResource {
	private static final KeyLockManager lockManager = new StripedKeyLockManager(10, TimeUnit.MINUTES, 120);

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response history() throws SQLException {
		return Response.ok(TransactionDAO.getAll()).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response exchangeMoney(TransferModel model) throws SQLException {
		TransferRule.validateTransferModel(model);
		ClientModel clientFrom = ClientDAO.getOne(new ClientModel.Builder().withAccount(model.getAccountFrom()).build());
		ClientModel clientTo = ClientDAO.getOne(new ClientModel.Builder().withAccount(model.getAccountTo()).build());
		TransferRule.validateAccounts(clientFrom, clientTo);
		return lockManager.executeLocked(clientFrom.getId() + "/" + clientTo.getId(), () -> {
			ResponseModel response = TransferRule.validateTransfer(clientFrom, clientTo, model.getAmount());
			if (response.getStatus()) {
				clientFrom.substractBalance(model.getAmount());
				clientTo.addBalance(model.getAmount());
				ClientDAO.updateClientsBalance(clientFrom, clientTo);
				TransactionDAO.insert(model.getAccountFrom(), model.getAccountTo(), model.getAmount(),
						new Timestamp(System.currentTimeMillis()), true, "");
				return Response.ok(new ResponseModel(true, response.getMessage())).build();
			} else {
				TransactionDAO.insert(model.getAccountFrom(), model.getAccountTo(), model.getAmount(),
						new Timestamp(System.currentTimeMillis()), false, response.getMessage());
				return Response.status(Status.BAD_REQUEST).entity(new ResponseModel(false, response.getMessage()))
						.build();
			}
		});
	}
}
