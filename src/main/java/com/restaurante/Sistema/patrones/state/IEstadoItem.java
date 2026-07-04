package com.restaurante.Sistema.patrones.state;

import com.restaurante.Sistema.Enum.EstadoItem;

public interface IEstadoItem {

	void enviarACocina();

	void marcarListo();

	void entregar();

	void cancelar();

	EstadoItem getEstadoItem();

	void setContexto(ContextoItem contexto);
}
