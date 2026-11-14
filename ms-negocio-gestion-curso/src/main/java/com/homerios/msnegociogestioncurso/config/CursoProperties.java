package com.homerios.msnegociogestioncurso.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

/**
 * Reglas de negocio parametrizadas del microservicio de cursos.
 *
 * <p>Viven en el Config Server ({@code ms-negocio-gestion-curso.yml}) y se recargan
 * en caliente con {@code POST /actuator/refresh}, sin recompilar ni reiniciar.</p>
 */
@Validated
@ConfigurationProperties(prefix = "gestion.curso")
public class CursoProperties {

	/** Maximo de creditos que puede tener un curso (lo decide la escuela). */
	@Min(1)
	@Max(60)
	private int maxCreditos = 20;

	/** Interruptor de funcionalidad: avisa en el log cuando un curso queda en el limite. */
	private boolean alertaCreditoAlto = true;

	public int getMaxCreditos() {
		return maxCreditos;
	}

	public void setMaxCreditos(int maxCreditos) {
		this.maxCreditos = maxCreditos;
	}

	public boolean isAlertaCreditoAlto() {
		return alertaCreditoAlto;
	}

	public void setAlertaCreditoAlto(boolean alertaCreditoAlto) {
		this.alertaCreditoAlto = alertaCreditoAlto;
	}
}
