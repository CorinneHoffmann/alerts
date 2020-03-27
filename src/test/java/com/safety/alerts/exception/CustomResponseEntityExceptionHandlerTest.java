package com.safety.alerts.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.safetynet.alerts.exception.CustomResponseEntityExceptionHandler;
import com.safetynet.alerts.exception.DaoException;

class CustomResponseEntityExceptionHandlerTest {

	private static CustomResponseEntityExceptionHandler customResponseEntityExceptionHandler;
	private static HttpStatus httpStatus;

	@BeforeAll
	private static void setUp() {
		customResponseEntityExceptionHandler = new CustomResponseEntityExceptionHandler();

	}

	@Test
	public void whenhandleNotFoundException() {
		String message = "objet absent des listes";
		DaoException ex = new DaoException(message);

		assertEquals(HttpStatus.NOT_FOUND,
				customResponseEntityExceptionHandler.handleNotFoundException(ex).getHttpStatus());
		assertEquals(message, customResponseEntityExceptionHandler.handleNotFoundException(ex).getMessage());

	}

	@Test
	public void whenhandleRuntimeException() {
		RuntimeException ex = new RuntimeException();

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,
				customResponseEntityExceptionHandler.handleRuntimeException(ex).getHttpStatus());
	}
}
