package com.restaurante.Sistema.dto;

import com.restaurante.Sistema.Enum.Estacion;
import com.restaurante.Sistema.Enum.EstadoItem;

public record AvisoItem(Long detalleId, Long comandaId, String nombreProducto, Integer cantidad,
		EstadoItem estado, Estacion estacion) {
}
