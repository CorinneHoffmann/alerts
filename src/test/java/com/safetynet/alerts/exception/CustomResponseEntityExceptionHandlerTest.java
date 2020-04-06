package com.safetynet.alerts.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.yaml.snakeyaml.parser.ParserException;

import com.safetynet.alerts.AlertsApplication;
import com.safetynet.alerts.exception.CustomResponseEntityExceptionHandler;
import com.safetynet.alerts.exception.DaoException;
import com.safetynet.alerts.model.ApiResponse;


@SpringBootTest(classes = AlertsApplication.class)
@ContextConfiguration
@ActiveProfiles("test")
class CustomResponseEntityExceptionHandlerTest {

	private static CustomResponseEntityExceptionHandler customResponseEntityExceptionHandler;
	private static HttpStatus httpStatus;

	@BeforeAll
	private static void setUp() {
		customResponseEntityExceptionHandler = new CustomResponseEntityExceptionHandler();

	}

	@Test
	public void whenHandleNotFoundException() {
		String message = "objet absent des listes";
		DaoException ex = new DaoException(message);

		assertEquals(HttpStatus.NOT_FOUND,
				customResponseEntityExceptionHandler.handleNotFoundException(ex).getHttpStatus());
		assertEquals(message, customResponseEntityExceptionHandler.handleNotFoundException(ex).getMessage());

	}

	@Test
	public void whenHandleRuntimeException() {
		RuntimeException ex = new RuntimeException();

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,
				customResponseEntityExceptionHandler.handleRuntimeException(ex).getHttpStatus());
	}
	
	@Test
	public void whenHandleControllerException() {
		String message = "parametres URL absents";
		ControllerException ex = new ControllerException(message);

		assertEquals(HttpStatus.BAD_REQUEST,
				customResponseEntityExceptionHandler.handleControllerException(ex).getHttpStatus());
		assertEquals(message, customResponseEntityExceptionHandler.handleControllerException(ex).getMessage());

	}
	
	@Test
	public void whenHandleParseException() {
		String message = "JSON_ERROR_CONVERSION_DATE";
		int errorOffset=0;
		ParseException ex = new ParseException(message,errorOffset);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,
				customResponseEntityExceptionHandler.handleParseException(ex).getHttpStatus());
		assertEquals(message, customResponseEntityExceptionHandler.handleParseException(ex).getMessage());
	}
	
	@Test
	public void whenhandleFoundException() {
		String message = "OBJET_FOUND";
		DaoCreationException ex = new DaoCreationException(message);

		assertEquals(HttpStatus.FOUND,
				customResponseEntityExceptionHandler.handleFoundException(ex).getHttpStatus());
		assertEquals(message, customResponseEntityExceptionHandler.handleFoundException(ex).getMessage());
	}
}

