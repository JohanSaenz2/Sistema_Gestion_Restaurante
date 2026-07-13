package com.restaurante.Sistema.patrones.observer;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.restaurante.Sistema.Enum.Estacion;
import com.restaurante.Sistema.dto.AvisoItem;
import com.restaurante.Sistema.patrones.bridge.CanalNotificacion;
import com.restaurante.Sistema.patrones.bridge.CanalWebSocket;
import com.restaurante.Sistema.patrones.bridge.Notificacion;
import com.restaurante.Sistema.patrones.bridge.NotificacionCocina;

@Component
public class ObservadorCocina implements IObservador {

	private final Notificacion consola;
	private final CanalWebSocket ws;

	public ObservadorCocina(CanalNotificacion canal, SimpMessagingTemplate template) {
		this.consola = new NotificacionCocina(canal);
		this.ws = new CanalWebSocket(template, "/topic/cocina");
	}

	@Override
	public void actualizar(EventoCambioEstado evento) {
		if (evento.estacion() == Estacion.cocina) {
			consola.notificar("Ítem " + evento.detalleId() + " (comanda " + evento.comandaId()
					+ ") cambió a " + evento.nuevoEstado());
			ws.enviarObjeto(new AvisoItem(evento.detalleId(), evento.comandaId(), evento.nombreProducto(),
					evento.cantidad(), evento.nuevoEstado(), evento.estacion()));
		}
	}
}
