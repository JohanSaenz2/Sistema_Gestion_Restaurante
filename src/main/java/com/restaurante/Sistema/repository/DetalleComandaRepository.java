package com.restaurante.Sistema.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restaurante.Sistema.Enum.Estacion;
import com.restaurante.Sistema.Enum.EstadoItem;
import com.restaurante.Sistema.model.DetalleComanda;

@Repository
public interface DetalleComandaRepository extends JpaRepository<DetalleComanda, Long> {
	List<DetalleComanda> findByComandaId(Long comandaId);

	List<DetalleComanda> findByProductoEstacionAndEstado(Estacion estacion, EstadoItem estado);
}
