package com.homerios.msnegociogestioncurso.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.homerios.msnegociogestioncurso.dto.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ReglaNegocioException.class)
	public ResponseEntity<ErrorResponse> reglaNegocio(ReglaNegocioException ex) {
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
				.body(ErrorResponse.of(HttpStatus.UNPROCESSABLE_ENTITY.value(), ex.getMessage()));
	}
}
