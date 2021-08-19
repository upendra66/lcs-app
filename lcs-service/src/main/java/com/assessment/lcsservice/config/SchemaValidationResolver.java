package com.assessment.lcsservice.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.assessment.lcsservice.exception.SchemaLoadingException;
import com.assessment.lcsservice.exception.SchemaValidationException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;

/**
 * 
 * @author uyadav509
 *
 */
public class SchemaValidationResolver implements HandlerMethodArgumentResolver {

	private final ObjectMapper objectMapper;
	private final ResourcePatternResolver resourcePatternResolver;
	private final Map<String, JsonSchema> schemaCache;

	public SchemaValidationResolver(ObjectMapper objectMapper, ResourcePatternResolver resourcePatternResolver) {
		this.objectMapper = objectMapper;
		this.resourcePatternResolver = resourcePatternResolver;
		this.schemaCache = new ConcurrentHashMap<>();
	}

	@Override
	public boolean supportsParameter(MethodParameter methodParameter) {
		return methodParameter.getParameterAnnotation(ValidSchema.class) != null;
	}

	/**
	 * This method first loads json schema and request payload then validates.
	 * Throws SchemaValidationException exceptions if validation fails.
	 */

	@Override
	public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer,
			NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {

		// get jsonSchema path
		String schemaPath = methodParameter.getParameterAnnotation(ValidSchema.class).value();

		// load jsonSchema

		JsonSchema schema = loadSchema(schemaPath);

		// parse JSON payload
		JsonNode json = objectMapper.readTree(getJsonPayload(nativeWebRequest));

		// validate JSON
		Set<ValidationMessage> validationResult = schema.validate(json);

		// Check if no validation error
		if (validationResult.isEmpty()) {
			return objectMapper.treeToValue(json, methodParameter.getParameterType());
		}
		throw new SchemaValidationException(validationResult);
	}

	/**
	 * This method loads Json Payload from request object.
	 * 
	 * @param nativeWebRequest
	 * @return String
	 * @throws IOException
	 */
	private String getJsonPayload(NativeWebRequest nativeWebRequest) throws IOException {
		HttpServletRequest httpServletRequest = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
		return StreamUtils.copyToString(httpServletRequest.getInputStream(), StandardCharsets.UTF_8);
	}

	/**
	 * This methods loads json schema from given path and retrieves JsonSchema
	 * object which will be used for validation against request object
	 * 
	 * @param schemaPath
	 * @return
	 * @throws SchemaLoadingException
	 */
	private JsonSchema loadSchema(String schemaPath) throws SchemaLoadingException {
		if (schemaCache.get(schemaPath) != null)
			return schemaCache.get(schemaPath);

		Resource resource = resourcePatternResolver.getResource(schemaPath);
		JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V201909);
		try {
			InputStream schemaStream = resource.getInputStream();
			JsonSchema jsonSchema = schemaFactory.getSchema(schemaStream);
			schemaCache.put(schemaPath, jsonSchema);
			return jsonSchema;
		} catch (Exception e) {
			throw new SchemaLoadingException("An error occurred while loading JSON Schema, path: " + schemaPath, e);
		}

	}
}
