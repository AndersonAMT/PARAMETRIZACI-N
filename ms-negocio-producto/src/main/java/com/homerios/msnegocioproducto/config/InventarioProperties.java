package com.homerios.msnegocioproducto.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Min;

/**
 * Parametros de negocio del inventario.
 * Se enlazan desde el Config Server con el prefijo {@code inventario}.
 */
@Validated
@ConfigurationProperties(prefix = "inventario")
public class InventarioProperties {

	/** Maximo de unidades permitidas en un solo movimiento. */
	@Min(1)
	private int maxUnidadesPorMovimiento = 1000;

	/** Si es true, se registra una advertencia cuando el stock queda bajo el minimo. */
	private boolean alertaStockBajo = true;

	public int getMaxUnidadesPorMovimiento() {
		return maxUnidadesPorMovimiento;
	}

	public void setMaxUnidadesPorMovimiento(int maxUnidadesPorMovimiento) {
		this.maxUnidadesPorMovimiento = maxUnidadesPorMovimiento;
	}

	public boolean isAlertaStockBajo() {
		return alertaStockBajo;
	}

	public void setAlertaStockBajo(boolean alertaStockBajo) {
		this.alertaStockBajo = alertaStockBajo;
	}
}
