package com.restaurante.Sistema.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.restaurante.Sistema.model.DetalleComanda;

@Repository
public interface DetalleComandaRepository extends JpaRepository<DetalleComanda, Long> {

}
