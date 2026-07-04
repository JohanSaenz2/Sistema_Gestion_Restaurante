package com.restaurante.Sistema.dto;

import java.util.List;

public record DetalleComandaRequest(Long comandaId, Long productoId, int cantidad, List<Long> adicionalIds) {

}
