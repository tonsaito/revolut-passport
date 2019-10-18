package com.revolut.tonsaito.rule;

import java.math.BigDecimal;

import javax.ws.rs.BadRequestException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.revolut.tonsaito.model.ClientModel;

public class ClientRule {
	private static final String INVALID_ACCOUNT_NUMBER_ONLY_NUMBERS_ARE_ALLOWED = "Invalid account number. Only numbers are allowed";
	private static final String INVALID_ACCOUNT_BALANCE_MUST_BE_GREATER_OR_EQUAL_ZERO = "Invalid account balance. Must be greater or equal Zero";
	private static final String INVALID_NAME = "Invalid Name. Must not be empty";
	private static final String INVALID_REQUEST = "Invalid request";

	private ClientRule() {
	}
	
	public static void validateClientModel(ClientModel model) {
		if(model == null) {
			throw new BadRequestException(INVALID_REQUEST);
		}
		if(StringUtils.isEmpty(model.getName())) {
			throw new BadRequestException(INVALID_NAME);
		}
		if(model.getBalance().compareTo(BigDecimal.ZERO) < 0) {
			throw new BadRequestException(INVALID_ACCOUNT_BALANCE_MUST_BE_GREATER_OR_EQUAL_ZERO);
		}
		if(StringUtils.isEmpty(model.getAccount()) || !NumberUtils.isCreatable(model.getAccount())) {
			throw new BadRequestException(INVALID_ACCOUNT_NUMBER_ONLY_NUMBERS_ARE_ALLOWED);
		}
	}
}
