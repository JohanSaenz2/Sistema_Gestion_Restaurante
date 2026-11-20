package com.restaurante.Sistema.dto;

import java.math.BigDecimal;

import com.restaurante.Sistema.Enum.TipoComprobante;

public record GenerarComprobanteRequest(Long comandaId, TipoComprobante tipo, Long clienteId,
		BigDecimal ivaPorcentaje) {
}