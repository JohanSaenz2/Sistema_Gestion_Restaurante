package com.restaurante.Sistema.controller;

import com.restaurante.Sistema.dto.PagoDescuentoRequest;
import com.restaurante.Sistema.dto.PagoRequest;
import com.restaurante.Sistema.exception.RecursoNoEncontradoException;
import com.restaurante.Sistema.model.Pago;
import com.restaurante.Sistema.repository.PagoRepository;
import com.restaurante.Sistema.service.PagoService;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

 private final PagoService service;  
 private final PagoRepository repo;

 public PagoController(PagoService service, PagoRepository repo) {
     this.service = service;
     this.repo = repo;
 }

 @GetMapping
 public List<Pago> listarTodos() { return repo.findAll(); }

 @GetMapping("/{id}")
 public Pago buscarPorId(@PathVariable Long id) {
     return repo.findById(id)
         .orElseThrow(() -> new RecursoNoEncontradoException("Pago " + id + " no encontrado"));
 }
 @PostMapping("/procesar")
 public Pago procesar(@RequestBody PagoRequest req) {
     return service.procesarPago(
         req.comprobanteId(),
         req.metodo(),
         req.propina()
     );
 }

 @PostMapping("/procesar-descuento")
 public Pago procesarDescuento(@RequestBody PagoDescuentoRequest req) {
     return service.procesarPagoConDescuento(
         req.comprobanteId(),
         req.metodo(),
         req.propina(),
         req.porcentaje(),
         req.motivo()
     );
 }
}