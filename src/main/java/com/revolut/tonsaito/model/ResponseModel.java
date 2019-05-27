package com.revolut.tonsaito.model;

public class ResponseModel {
	private Boolean status;
	private String message;
	
	public ResponseModel() {}

	public ResponseModel(Boolean status, String message) {
		this.status = status;
		this.message = message;
	}
	
	private ResponseModel(Builder builder) {
		this.status = builder.status;
		this.message = builder.message;
	}
	
	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public static class Builder{
		private Boolean status;
		private String message;
		
		public Builder() {}
		
		public Builder withStatus(Boolean status){
			this.status = status;
			return this;
		}
		
		public Builder withMessage(String message){
			this.message = message;
			return this;
		}
		
		public ResponseModel build() {
			return new ResponseModel(this);
		}
		
		
	}
}
