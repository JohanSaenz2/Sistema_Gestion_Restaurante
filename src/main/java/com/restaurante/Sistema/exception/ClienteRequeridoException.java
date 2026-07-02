package com.restaurante.Sistema.exception;

public class ClienteRequeridoException extends RuntimeException {

	public ClienteRequeridoException(String mensaje) {
		super(mensaje);
	}
}
