package com.restaurante.Sistema.patrones.state;

import com.restaurante.Sistema.Enum.EstadoItem;

public class EnPreparacion implements IEstadoItem {

	private ContextoItem contexto;

	@Override
	public void setContexto(ContextoItem contexto) {
		this.contexto = contexto;
	}

	@Override
	public void enviarACocina() {
		throw new TransicionInvalidaException("El ítem ya está en preparación");
	}

	@Override
	public void marcarListo() {
		contexto.setEstado(new Listo());
	}

	@Override
	public void entregar() {
		throw new TransicionInvalidaException("No se puede entregar un ítem que aún está en preparación");
	}

	@Override
	public void cancelar() {
		contexto.setEstado(new Cancelado());
	}

	@Override
	public EstadoItem getEstadoItem() {
		return EstadoItem.en_preparacion;
	}
}
