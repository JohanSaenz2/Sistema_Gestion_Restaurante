package com.restaurante.Sistema.dto;

import com.restaurante.Sistema.Enum.EstadoItem;
import java.math.BigDecimal;

public record DetalleComandaResponse(
    Long id,
    Long comandaId,
    Long productoId,
    String nombreProducto,
    Integer cantidad,
    BigDecimal precioUnitario,
    String notas,
    EstadoItem estado
) {}