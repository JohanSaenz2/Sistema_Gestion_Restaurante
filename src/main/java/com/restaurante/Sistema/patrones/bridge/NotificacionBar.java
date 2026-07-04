package com.restaurante.Sistema.patrones.bridge;

public class NotificacionBar extends Notificacion {

	public NotificacionBar(CanalNotificacion canal) {
		super(canal);
	}

	@Override
	public void notificar(String mensaje) {
		canal.enviar("[BAR] " + mensaje);
	}
}
