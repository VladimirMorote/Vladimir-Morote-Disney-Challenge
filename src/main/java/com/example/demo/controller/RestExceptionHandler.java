package com.example.demo.controller;

import java.util.Arrays;

import com.example.demo.dto.ApiErrorDTO;
import com.example.demo.exception.ParamNotFound;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;



@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	// Definimos un EXCEPTION HANDLER
	@ExceptionHandler(value = {ParamNotFound.class})
	protected ResponseEntity<Object> handleParamNotFound(RuntimeException ex, WebRequest request) {
		// Instanciamos y construimos una DTO
		ApiErrorDTO errorDTO = new ApiErrorDTO (HttpStatus.BAD_REQUEST, ex.getMessage(), Arrays.asList("Param Not Found"));
		return handleExceptionInternal(ex, errorDTO, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
}
