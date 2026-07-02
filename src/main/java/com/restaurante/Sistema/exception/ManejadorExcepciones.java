package com.restaurante.Sistema.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ManejadorExcepciones {

	@ExceptionHandler({ ClienteRequeridoException.class, ComandaInvalidaException.class,
			IllegalArgumentException.class })
	public ResponseEntity<Map<String, String>> manejarReglasDeNegocio(RuntimeException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", ex.getMessage()));
	}
}
