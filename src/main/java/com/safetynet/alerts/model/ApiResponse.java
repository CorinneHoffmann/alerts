package com.safetynet.alerts.model;

import java.util.List;

import org.springframework.http.*;

public class ApiResponse {

	private String message;
	private HttpStatus httpStatus;
	private List<String> errors;

	public ApiResponse(String message) {
		this.message = message;
	}

	public ApiResponse(HttpStatus httpStatus, String message) {
		this.message = message;
		this.httpStatus = httpStatus;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public String getMessage() {
		return message;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setHttpStatus(HttpStatus httpstatus) {
		this.httpStatus = httpstatus;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

}
