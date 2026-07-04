package com.restaurante.Sistema.model;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "adicionales", schema = "restaurante")
public class Adicionales {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "adicional_id")
	private Long adicionalId;
	@Column(name = "nombre")
	private String nombre;
	@Column(name = "precio")
	private BigDecimal precio;
	@Column(name = "aplica_a")
	private String aplicaA;

}
