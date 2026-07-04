package com.restaurante.Sistema.patrones.bridge;

public abstract class Notificacion {

	protected final CanalNotificacion canal;

	protected Notificacion(CanalNotificacion canal) {
		this.canal = canal;
	}

	public abstract void notificar(String mensaje);
}
