package com.restaurante.Sistema.patrones.bridge;

import org.springframework.messaging.simp.SimpMessagingTemplate;

public class CanalWebSocket implements CanalNotificacion {

	private final SimpMessagingTemplate template;
	private final String destino;

	public CanalWebSocket(SimpMessagingTemplate template, String destino) {
		this.template = template;
		this.destino = destino;
	}

	@Override
	public void enviar(String mensaje) {
		template.convertAndSend(destino, mensaje);
	}

	public void enviarObjeto(Object payload) {
		template.convertAndSend(destino, payload);
	}
}
