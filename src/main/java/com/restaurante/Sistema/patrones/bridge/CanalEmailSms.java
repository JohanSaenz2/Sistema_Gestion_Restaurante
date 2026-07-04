package com.restaurante.Sistema.patrones.bridge;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CanalEmailSms implements CanalNotificacion {

	private static final Logger log = LoggerFactory.getLogger(CanalEmailSms.class);

	@Override
	public void enviar(String mensaje) {
		log.info("[EMAIL/SMS] enviando -> {}", mensaje);
	}
}
