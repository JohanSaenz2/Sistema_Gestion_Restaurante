package com.restaurante.Sistema.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restaurante.Sistema.model.Comanda;

@Repository
public interface ComandaRepository extends JpaRepository<Comanda, Long> {
	List<Comanda> findByEstado(String estado);

	List<Comanda> findByMesaMesaId(Long mesaId);
}
