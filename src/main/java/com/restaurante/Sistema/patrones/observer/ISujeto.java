package com.restaurante.Sistema.patrones.observer;

public interface ISujeto {

	void registrar(IObservador observador);

	void quitar(IObservador observador);

	void notificar(EventoCambioEstado evento);
}
