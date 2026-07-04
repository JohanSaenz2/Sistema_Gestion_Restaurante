package com.restaurante.Sistema.patrones.state;

import com.restaurante.Sistema.Enum.EstadoItem;

public class Listo implements IEstadoItem {

	private ContextoItem contexto;

	@Override
	public void setContexto(ContextoItem contexto) {
		this.contexto = contexto;
	}

	@Override
	public void enviarACocina() {
		throw new TransicionInvalidaException("No se puede enviar a cocina un ítem que ya está listo");
	}

	@Override
	public void marcarListo() {
		throw new TransicionInvalidaException("El ítem ya está listo");
	}

	@Override
	public void entregar() {
		contexto.setEstado(new Entregado());
	}

	@Override
	public void cancelar() {
		throw new TransicionInvalidaException("No se puede cancelar un ítem que ya está listo");
	}

	@Override
	public EstadoItem getEstadoItem() {
		return EstadoItem.listo;
	}
}
