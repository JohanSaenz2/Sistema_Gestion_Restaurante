package com.restaurante.Sistema.dto;

import com.restaurante.Sistema.Enum.EstadoComanda;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ComandaResponse(
    Long id,
    EstadoComanda estado,
    BigDecimal total,
    LocalDateTime fecha,
    Long mesaId,
    Integer numeroMesa,
    Long empleadoId,
    String nombreEmpleado,
    String servicio 
) {}