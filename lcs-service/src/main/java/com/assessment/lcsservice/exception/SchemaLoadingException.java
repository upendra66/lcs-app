package com.assessment.lcsservice.exception;

public class SchemaLoadingException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public SchemaLoadingException(String message) {
        super(message);
    }

    public SchemaLoadingException(String message, Throwable cause) {
        super(message, cause);
    }
}
