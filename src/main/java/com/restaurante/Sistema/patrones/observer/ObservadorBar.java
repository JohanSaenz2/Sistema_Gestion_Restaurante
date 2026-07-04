package com.restaurante.Sistema.patrones.observer;

import org.springframework.stereotype.Component;

import com.restaurante.Sistema.Enum.Estacion;
import com.restaurante.Sistema.patrones.bridge.CanalNotificacion;
import com.restaurante.Sistema.patrones.bridge.Notificacion;
import com.restaurante.Sistema.patrones.bridge.NotificacionBar;

@Component
public class ObservadorBar implements IObservador {

	private final Notificacion notificacion;

	public ObservadorBar(CanalNotificacion canal) {
		this.notificacion = new NotificacionBar(canal);
	}

	@Override
	public void actualizar(EventoCambioEstado evento) {
		if (evento.estacion() == Estacion.bar) {
			notificacion.notificar("Ítem " + evento.detalleId() + " (comanda " + evento.comandaId()
					+ ") cambió a " + evento.nuevoEstado());
		}
	}
}
