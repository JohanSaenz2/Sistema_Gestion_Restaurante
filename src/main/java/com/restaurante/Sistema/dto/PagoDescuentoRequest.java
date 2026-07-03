package com.restaurante.Sistema.dto;
import com.restaurante.Sistema.Enum.MetodoPago;
import java.math.BigDecimal;

public record PagoDescuentoRequest(
    Long comprobanteId,
    MetodoPago metodo,
    BigDecimal propina,
    BigDecimal porcentaje,   
    String motivo            
) {}