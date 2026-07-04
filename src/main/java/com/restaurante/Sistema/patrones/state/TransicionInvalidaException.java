package com.restaurante.Sistema.patrones.state;

public class TransicionInvalidaException extends RuntimeException {

	public TransicionInvalidaException(String mensaje) {
		super(mensaje);
	}
}
