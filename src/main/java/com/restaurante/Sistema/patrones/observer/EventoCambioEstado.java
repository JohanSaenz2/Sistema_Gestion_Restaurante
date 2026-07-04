package com.restaurante.Sistema.patrones.observer;

import com.restaurante.Sistema.Enum.Estacion;
import com.restaurante.Sistema.Enum.EstadoItem;

public record EventoCambioEstado(Long detalleId, Long comandaId, EstadoItem nuevoEstado, Estacion estacion) {
}
