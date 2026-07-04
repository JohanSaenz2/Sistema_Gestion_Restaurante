package com.restaurante.Sistema.patrones.decorator;

import java.math.BigDecimal;

import com.restaurante.Sistema.model.Adicionales;

public class AdicionalConcret extends AdicionalDecorator {

	private final Adicionales adicional;

	public AdicionalConcret(ItemPedido item, Adicionales adi) {
		super(item);
		this.adicional = adi;
	}

	@Override
	public String getName() {
		return item.getName()+ " + "+ adicional.getNombre();
	}

	@Override
	public BigDecimal getPrice() {
		return item.getPrice().add(this.adicional.getPrecio());
	}


}
