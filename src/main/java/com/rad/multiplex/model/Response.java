package com.rad.multiplex.model;

import java.math.BigDecimal;

import java.time.LocalDateTime;


import com.google.gson.annotations.Expose;

public class Response {
	@Expose
	private boolean success;
	@Expose
	private BigDecimal amount;
	@Expose
	private LocalDateTime expiration;
	@Expose
	private String errormessage;

	public Response(boolean success, BigDecimal amount, LocalDateTime expiration, String errormessage) {

		this.success = success;
		this.amount = amount;
		this.expiration = expiration;
		this.errormessage = errormessage;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public LocalDateTime getExpiration() {
		return expiration;
	}

	public void setExpiration(LocalDateTime expiration) {
		this.expiration = expiration;
	}

	public String getErrormessage() {
		return errormessage;
	}

	public void setErrormessage(String errormessage) {
		this.errormessage = errormessage;
	}

}
