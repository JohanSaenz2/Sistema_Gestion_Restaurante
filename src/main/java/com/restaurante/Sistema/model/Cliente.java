package com.restaurante.Sistema.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "clientes", schema = "restaurante")
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cliente_id")
	private Long clienteID;
	@Column(name = "identificacion")
	private String identificacion;
	@Column(name = "nombre")
	private String nombre;
	@Column(name = "direccion")
	private String direccion;
	@Column(name = "telefono")
	private String telefono;
	@Column(name = "email")
	private String email;
}
