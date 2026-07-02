package com.restaurante.Sistema.config;

import java.math.BigDecimal;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "restaurante.impuesto")
public class ImpuestoConfig {

	private BigDecimal iva = new BigDecimal("0.15");
}
