package com.restaurante.Sistema.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.restaurante.Sistema.Enum.RolEmpleados;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "empleados", schema = "restaurante")
public class Empleado {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "empleado_id")
	private Long id;
	@Column(name = "nombre")
	private String nombre;
	@Column(name = "usuario")
	private String usuario;
	@Column(name = "password_hash")
	private String password;
	@Enumerated(EnumType.STRING)
	@Column(name = "rol")
	private RolEmpleados rol;

	@OneToMany(mappedBy = "mesero")
	@JsonManagedReference("empleado-comanda")
	private List<Comanda> comandas = new ArrayList();

}
