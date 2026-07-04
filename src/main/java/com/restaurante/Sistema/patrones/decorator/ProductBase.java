package com.restaurante.Sistema.patrones.decorator;

import java.math.BigDecimal;
import com.restaurante.Sistema.model.Product;

public class ProductBase implements ItemPedido {

    private final Product producto;

    public ProductBase(Product producto) {
        this.producto = producto;
    }

    @Override
    public String getName() {
        return producto.getNombre();
    }

    @Override
    public BigDecimal getPrice() {
        return producto.getPrecioBase();
    }
}