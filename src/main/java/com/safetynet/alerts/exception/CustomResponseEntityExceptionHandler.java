package com.safetynet.alerts.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.safetynet.alerts.model.ApiResponse;

@RestControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	Logger logger = LoggerFactory.getLogger(CustomResponseEntityExceptionHandler.class);

	@ExceptionHandler(DaoException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ApiResponse handleNotFoundException(DaoException e) {
		logger.error("PERSON NOT_FOUND");
		ApiResponse apiresponse = new ApiResponse(HttpStatus.NOT_FOUND , e.getMessage());
		return apiresponse;
	}

	@ExceptionHandler(ControllerPersonException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.NOT_MODIFIED)
	public ApiResponse handleNotFoundException(ControllerPersonException e) {
		logger.error("PARAMETRE NOT INFORMED");
		ApiResponse apiresponse = new ApiResponse(HttpStatus.NOT_MODIFIED , e.getMessage());
		return apiresponse;
	}
	
	@ExceptionHandler(RuntimeException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ApiResponse handleRuntimeException(RuntimeException e) {
		logger.error("INTERNAL_SERVER_ERROR");
		ApiResponse apiresponse = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR , e.getMessage());
		return apiresponse;
	}
}
