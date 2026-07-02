package com.restaurante.Sistema.dto;

import com.restaurante.Sistema.Enum.TipoComprobante;

public record GenerarComprobanteRequest(Long comandaId, TipoComprobante tipo, Long clienteId) {
}
