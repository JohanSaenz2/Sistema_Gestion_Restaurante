package com.restaurante.Sistema.dto;

import com.restaurante.Sistema.Enum.RolEmpleados;

public record EmpleadoResponse(Long id, String nombre, String usuario, RolEmpleados rol) {
}
