package com.restaurante.Sistema.patrones.bridge;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CanalImpresionTicket implements CanalNotificacion {

	private static final Logger log = LoggerFactory.getLogger(CanalImpresionTicket.class);

	@Override
	public void enviar(String mensaje) {
		log.info("[IMPRESORA] --- ticket ---\n  {}\n  ------------------------", mensaje);
	}
}
