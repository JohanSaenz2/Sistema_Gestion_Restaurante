package com.restaurante.Sistema.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.restaurante.Sistema.Enum.EstadoMesa;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mesas", schema = "restaurante")
public class Mesa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "mesa_id")
	private Long mesaId;
	@Column(name = "numero")
	private int numMesa;
	@Enumerated(EnumType.STRING)
	@Column(name = "estado")
	private EstadoMesa estado;
	@OneToMany(mappedBy = "mesa")
	@JsonManagedReference("mesa-comanda")
	private List<Comanda> comandas = new ArrayList();

}
