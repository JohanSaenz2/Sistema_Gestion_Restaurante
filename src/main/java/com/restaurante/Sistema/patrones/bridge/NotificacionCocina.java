package com.restaurante.Sistema.patrones.bridge;

public class NotificacionCocina extends Notificacion {

	public NotificacionCocina(CanalNotificacion canal) {
		super(canal);
	}

	@Override
	public void notificar(String mensaje) {
		canal.enviar("[COCINA] " + mensaje);
	}
}
