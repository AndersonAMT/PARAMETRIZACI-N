package com.homerios.msnegociogestioncurso.exception;

/** Violacion de una regla de negocio parametrizada (p. ej. creditos fuera de rango). */
public class ReglaNegocioException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ReglaNegocioException(String mensaje) {
		super(mensaje);
	}
}
