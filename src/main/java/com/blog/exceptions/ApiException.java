package com.blog.exceptions;

/*
The ApiException class in the code is a custom exception that extends the RuntimeException class in Java. This is part
of an exception-handling mechanism to provide more meaningful error handling in the application.
 */

public class ApiException extends RuntimeException{
	
	public ApiException(String message) {
		super(message);
	}

	public ApiException() {
		super();
	}
	
	

}
