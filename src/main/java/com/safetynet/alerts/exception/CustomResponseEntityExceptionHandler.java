package com.safetynet.alerts.exception;

import java.text.ParseException;

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
		logger.error("OBJET_NOT_FOUND");
		ApiResponse apiresponse = new ApiResponse(HttpStatus.NOT_FOUND , e.getMessage());
		return apiresponse;
	}
	
	@ExceptionHandler(DaoCreationException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.FOUND)
	public ApiResponse handleFoundException(DaoCreationException e) {
		logger.error("OBJET_FOUND");
		ApiResponse apiresponse = new ApiResponse(HttpStatus.FOUND , e.getMessage());
		return apiresponse;
	}

	@ExceptionHandler(ControllerException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiResponse handleControllerException(ControllerException ex) {
		logger.error("PARAMETRE_NOT_INFORMED");
		ApiResponse apiresponse = new ApiResponse(HttpStatus.BAD_REQUEST , ex.getMessage());
		return apiresponse;
	}
	
	@ExceptionHandler(ParseException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ApiResponse handleParseException(ParseException ex) {
		logger.error("JSON_ERROR_CONVERSION_DATE");
		ApiResponse apiresponse = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR , "JSON_ERROR_CONVERSION_DATE");
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
