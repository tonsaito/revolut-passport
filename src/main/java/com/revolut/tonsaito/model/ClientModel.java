package com.revolut.tonsaito.model;

import java.math.BigDecimal;

public class ClientModel {
	private Integer id;
	private String name;
	private String account;
	private BigDecimal balance;
	
	public ClientModel() {		
	}
	
	public ClientModel(Integer id, String name, String account, BigDecimal balance) {
		this.id = id;
		this.name = name;
		this.account = account;
		this.balance = balance;
	}

	private ClientModel(Builder builder) {
		this.name = builder.name;
		this.account = builder.account;
		this.balance = builder.balance;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	
	public static class Builder{
		private String name;
		private String account;
		private BigDecimal balance;
		
		public Builder() {
		}
		
		public Builder withName(String name) {
			this.name = name;
			return this;
		}
		
		public Builder withAccount(String account) {
			this.account = account;
			return this;
		}
		
		public Builder withBalance(BigDecimal balance) {
			this.balance = balance;
			return this;
		}
		
		public ClientModel build() {
			return new ClientModel(this);
		}
	}
}
