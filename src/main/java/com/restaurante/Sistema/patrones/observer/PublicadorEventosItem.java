package com.restaurante.Sistema.patrones.observer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class PublicadorEventosItem implements ISujeto {

	private final List<IObservador> observadores = new ArrayList<>();

	public PublicadorEventosItem(List<IObservador> observadoresIniciales) {
		observadoresIniciales.forEach(this::registrar);
	}

	@Override
	public void registrar(IObservador observador) {
		observadores.add(observador);
	}

	@Override
	public void quitar(IObservador observador) {
		observadores.remove(observador);
	}

	@Override
	public void notificar(EventoCambioEstado evento) {
		observadores.forEach(observador -> observador.actualizar(evento));
	}
}
