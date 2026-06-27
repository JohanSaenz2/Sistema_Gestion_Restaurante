package com.restaurante.Sistema.model;

import java.math.BigDecimal;

import com.restaurante.Sistema.Enum.MetodoPago;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "pagos", schema = "restaurante")
public class Pago {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pago_id")
	private Long pago_id;
	@ManyToOne
	@JoinColumn(name = "comprobante_id")
	private Comprobante comprobante;
	@Column(name = "metodo")
	private MetodoPago metodo;
	@Column(name = "monto")
	private BigDecimal monto;
	@Column(name = "comision")
	private BigDecimal comision;

}
