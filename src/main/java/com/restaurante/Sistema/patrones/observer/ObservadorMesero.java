package com.restaurante.Sistema.patrones.observer;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.restaurante.Sistema.Enum.EstadoItem;
import com.restaurante.Sistema.dto.AvisoItem;
import com.restaurante.Sistema.patrones.bridge.CanalNotificacion;
import com.restaurante.Sistema.patrones.bridge.CanalWebSocket;
import com.restaurante.Sistema.patrones.bridge.Notificacion;
import com.restaurante.Sistema.patrones.bridge.NotificacionMesero;

@Component
public class ObservadorMesero implements IObservador {

	private final Notificacion consola;
	private final CanalWebSocket ws;

	public ObservadorMesero(CanalNotificacion canal, SimpMessagingTemplate template) {
		this.consola = new NotificacionMesero(canal);
		this.ws = new CanalWebSocket(template, "/topic/mesero");
	}

	@Override
	public void actualizar(EventoCambioEstado evento) {
		EstadoItem estado = evento.nuevoEstado();
		if (estado == EstadoItem.listo || estado == EstadoItem.entregado) {
			consola.notificar("Ítem " + evento.detalleId() + " (comanda " + evento.comandaId()
					+ ") está " + estado);
			ws.enviarObjeto(new AvisoItem(evento.detalleId(), evento.comandaId(), evento.nombreProducto(),
					evento.cantidad(), estado, evento.estacion()));
		}
	}
}
