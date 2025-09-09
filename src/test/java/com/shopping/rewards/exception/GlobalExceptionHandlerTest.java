package com.shopping.rewards.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.shopping.rewards.constants.ErrorCodes;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    @DisplayName("Should handle NotFoundException and return NOT_FOUND status")
    void testHandleNotFoundException() {
        String errorMessage = "Resource not found";
        NotFoundException exception = new NotFoundException(errorMessage);

        ResponseEntity<ApiException> response = globalExceptionHandler.handleNotFoundException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(errorMessage, response.getBody().getMessage());
        assertEquals(ErrorCodes.NOT_FOUND, response.getBody().getError());
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getBody().getStatus());
    }

    @Test
    @DisplayName("Should handle BadRequestException and return BAD_REQUEST status")
    void testHandleBadRequestException() {
        String errorMessage = "Invalid request";
        BadRequestException exception = new BadRequestException(errorMessage);

        ResponseEntity<ApiException> response = globalExceptionHandler.handleBadRequestException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(errorMessage, response.getBody().getMessage());
        assertEquals(ErrorCodes.BAD_REQUEST, response.getBody().getError());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
    }

    @Test
    @DisplayName("Should handle MethodArgumentNotValidException with field errors")
    void testHandleValidationExceptionWithFieldError() {
        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("objectName", "fieldName", "Field validation failed");

        when(exception.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.hasFieldErrors()).thenReturn(true);
        when(bindingResult.getFieldError()).thenReturn(fieldError);

        ResponseEntity<ApiException> response = globalExceptionHandler.handleValidationException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Field validation failed", response.getBody().getMessage());
        assertEquals(ErrorCodes.VALIDATION_ERROR, response.getBody().getError());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
    }

    @Test
    @DisplayName("Should handle MethodArgumentNotValidException with global errors")
    void testHandleValidationExceptionWithGlobalError() {
        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        ObjectError globalError = new ObjectError("objectName", "From Date must be before or equal to To Date");

        when(exception.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.hasFieldErrors()).thenReturn(false);
        when(bindingResult.hasGlobalErrors()).thenReturn(true);
        when(bindingResult.getGlobalError()).thenReturn(globalError);

        ResponseEntity<ApiException> response = globalExceptionHandler.handleValidationException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("From Date must be before or equal to To Date", response.getBody().getMessage());
        assertEquals(ErrorCodes.VALIDATION_ERROR, response.getBody().getError());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
    }

    @Test
    @DisplayName("Should handle MethodArgumentNotValidException with null field error")
    void testHandleValidationExceptionWithNullFieldError() {
        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);

        when(exception.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.hasFieldErrors()).thenReturn(true);
        when(bindingResult.getFieldError()).thenReturn(null);

        ResponseEntity<ApiException> response = globalExceptionHandler.handleValidationException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Validation failed", response.getBody().getMessage());
        assertEquals(ErrorCodes.VALIDATION_ERROR, response.getBody().getError());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
    }

    @Test
    @DisplayName("Should handle MethodArgumentNotValidException with null global error")
    void testHandleValidationExceptionWithNullGlobalError() {
        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);

        when(exception.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.hasFieldErrors()).thenReturn(false);
        when(bindingResult.hasGlobalErrors()).thenReturn(true);
        when(bindingResult.getGlobalError()).thenReturn(null);

        ResponseEntity<ApiException> response = globalExceptionHandler.handleValidationException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Validation failed", response.getBody().getMessage());
        assertEquals(ErrorCodes.VALIDATION_ERROR, response.getBody().getError());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
    }

    @Test
    @DisplayName("Should handle MethodArgumentNotValidException with no errors")
    void testHandleValidationExceptionWithNoErrors() {
        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);

        when(exception.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.hasFieldErrors()).thenReturn(false);
        when(bindingResult.hasGlobalErrors()).thenReturn(false);

        ResponseEntity<ApiException> response = globalExceptionHandler.handleValidationException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Validation failed", response.getBody().getMessage());
        assertEquals(ErrorCodes.VALIDATION_ERROR, response.getBody().getError());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
    }

    @Test
    @DisplayName("Should handle HttpMessageNotReadableException and return BAD_REQUEST status")
    void testHandleHttpMessageNotReadableException() {
        HttpMessageNotReadableException exception = mock(HttpMessageNotReadableException.class);
        when(exception.getMessage()).thenReturn("JSON parse error");

        ResponseEntity<ApiException> response = globalExceptionHandler.handleHttpMessageNotReadableException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Invalid request body format", response.getBody().getMessage());
        assertEquals(ErrorCodes.BAD_REQUEST, response.getBody().getError());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
    }

    @Test
    @DisplayName("Should handle DataAccessException and return INTERNAL_SERVER_ERROR status")
    void testHandleDatabaseException() {
        String errorMessage = "Database connection failed";
        DataAccessException exception = new DataAccessException(errorMessage) {};

        ResponseEntity<ApiException> response = globalExceptionHandler.handleDatabaseException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(errorMessage, response.getBody().getMessage());
        assertEquals(ErrorCodes.INTERNAL_SERVER_ERROR, response.getBody().getError());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getBody().getStatus());
    }

    @Test
    @DisplayName("Should handle RuntimeException and return INTERNAL_SERVER_ERROR status")
    void testHandleRuntimeException() {
        String errorMessage = "Runtime error occurred";
        RuntimeException exception = new RuntimeException(errorMessage);

        ResponseEntity<ApiException> response = globalExceptionHandler.handleRuntimeException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(errorMessage, response.getBody().getMessage());
        assertEquals(ErrorCodes.INTERNAL_SERVER_ERROR, response.getBody().getError());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getBody().getStatus());
    }

    @Test
    @DisplayName("Should handle generic Exception and return INTERNAL_SERVER_ERROR status")
    void testHandleGenericException() {
        String errorMessage = "Unexpected error";
        Exception exception = mock(Exception.class);
        when(exception.getMessage()).thenReturn(errorMessage);

        ResponseEntity<ApiException> response = globalExceptionHandler.handleGenericException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("An unexpected error occurred", response.getBody().getMessage());
        assertEquals(ErrorCodes.INTERNAL_SERVER_ERROR, response.getBody().getError());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getBody().getStatus());
    }

    @Test
    @DisplayName("Should handle MethodArgumentNotValidException with field error having null message")
    void testHandleValidationExceptionWithFieldErrorNullMessage() {
        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("objectName", "fieldName", null);

        when(exception.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.hasFieldErrors()).thenReturn(true);
        when(bindingResult.getFieldError()).thenReturn(fieldError);

        ResponseEntity<ApiException> response = globalExceptionHandler.handleValidationException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Validation failed", response.getBody().getMessage());
        assertEquals(ErrorCodes.VALIDATION_ERROR, response.getBody().getError());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
    }

    @Test
    @DisplayName("Should handle MethodArgumentNotValidException with global error having null message")
    void testHandleValidationExceptionWithGlobalErrorNullMessage() {
        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        ObjectError globalError = new ObjectError("objectName", null);

        when(exception.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.hasFieldErrors()).thenReturn(false);
        when(bindingResult.hasGlobalErrors()).thenReturn(true);
        when(bindingResult.getGlobalError()).thenReturn(globalError);

        ResponseEntity<ApiException> response = globalExceptionHandler.handleValidationException(exception);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Validation failed", response.getBody().getMessage());
        assertEquals(ErrorCodes.VALIDATION_ERROR, response.getBody().getError());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
    }
}
