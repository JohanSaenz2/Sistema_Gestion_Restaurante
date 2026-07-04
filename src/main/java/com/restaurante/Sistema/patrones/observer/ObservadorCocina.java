package com.restaurante.Sistema.patrones.observer;

import org.springframework.stereotype.Component;

import com.restaurante.Sistema.Enum.Estacion;
import com.restaurante.Sistema.patrones.bridge.CanalNotificacion;
import com.restaurante.Sistema.patrones.bridge.Notificacion;
import com.restaurante.Sistema.patrones.bridge.NotificacionCocina;

@Component
public class ObservadorCocina implements IObservador {

	private final Notificacion notificacion;

	public ObservadorCocina(CanalNotificacion canal) {
		this.notificacion = new NotificacionCocina(canal);
	}

	@Override
	public void actualizar(EventoCambioEstado evento) {
		if (evento.estacion() == Estacion.cocina) {
			notificacion.notificar("Ítem " + evento.detalleId() + " (comanda " + evento.comandaId()
					+ ") cambió a " + evento.nuevoEstado());
		}
	}
}
