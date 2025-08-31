package com.shopping.rewards.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.shopping.rewards.constants.ErrorCodes;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ApiException> handleNotFoundException(NotFoundException ex) {
		ApiException apiException = new ApiException(ex.getMessage(), ErrorCodes.NOT_FOUND,
				HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<>(apiException, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ApiException> handleBadRequestException(BadRequestException ex) {
		ApiException apiException = new ApiException(ex.getMessage(), ErrorCodes.BAD_REQUEST,
				HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiException> handleValidationException(MethodArgumentNotValidException ex) {
		String errorMessage = ex.getBindingResult().getFieldError().getDefaultMessage();
		ApiException apiException = new ApiException(errorMessage, ErrorCodes.VALIDATION_ERROR,
				HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiException> handleGenericException(Exception ex) {
		ApiException apiException = new ApiException(ex.getMessage(), ErrorCodes.INTERNAL_SERVER_ERROR,
				HttpStatus.INTERNAL_SERVER_ERROR.value());
		return new ResponseEntity<>(apiException, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
