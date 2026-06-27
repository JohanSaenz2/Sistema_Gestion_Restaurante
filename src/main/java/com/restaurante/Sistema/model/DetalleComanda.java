package com.restaurante.Sistema.model;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "detalle_comanda", schema = "restaurante")
public class DetalleComanda {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "detalle_comanda_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "comanda_id")
	private Comanda comanda;

	@ManyToOne
	@JoinColumn(name = "producto_id")
	private Product producto;

	@Column(name = "cantidad")
	private Integer cantidad;

	@Column(name = "subtotal")
	private BigDecimal subtotal;

	@Column(name = "notas")
	private String notas;

}