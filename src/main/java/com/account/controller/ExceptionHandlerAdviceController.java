package com.account.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.account.exception.AccountServiceException;
import com.account.model.response.ErrorResponse;

@ControllerAdvice
public class ExceptionHandlerAdviceController {

	Logger logger = LoggerFactory.getLogger(ExceptionHandlerAdviceController.class);
	ErrorResponse errorResponse;

	@ExceptionHandler
	ResponseEntity<Object> accountServiceException(AccountServiceException ex) {
		logger.error("AccountServiceException :" + ex.getMessage());
		errorResponse = new ErrorResponse();
		errorResponse.setMessage(ex.getMessage());
		errorResponse.setErrorCode(HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler
	ResponseEntity<Object> exception(Exception ex) {
		logger.error("Exception :" + ex.getMessage());
		errorResponse = new ErrorResponse();
		errorResponse.setMessage("Exception : " + ex);
		errorResponse.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
