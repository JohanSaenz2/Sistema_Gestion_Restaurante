package com.restaurante.Sistema.patrones.bridge;

public class NotificacionMesero extends Notificacion {

	public NotificacionMesero(CanalNotificacion canal) {
		super(canal);
	}

	@Override
	public void notificar(String mensaje) {
		canal.enviar("[MESERO] " + mensaje);
	}
}
