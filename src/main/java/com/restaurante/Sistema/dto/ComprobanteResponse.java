package com.restaurante.Sistema.dto;

import com.restaurante.Sistema.Enum.TipoComprobante;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ComprobanteResponse(
    Long id,
    Long comandaId,
    Long clienteId,
    String nombreCliente,
    String identificacionCliente,
    TipoComprobante tipoComprobante,
    String numero,
    BigDecimal subtotal,
    BigDecimal descuento,
    BigDecimal iva,
    BigDecimal propina,
    BigDecimal total,
    LocalDateTime fechaEmision
) {}