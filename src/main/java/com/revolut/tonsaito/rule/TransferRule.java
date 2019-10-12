package com.revolut.tonsaito.rule;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;

import com.revolut.tonsaito.model.ClientModel;
import com.revolut.tonsaito.model.ResponseModel;

public class TransferRule {
	
	private TransferRule() {}

	public static ResponseModel validateTransfer(ClientModel clientFrom, ClientModel clientTo, BigDecimal amount) {
		if(clientFrom == null || StringUtils.isBlank(clientFrom.getAccount())) {
			return new ResponseModel.Builder().withStatus(false).withMessage("Invalid client FROM Account").build();
		}
		if(clientTo == null || StringUtils.isBlank(clientTo.getAccount())) {
			return new ResponseModel.Builder().withStatus(false).withMessage("Invalid client TO Account").build();
		}
		if(clientFrom.getAccount().equals(clientTo.getAccount())) {
			return new ResponseModel.Builder().withStatus(false).withMessage("Account From and account TO MUST be different").build();
		}
		if(clientFrom.getBalance().compareTo(BigDecimal.ZERO) <= 0 || clientFrom.getBalance().compareTo(amount) <= 0) {
			return new ResponseModel.Builder().withStatus(false).withMessage("Invalid funds :(").build();
		}
		if(amount.compareTo(BigDecimal.ZERO) <= 0) {
			return new ResponseModel.Builder().withStatus(false).withMessage("Invalid amount of money").build();
		}
		return new ResponseModel.Builder().withStatus(true).withMessage("Money Exchange successfully completed!").build();
	}
}
