package com.restaurante.Sistema.model;

import com.restaurante.Sistema.Enum.Estacion;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "productos", schema = "restaurante")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "producto_id")
	private Long id;

	@Column(name = "nombre", nullable = false)
	private String nombre;

	@Column(name = "codigo")
	private String codigo;
	@Column(name = "precio_base", nullable = false)
	private BigDecimal precioBase;

	@Column(name = "categoria", nullable = false)
	private String categoria;

	@Enumerated(EnumType.STRING)
	@Column(name = "estacion", nullable = false)
	private Estacion estacion;

}