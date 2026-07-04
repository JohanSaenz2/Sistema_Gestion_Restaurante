package com.restaurante.Sistema.patrones.decorator;

import java.math.BigDecimal;

public class AdicionalDecorator implements ItemPedido {

	protected final ItemPedido item;

	public AdicionalDecorator(ItemPedido item) {
		super();
		this.item = item;
	}

	@Override
	public String getName() {
		return item.getName();
	}

	@Override
	public BigDecimal getPrice() {
		return item.getPrice();
	}

}
