package com.revolut.tonsaito.rule;

import java.math.BigDecimal;

import javax.ws.rs.BadRequestException;

import org.apache.commons.lang3.StringUtils;

import com.revolut.tonsaito.model.ClientModel;
import com.revolut.tonsaito.model.ResponseModel;
import com.revolut.tonsaito.model.TransferModel;

public class TransferRule {
	
	private TransferRule() {}
	
	public static void validateTransferModel(TransferModel model) {
		if(model == null || !StringUtils.isBlank(model.getAccountFrom())) {
			System.out.println("a");
			throw new BadRequestException("Invalid client FROM Account");
		}
		if(model == null || !StringUtils.isBlank(model.getAccountTo())) {
			System.out.println("b");
			throw new BadRequestException("Invalid client TO Account");
		}
	}

	public static ResponseModel validateTransfer(ClientModel clientFrom, ClientModel clientTo, BigDecimal amount) {
		if(clientFrom.getAccount().equals(clientTo.getAccount())) {
			return new ResponseModel.Builder().withStatus(false).withMessage("Account From and account TO MUST be different").build();
		}
		if(clientFrom.getBalance().compareTo(BigDecimal.ZERO) <= 0 || clientFrom.getBalance().compareTo(amount) <= 0) {
			return new ResponseModel.Builder().withStatus(false).withMessage("Invalid funds :(").build();
		}
		if(amount.compareTo(BigDecimal.ZERO) <= 0) {
			return new ResponseModel.Builder().withStatus(false).withMessage("Invalid amount of money").build();
		}
		return new ResponseModel.Builder().withStatus(true).withMessage("Money Transfer successfully completed!").build();
	}
}
