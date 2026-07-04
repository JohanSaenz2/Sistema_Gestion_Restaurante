package com.restaurante.Sistema.patrones.observer;

import org.springframework.stereotype.Component;

import com.restaurante.Sistema.Enum.EstadoItem;
import com.restaurante.Sistema.patrones.bridge.CanalNotificacion;
import com.restaurante.Sistema.patrones.bridge.Notificacion;
import com.restaurante.Sistema.patrones.bridge.NotificacionMesero;

@Component
public class ObservadorMesero implements IObservador {

	private final Notificacion notificacion;

	public ObservadorMesero(CanalNotificacion canal) {
		this.notificacion = new NotificacionMesero(canal);
	}

	@Override
	public void actualizar(EventoCambioEstado evento) {
		EstadoItem estado = evento.nuevoEstado();
		if (estado == EstadoItem.listo || estado == EstadoItem.entregado) {
			notificacion.notificar("Ítem " + evento.detalleId() + " (comanda " + evento.comandaId()
					+ ") está " + estado);
		}
	}
}
