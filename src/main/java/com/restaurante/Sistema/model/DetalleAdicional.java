package com.restaurante.Sistema.model;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "detalle_adicional", schema = "restaurante")
public class DetalleAdicional {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "detalle_adicional_id")
	private Long detalleAdicionalid;
	@ManyToOne
	@JoinColumn(name = "detalle_comanda_id")
	private DetalleComanda detalleComanda;
	@ManyToOne
	@JoinColumn(name = "adicional_id")
	private Adicionales adicional;
	@Column(name = "cantidad")
	private int cantidad;
	@Column(name = "precio_aplicado")
	private BigDecimal precioAplicado;

}
