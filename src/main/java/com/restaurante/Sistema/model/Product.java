package com.restaurante.Sistema.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "productos", schema = "restaurante")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "producto_id")
	private Long id;

	@Column(name = "nombre", nullable = false)
	private String nombre;

	@Column(name = "precio_base", nullable = false)
	private BigDecimal precioBase;

	@Column(name = "categoria", nullable = false)
	private String categoria;

	@Column(name = "estacion", nullable = false)
	private String estacion;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public BigDecimal getPrecioBase() {
		return precioBase;
	}

	public void setPrecioBase(BigDecimal precioBase) {
		this.precioBase = precioBase;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getEstacion() {
		return estacion;
	}

	public void setEstacion(String estacion) {
		this.estacion = estacion;
	}
}