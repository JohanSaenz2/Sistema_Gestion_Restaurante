package com.restaurante.Sistema.repository;

import java.util.List;

import com.restaurante.Sistema.model.Adicionales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdicionalesRepository extends JpaRepository<Adicionales, Long> {

	List<Adicionales> findByAplicaA(String aplicaA);
}