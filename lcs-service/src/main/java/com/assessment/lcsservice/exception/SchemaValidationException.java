package com.assessment.lcsservice.exception;

import com.networknt.schema.ValidationMessage;

import java.util.Set;

public class SchemaValidationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private final Set<ValidationMessage> validationMessages;

	public SchemaValidationException(Set<ValidationMessage> validationMessages) {
		super("Request object validation failed: " + validationMessages);
		this.validationMessages = validationMessages;
	}

	public Set<ValidationMessage> getValidationMessages() {
		return validationMessages;
	}
}
