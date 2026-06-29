package com.restaurante.Sistema.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restaurante.Sistema.model.Empledado;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empledado, Long> {

}
