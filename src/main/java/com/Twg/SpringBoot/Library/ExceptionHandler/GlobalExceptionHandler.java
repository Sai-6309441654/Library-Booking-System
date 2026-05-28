package com.Twg.SpringBoot.Library.ExceptionHandler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.swagger.v3.oas.annotations.Hidden;

@RestControllerAdvice
@Hidden // <--- Add this! Tells Swagger to skip scanning this bean
public class GlobalExceptionHandler 
{
	@ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) // Sets the actual HTTP Network Status to 404
    public ErrorResponse handleResourceNotFoundException(ResourceNotFoundException ex) {
        ErrorResponse er = new ErrorResponse();
        er.setTimestamp(LocalDateTime.now());
        er.setStatus(HttpStatus.NOT_FOUND.value());
        er.setError(HttpStatus.NOT_FOUND.getReasonPhrase());
        er.setMessage(ex.getMessage());
        return er;    
    }

    // 2. Handle NullPointerExceptions (500 Internal Server Error)
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // Sets Network Status to 500
    public ErrorResponse handleNullPointerException(NullPointerException ex) {
        ErrorResponse er = new ErrorResponse();
        er.setTimestamp(LocalDateTime.now());
        er.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        er.setError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        er.setMessage("An unexpected null pointer error occurred."); // Avoid exposing raw NPE details to the client
        return er;    
    }

    // 3. Catch-all for any other unhandled exceptions (500 Internal Server Error)
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // Sets Network Status to 500
    public ErrorResponse handleGenericException(Exception ex) {
        ErrorResponse er = new ErrorResponse();
        er.setTimestamp(LocalDateTime.now());
        er.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        er.setError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        er.setMessage(ex.getMessage());
        return er;        
    }
}
	

