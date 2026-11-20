package com.restaurante.Sistema.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.restaurante.Sistema.model.Comprobante;
import com.restaurante.Sistema.model.DetalleComanda; // Ajusta el import al nombre real de tu clase de detalle

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;

@Service
public class PdfService {

    private final TemplateEngine templateEngine;

    @Value("${restaurante.nombre:Mi Restaurante}")
    private String nombreRestaurante;
    @Value("${restaurante.direccion:}")
    private String direccionRestaurante;
    @Value("${restaurante.ruc:}")
    private String rucRestaurante;

     private static final double ALTO_BASE_MM = 45.0;      
    private static final double ALTO_POR_ITEM_MM = 4.2;   
    private static final double ALTO_POR_LINEA_EXTRA_NOMBRE_MM = 3.4; 
    private static final double ALTO_POR_NOTA_MM = 3.0;   
    private static final double ALTO_MINIMA_MM = 60.0;    
    private static final double ALTO_MAXIMA_MM = 1000.0;  
    private static final double BUFFER_SEGURIDAD_MM = 10.0; 

     private static final int CARACTERES_POR_LINEA_NOMBRE = 16;

    public PdfService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Transactional(readOnly = true)
    public byte[] generarFacturaPdf(Comprobante comprobante, BigDecimal ivaTasa) {
        Context context = new Context();
        context.setVariable("comprobante", comprobante);
        context.setVariable("comanda", comprobante.getComanda());
        context.setVariable("nombreRestaurante", nombreRestaurante);
        context.setVariable("direccionRestaurante", direccionRestaurante);
        context.setVariable("rucRestaurante", rucRestaurante);
        context.setVariable("ivaPorcentaje", ivaTasa.multiply(BigDecimal.valueOf(100)).intValue());
        context.setVariable("tipoComprobanteTexto", switch (comprobante.getTipo()) {
            case factura -> "Factura";
            case nota_venta -> "Nota de venta";
            case ticket_interno -> "Ticket interno";
        });

        double alturaPaginaMm = calcularAlturaMm(comprobante);
        context.setVariable("alturaPaginaMm", alturaPaginaMm);

        String html = templateEngine.process("factura-pdf", context);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(out);

            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error al generar el PDF de la factura", e);
        }
    }

     private double calcularAlturaMm(Comprobante comprobante) {
        var detalles = comprobante.getComanda().getDetalles();

        int cantidadItems = detalles.size();
        long cantidadNotas = detalles.stream()
                .filter(d -> d.getNotas() != null && !d.getNotas().isEmpty())
                .count();

        int lineasExtraPorNombre = detalles.stream()
                .mapToInt(d -> {
                    String nombre = d.getProducto().getNombre();
                    int largo = (nombre != null) ? nombre.length() : 0;
                    int lineas = (int) Math.ceil((double) largo / CARACTERES_POR_LINEA_NOMBRE);
                    return Math.max(0, lineas - 1); 
                })
                .sum();

        double altura = ALTO_BASE_MM
                + (cantidadItems * ALTO_POR_ITEM_MM)
                + (lineasExtraPorNombre * ALTO_POR_LINEA_EXTRA_NOMBRE_MM)
                + (cantidadNotas * ALTO_POR_NOTA_MM)
                + BUFFER_SEGURIDAD_MM;

        if (comprobante.getDescuento() != null && comprobante.getDescuento().compareTo(BigDecimal.ZERO) > 0) {
            altura += ALTO_POR_NOTA_MM;
        }
        if (comprobante.getPropina() != null && comprobante.getPropina().compareTo(BigDecimal.ZERO) > 0) {
            altura += ALTO_POR_NOTA_MM;
        }

        return Math.min(ALTO_MAXIMA_MM, Math.max(ALTO_MINIMA_MM, altura));
    }
}