package com.restaurante.Sistema.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.restaurante.Sistema.model.Comprobante;

@Repository
public interface ComprobanteRepository extends JpaRepository<Comprobante, Long> {

}
