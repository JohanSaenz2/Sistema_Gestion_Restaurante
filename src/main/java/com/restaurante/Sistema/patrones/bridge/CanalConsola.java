package com.restaurante.Sistema.patrones.bridge;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class CanalConsola implements CanalNotificacion {

	private static final Logger log = LoggerFactory.getLogger(CanalConsola.class);

	@Override
	public void enviar(String mensaje) {
		log.info(mensaje);
	}
}
