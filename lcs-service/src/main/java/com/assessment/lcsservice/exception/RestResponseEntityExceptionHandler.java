package com.assessment.lcsservice.exception;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.core.JsonParseException;
import com.networknt.schema.ValidationMessage;

@ControllerAdvice
public class RestResponseEntityExceptionHandler {
	/**
	 * This method handles schema validation exceptions if thrown any.
	 * 
	 * @param ex: SchemaValidationException
	 * @return ResponseEntity object with errorMessage map.
	 */
	@ExceptionHandler(SchemaValidationException.class)
	public ResponseEntity<HashMap<String, Object>> handleSchemaValidationExceptions(SchemaValidationException ex) {
		List<String> messages = ex.getValidationMessages().stream().map(ValidationMessage::getMessage)
				.collect(Collectors.toList());

		HashMap<String, Object> errorMap = new HashMap<String, Object>();
		errorMap.put("message", "Request object validation failed!");
		errorMap.put("details", messages);

		return ResponseEntity.badRequest().body(errorMap);
	}
	
	
	/**
	 * This Method handles MethodNotSupportedException
	 * 
	 * @param ex: JsonParseException
	 * @return ResponseEntity object with errorMessage map.
	 */
	
	@ExceptionHandler(value = { HttpRequestMethodNotSupportedException.class })
	public ResponseEntity<HashMap<String, Object>> handleMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {

		HashMap<String, Object> errorMap = new HashMap<String, Object>();
		errorMap.put("message", "This method is not supported");
		return ResponseEntity.status(405).body(errorMap);
	}
	

	/**
	 * This Method handles json parsing exceptions.
	 * 
	 * @param ex: JsonParseException
	 * @return ResponseEntity object with errorMessage map.
	 */

	@ExceptionHandler(value = { JsonParseException.class })
	public ResponseEntity<HashMap<String, Object>> handleJsonParseException(JsonParseException ex) {

		HashMap<String, Object> errorMap = new HashMap<String, Object>();
		errorMap.put("message", "This is not a JSON object");
		return ResponseEntity.badRequest().body(errorMap);
	}

	/**
	 * This Method handles all kind exceptions which are not handled.
	 * 
	 * @param ex: Exception
	 * @return ResponseEntity object with errorMessage map.
	 */

	@ExceptionHandler(value = { Exception.class })
	public ResponseEntity<HashMap<String, Object>> handleAllExceptios(Exception ex) {

		HashMap<String, Object> errorMap = new HashMap<String, Object>();
		errorMap.put("message", "Technical excpetion occured!");
		return ResponseEntity.internalServerError().body(errorMap);
	}
}
