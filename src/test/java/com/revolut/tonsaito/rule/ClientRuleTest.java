package com.revolut.tonsaito.rule;

import java.math.BigDecimal;

import javax.ws.rs.BadRequestException;

import org.junit.Before;
import org.junit.Test;

import com.revolut.tonsaito.model.ClientModel;

public class ClientRuleTest {

	@Before
	public void setup(){	}
	
	@Test
	public void testShouldValidateClientModelWithoutErrors() {
		ClientModel model = new ClientModel();
		model.setName("Tomas");
		model.setBalance(new BigDecimal("0"));
		model.setAccount("001");
		ClientRule.validateClientModel(model);
	}
	
	@Test(expected = BadRequestException.class)
	public void testValidateClientModelShouldFailWithEmptyName() {
		ClientModel model = new ClientModel();
		model.setName("");
		model.setBalance(new BigDecimal("0"));
		model.setAccount("001");
		ClientRule.validateClientModel(model);
	}
	
	@Test(expected = BadRequestException.class)
	public void testValidateClientModelShouldFailWithBalanceLessThanZero() {
		ClientModel model = new ClientModel();
		model.setName("Tomas");
		model.setBalance(new BigDecimal("-1"));
		model.setAccount("001");
		ClientRule.validateClientModel(model);
	}
	
	@Test(expected = BadRequestException.class)
	public void testValidateClientModelShouldFailWithEmptyAccount() {
		ClientModel model = new ClientModel();
		model.setName("Tomas");
		model.setBalance(new BigDecimal("0"));
		model.setAccount("");
		ClientRule.validateClientModel(model);
	}
	
	@Test(expected = BadRequestException.class)
	public void testValidateClientModelShouldFailWithNonNumeralAccountNumber() {
		ClientModel model = new ClientModel();
		model.setName("Tomas");
		model.setBalance(new BigDecimal("0"));
		model.setAccount("123a");
		ClientRule.validateClientModel(model);
	}
}
