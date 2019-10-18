package com.revolut.tonsaito.rule;

import java.math.BigDecimal;

import javax.ws.rs.BadRequestException;

import org.apache.commons.lang3.StringUtils;

import com.revolut.tonsaito.model.ClientModel;
import com.revolut.tonsaito.model.ResponseModel;
import com.revolut.tonsaito.model.TransferModel;

public class TransferRule {
	private static final String MONEY_TRANSFER_SUCCESSFULLY_COMPLETED = "Money Transfer successfully completed!";
	private static final String INVALID_AMOUNT_OF_MONEY = "Invalid amount of money";
	private static final String INVALID_FUNDS = "Invalid funds :(";
	private static final String ACCOUNT_FROM_AND_ACCOUNT_TO_MUST_BE_DIFFERENT = "Account From and account To MUST be different";
	private static final String INVALID_REQUEST = "Invalid request";
	private static final String INVALID_CLIENT_TO_ACCOUNT = "Invalid client TO Account";
	private static final String INVALID_CLIENT_FROM_ACCOUNT = "Invalid client FROM Account";

	private TransferRule() {}
	
	public static void validateTransferModel(TransferModel model) {
		if(model == null) {
			throw new BadRequestException(INVALID_REQUEST);
		}
		if(StringUtils.isEmpty(model.getAccountFrom())) {
			throw new BadRequestException(INVALID_CLIENT_FROM_ACCOUNT);
		}
		if(StringUtils.isEmpty(model.getAccountTo())) {
			throw new BadRequestException(INVALID_CLIENT_TO_ACCOUNT);
		}
	}
	

	public static void validateAccounts(ClientModel clientFrom, ClientModel clientTo) {
		if(clientFrom == null || StringUtils.isBlank(clientFrom.getAccount())) {
			throw new BadRequestException(INVALID_CLIENT_FROM_ACCOUNT);
		}
		if(clientTo == null || StringUtils.isBlank(clientTo.getAccount())) {
			throw new BadRequestException(INVALID_CLIENT_TO_ACCOUNT);
		}
	}

	public static ResponseModel validateTransfer(ClientModel clientFrom, ClientModel clientTo, BigDecimal amount) {
		if(clientFrom.getAccount().equals(clientTo.getAccount())) {
			return new ResponseModel.Builder().withStatus(false).withMessage(ACCOUNT_FROM_AND_ACCOUNT_TO_MUST_BE_DIFFERENT).build();
		}
		if(clientFrom.getBalance().compareTo(BigDecimal.ZERO) <= 0 || clientFrom.getBalance().compareTo(amount) <= 0) {
			return new ResponseModel.Builder().withStatus(false).withMessage(INVALID_FUNDS).build();
		}
		if(amount.compareTo(BigDecimal.ZERO) <= 0) {
			return new ResponseModel.Builder().withStatus(false).withMessage(INVALID_AMOUNT_OF_MONEY).build();
		}
		return new ResponseModel.Builder().withStatus(true).withMessage(MONEY_TRANSFER_SUCCESSFULLY_COMPLETED).build();
	}

}
