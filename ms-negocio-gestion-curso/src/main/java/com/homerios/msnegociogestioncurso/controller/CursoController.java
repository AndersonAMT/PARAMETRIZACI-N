package com.homerios.msnegociogestioncurso.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.homerios.msnegociogestioncurso.config.CursoProperties;
import com.homerios.msnegociogestioncurso.exception.ReglaNegocioException;
import com.homerios.msnegociogestioncurso.model.Curso;
import com.homerios.msnegociogestioncurso.repository.CursoRepository;

@RestController
public class CursoController {

	private static final Logger log = LoggerFactory.getLogger(CursoController.class);

	@Autowired
	private CursoRepository cursoRepository;

	@Autowired
	private CursoProperties cursoProperties;

	@GetMapping("/cursos")
	public List<Curso> obtenerCursos() {
		return cursoRepository.findAll();
	}

	@GetMapping("/curso/{id}")
	public Optional<Curso> busCurso(@PathVariable("id") String id) {
		Long id_curso = Long.parseLong(id);
		return cursoRepository.findById(id_curso);
	}

	@PostMapping("/curso")
	public Curso crearDocente(@RequestBody Curso nCurso) {
		validarCreditos(nCurso.getCredito());

		Curso tCurso = new Curso();
		tCurso.setNombre(nCurso.getNombre());
		tCurso.setCredito(nCurso.getCredito());
		tCurso.setId_docente(nCurso.getId_docente());

		return cursoRepository.save(tCurso);
	}

	@PutMapping("/curso/{id}")
	public ResponseEntity<Curso> actualizarCurso(@PathVariable("id") Long id, @RequestBody Curso aCurso) {
		validarCreditos(aCurso.getCredito());

		return cursoRepository.findById(id).map(curso -> {
			curso.setNombre(aCurso.getNombre());
			curso.setCredito(aCurso.getCredito());
			curso.setId_docente(aCurso.getId_docente());
			Curso actualizado = cursoRepository.save(curso);
			return ResponseEntity.ok(actualizado);
		}).orElseGet(() -> ResponseEntity.notFound().build());

	}

	@DeleteMapping("/curso/{id}")
	public boolean deleteCurso(@PathVariable("id") Long id) {
		cursoRepository.deleteById(id);
		return true;
	}

	/**
	 * Regla de negocio parametrizada: {@code gestion.curso.max-creditos}.
	 * Si el curso queda justo en el limite y {@code gestion.curso.alerta-credito-alto} esta activo,
	 * deja la advertencia en el log.
	 */
	private void validarCreditos(int credito) {
		int maximo = cursoProperties.getMaxCreditos();

		if (credito > maximo) {
			throw new ReglaNegocioException("Un curso no puede superar " + maximo + " creditos");
		}
		if (cursoProperties.isAlertaCreditoAlto() && credito == maximo) {
			log.warn("Curso en el limite de creditos permitido: {} de {}", credito, maximo);
		}
	}
}
