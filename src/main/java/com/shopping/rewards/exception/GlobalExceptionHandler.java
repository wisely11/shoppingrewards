package com.shopping.rewards.exception;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
		String errorMessage = ex.getBindingResult().getFieldError().getDefaultMessage();
		ApiException apiException = new ApiException(errorMessage, ErrorCodes.VALIDATION_ERROR,
				HttpStatus.BAD_REQUEST.value());
		logger.error("Error encountered with message {}",errorMessage);
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
}
