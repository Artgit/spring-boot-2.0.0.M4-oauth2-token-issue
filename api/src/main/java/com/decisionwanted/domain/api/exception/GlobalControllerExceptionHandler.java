package com.decisionwanted.domain.api.exception;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.decisionwanted.domain.api.dto.exception.ResponseError;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

	static final Logger logger = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

	private static final String ERROR = "error";

	@ExceptionHandler
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String, ResponseError> handleIllegalArgumentException(IllegalArgumentException e, HttpServletRequest request, HttpServletResponse response) throws IOException {
		logger.debug("API error", e);
		return createResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
	}

	@ExceptionHandler
	@ResponseBody
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public Map<String, ResponseError> handleAccessDeniedException(AccessDeniedException e, HttpServletRequest request, HttpServletResponse response) throws IOException {
		logger.debug("API error", e);
		return createResponseError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
	}

	@ExceptionHandler
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String, ResponseError> handleException(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
		logger.debug("API error", e);
		return createResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
	}

	protected Map<String, ResponseError> createResponseError(int httpStatus, String message) {
		Map<String, ResponseError> responseError = new HashMap<>();
		responseError.put(ERROR, new ResponseError(httpStatus, message));
		return responseError;
	}

}