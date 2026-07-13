// controller/DetalleComandaController.java
package com.restaurante.Sistema.controller;

import com.restaurante.Sistema.dto.DetalleComandaRequest;
import com.restaurante.Sistema.dto.DetalleComandaResponse;
import com.restaurante.Sistema.model.DetalleComanda;
import com.restaurante.Sistema.service.DetalleComandaService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/detalles")
public class DetalleComandaController {

    private final DetalleComandaService service;

    public DetalleComandaController(DetalleComandaService service) {
        this.service = service;
    }

    @PostMapping
    public DetalleComandaResponse crear(@RequestBody DetalleComandaRequest req) {
        return service.crearDetalle(
            req.productoId(),
            req.comandaId(),
            req.cantidad(),
            req.adicionalIds()
        );
    }

    @PutMapping("/{id}/cocina")
    public DetalleComandaResponse enviarACocina(@PathVariable Long id) {
        return service.enviarACocina(id);
    }

    @PutMapping("/{id}/listo")
    public DetalleComandaResponse marcarListo(@PathVariable Long id) {
        return service.marcarListo(id);
    }

    @PutMapping("/{id}/entregar")
    public DetalleComandaResponse entregar(@PathVariable Long id) {
        return service.entregar(id);
    }

    @PutMapping("/{id}/cancelar")
    public DetalleComandaResponse cancelar(@PathVariable Long id) {
        return service.cancelarItem(id);
    }
}