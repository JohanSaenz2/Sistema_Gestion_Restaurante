package com.restaurante.Sistema.dto;

import com.restaurante.Sistema.Enum.EstadoMesa;

public record MesaResponse(Long mesaId, Integer numero, EstadoMesa estado) {
}
