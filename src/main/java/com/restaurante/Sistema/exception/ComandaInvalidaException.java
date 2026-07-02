package com.restaurante.Sistema.exception;

public class ComandaInvalidaException extends RuntimeException {

	public ComandaInvalidaException(String mensaje) {
		super(mensaje);
	}
}
