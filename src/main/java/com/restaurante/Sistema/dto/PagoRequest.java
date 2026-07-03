package com.restaurante.Sistema.dto;

import java.math.BigDecimal;

import com.restaurante.Sistema.Enum.MetodoPago;

public record PagoRequest(Long comprobanteId, MetodoPago metodo, BigDecimal propina) {

}