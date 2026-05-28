package com.Twg.SpringBoot.Library.ExceptionHandler;

public class ResourceNotFoundException extends RuntimeException
{
	public ResourceNotFoundException() {}
	public ResourceNotFoundException(String message) {
        super(message);
    }

}
