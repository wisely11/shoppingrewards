package com.shopping.rewards.exception;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.shopping.rewards.constants.ErrorCodes;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ApiException> handleNotFoundException(NotFoundException ex) {
		ApiException apiException = new ApiException(ex.getMessage(), ErrorCodes.NOT_FOUND,
				HttpStatus.NOT_FOUND.value());
		logger.error("Error encountered with message {}",ex.getMessage());
		return new ResponseEntity<>(apiException, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ApiException> handleBadRequestException(BadRequestException ex) {
		ApiException apiException = new ApiException(ex.getMessage(), ErrorCodes.BAD_REQUEST,
				HttpStatus.BAD_REQUEST.value());
		logger.error("Error encountered with message {}",ex.getMessage());
		return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiException> handleValidationException(MethodArgumentNotValidException ex) {
		String errorMessage = "Validation failed";
		
		// Check if there are field errors
		if (ex.getBindingResult().hasFieldErrors()) {
			var fieldError = ex.getBindingResult().getFieldError();
			if (fieldError != null && fieldError.getDefaultMessage() != null) {
				errorMessage = fieldError.getDefaultMessage();
			}
		} else if (ex.getBindingResult().hasGlobalErrors()) { 
			var globalError = ex.getBindingResult().getGlobalError();
			if (globalError != null && globalError.getDefaultMessage() != null) {
				errorMessage = globalError.getDefaultMessage();
			}
		}
		
		ApiException apiException = new ApiException(errorMessage, ErrorCodes.VALIDATION_ERROR,
				HttpStatus.BAD_REQUEST.value());
		logger.error("Validation error encountered with message: {}", errorMessage);
		return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ApiException> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
		String errorMessage = "Invalid request body format";
		ApiException apiException = new ApiException(errorMessage, ErrorCodes.BAD_REQUEST,
				HttpStatus.BAD_REQUEST.value());
		logger.error("HTTP message not readable: {}",ex.getMessage());
		return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ApiException> handleDatabaseException(DataAccessException ex) {
		String errorMessage = ex.getMessage();
		ApiException apiException = new ApiException(errorMessage, ErrorCodes.INTERNAL_SERVER_ERROR,
				HttpStatus.INTERNAL_SERVER_ERROR.value()); 
		logger.error("Error encountered with message {}",errorMessage);

        return new ResponseEntity<>(apiException, HttpStatus.INTERNAL_SERVER_ERROR);
    }

	@ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiException> handleRuntimeException(RuntimeException ex) {
		String errorMessage = ex.getMessage();
		ApiException apiException = new ApiException(errorMessage, ErrorCodes.INTERNAL_SERVER_ERROR,
				HttpStatus.INTERNAL_SERVER_ERROR.value()); 
		logger.error("Runtime error encountered with message {}",errorMessage);

        return new ResponseEntity<>(apiException, HttpStatus.INTERNAL_SERVER_ERROR);
    }

	@ExceptionHandler(Exception.class)
    public ResponseEntity<ApiException> handleGenericException(Exception ex) {
		String errorMessage = "An unexpected error occurred";
		ApiException apiException = new ApiException(errorMessage, ErrorCodes.INTERNAL_SERVER_ERROR,
				HttpStatus.INTERNAL_SERVER_ERROR.value()); 
		logger.error("Unexpected error encountered: {}",ex.getMessage(), ex);

        return new ResponseEntity<>(apiException, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
