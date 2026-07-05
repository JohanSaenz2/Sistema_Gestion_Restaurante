package com.restaurante.Sistema.dto;

public record ComandaRequest(
    Long mesaId,
    Long empleadoId
) {}