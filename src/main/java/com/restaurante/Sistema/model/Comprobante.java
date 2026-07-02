package com.restaurante.Sistema.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.restaurante.Sistema.Enum.TipoComprobante;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "comprobantes", schema = "restaurante")
public class Comprobante {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "comprobante_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "comanda_id")
	private Comanda comanda;

	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_comprobante")
	private TipoComprobante tipo;

	@Column(name = "numero")
	private String numero;
	@Column(name = "subtotal")
	private BigDecimal subtotal;
	@Column(name = "descuento")
	private BigDecimal descuento;

	@Column(name = "iva")
	private BigDecimal iva;

	@Column(name = "propina")
	private BigDecimal propina;

	@Column(name = "total")
	private BigDecimal total;

	@Column(name = "fecha_emision")
	private LocalDateTime fechaEmision;
}