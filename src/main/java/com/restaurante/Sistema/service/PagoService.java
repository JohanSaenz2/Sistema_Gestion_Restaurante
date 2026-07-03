// service/PagoService.java
package com.restaurante.Sistema.service;

import com.restaurante.Sistema.model.Comprobante;
import com.restaurante.Sistema.model.Pago;
import com.restaurante.Sistema.Enum.MetodoPago;
import com.restaurante.Sistema.repository.ComprobanteRepository;
import com.restaurante.Sistema.repository.PagoRepository;
import com.restaurante.Sistema.service.pago.ContextoPago;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
public class PagoService {

    private final PagoRepository pagoRepo;
    private final ComprobanteRepository comprobanteRepo;
    private final ContextoPago contextoPago;

    public PagoService(PagoRepository pagoRepo,
                       ComprobanteRepository comprobanteRepo,
                       ContextoPago contextoPago) {
        this.pagoRepo = pagoRepo;
        this.comprobanteRepo = comprobanteRepo;
        this.contextoPago = contextoPago;
    }

    public Pago procesarPago(Long comprobanteId,
                             MetodoPago metodo,
                             BigDecimal propina) {

        Comprobante comprobante = comprobanteRepo
            .findById(comprobanteId).orElse(null);
        if (comprobante == null) return null;

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
        return pagoRepo.save(pago);
    }

    public Pago procesarPagoConDescuento(Long comprobanteId,
                                          MetodoPago metodo,
                                          BigDecimal propina,
                                          BigDecimal porcentaje,
                                          String motivo) {

        Comprobante comprobante = comprobanteRepo
            .findById(comprobanteId).orElse(null);
        if (comprobante == null) return null;

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
        comprobante.setDescuento(descuento);  // guardas el motivo en BD
        comprobante.setTotal(total);
        comprobanteRepo.save(comprobante);

        Pago pago = new Pago();
        pago.setComprobante(comprobante);
        pago.setMetodo(metodo);
        pago.setMonto(total);
        return pagoRepo.save(pago);
    }
}