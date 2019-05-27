package com.revolut.tonsaito.model;

import java.math.BigDecimal;
import java.util.Date;

public class TransactionModel {
	private Integer id;
	private String accountFrom;
	private String accountTo;
	private BigDecimal amount;
	private Date date;
	private Boolean status;
	private String info;

	public TransactionModel(Integer id, String accountFrom, String accountTo, BigDecimal amount, Date date, Boolean status,
			String info) {
		this.id = id;
		this.accountFrom = accountFrom;
		this.accountTo = accountTo;
		this.amount = amount;
		this.date = date;
		this.status = status;
		this.info = info;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccountFrom() {
		return accountFrom;
	}

	public void setAccountFrom(String accountFrom) {
		this.accountFrom = accountFrom;
	}

	public String getAccountTo() {
		return accountTo;
	}

	public void setAccountTo(String accountTo) {
		this.accountTo = accountTo;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Boolean isStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

}
