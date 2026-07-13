package com.restaurante.Sistema.patrones.observer;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.restaurante.Sistema.Enum.Estacion;
import com.restaurante.Sistema.dto.AvisoItem;
import com.restaurante.Sistema.patrones.bridge.CanalNotificacion;
import com.restaurante.Sistema.patrones.bridge.CanalWebSocket;
import com.restaurante.Sistema.patrones.bridge.Notificacion;
import com.restaurante.Sistema.patrones.bridge.NotificacionBar;

@Component
public class ObservadorBar implements IObservador {

	private final Notificacion consola;
	private final CanalWebSocket ws;

	public ObservadorBar(CanalNotificacion canal, SimpMessagingTemplate template) {
		this.consola = new NotificacionBar(canal);
		this.ws = new CanalWebSocket(template, "/topic/bar");
	}

	@Override
	public void actualizar(EventoCambioEstado evento) {
		if (evento.estacion() == Estacion.bar) {
			consola.notificar("Ítem " + evento.detalleId() + " (comanda " + evento.comandaId()
					+ ") cambió a " + evento.nuevoEstado());
			ws.enviarObjeto(new AvisoItem(evento.detalleId(), evento.comandaId(), evento.nombreProducto(),
					evento.cantidad(), evento.nuevoEstado(), evento.estacion()));
		}
	}
}
