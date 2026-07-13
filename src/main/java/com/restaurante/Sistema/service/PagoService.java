package com.restaurante.Sistema.service;

import com.restaurante.Sistema.Enum.MetodoPago;
import com.restaurante.Sistema.model.Comprobante;
import com.restaurante.Sistema.model.Pago;
import com.restaurante.Sistema.exception.RecursoNoEncontradoException;
import com.restaurante.Sistema.repository.ComprobanteRepository;
import com.restaurante.Sistema.repository.PagoRepository;
import com.restaurante.Sistema.patrones.strategy.ContextoPago;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
public class PagoService {

    private final PagoRepository        pagoRepo;
    private final ComprobanteRepository comprobanteRepo;
    private final ContextoPago          contextoPago;

    public PagoService(PagoRepository pagoRepo,
                       ComprobanteRepository comprobanteRepo,
                       ContextoPago contextoPago) {
        this.pagoRepo        = pagoRepo;
        this.comprobanteRepo = comprobanteRepo;
        this.contextoPago    = contextoPago;
    }

    public List<Pago> listarTodos() {
        return pagoRepo.findAll();
    }

    public Pago buscarPorId(Long id) {
        return pagoRepo.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("Pago " + id + " no encontrado"));
    }

    public Pago procesarPago(Long comprobanteId,
                              MetodoPago metodo,
                              BigDecimal propina) {

        validarPropina(propina);

        Comprobante comprobante = comprobanteRepo.findById(comprobanteId)
            .orElseThrow(() -> new RecursoNoEncontradoException("Comprobante " + comprobanteId + " no encontrado"));

        BigDecimal total = contextoPago.calcular(
            metodo.name().toLowerCase(),
            comprobante.getSubtotal(),
            propina
        );

        comprobante.setPropina(propina);
        comprobante.setTotal(total);
        comprobanteRepo.save(comprobante);

        Pago pago = new Pago();
        pago.setComprobante(comprobante);
        pago.setMetodo(metodo);
        pago.setMonto(total);
        pago.setComision(BigDecimal.ZERO);
        return pagoRepo.save(pago);
    }

    public Pago procesarPagoConDescuento(Long comprobanteId,
                                          MetodoPago metodo,
                                          BigDecimal propina,
                                          BigDecimal porcentaje,
                                          String motivo) {

        validarPropina(propina);
        if (porcentaje == null || porcentaje.signum() < 0 || porcentaje.compareTo(BigDecimal.ONE) > 0) {
            throw new IllegalArgumentException("El porcentaje de descuento debe estar entre 0 y 1");
        }

        Comprobante comprobante = comprobanteRepo.findById(comprobanteId)
            .orElseThrow(() -> new RecursoNoEncontradoException("Comprobante " + comprobanteId + " no encontrado"));

        BigDecimal total = contextoPago.calcularConDescuento(
            comprobante.getSubtotal(),
            propina,
            porcentaje,
            motivo
        );

        BigDecimal descuento = comprobante.getSubtotal()
            .multiply(porcentaje)
            .setScale(2, java.math.RoundingMode.HALF_UP);

        comprobante.setPropina(propina);
        comprobante.setDescuento(descuento);
        comprobante.setTotal(total);
        comprobanteRepo.save(comprobante);

        Pago pago = new Pago();
        pago.setComprobante(comprobante);
        pago.setMetodo(metodo);
        pago.setMonto(total);
        pago.setComision(BigDecimal.ZERO);
        return pagoRepo.save(pago);
    }

    public void eliminar(Long id) {
        pagoRepo.deleteById(id);
    }

    private void validarPropina(BigDecimal propina) {
        if (propina == null || propina.signum() < 0) {
            throw new IllegalArgumentException("La propina no puede ser negativa");
        }
    }
}