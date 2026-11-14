package com.homerios.msnegociogestiondocente.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.homerios.msnegociogestiondocente.model.Docente;
import com.homerios.msnegociogestiondocente.repository.DocenteRepository;

@RefreshScope
@RestController
public class DocenteController {

	private static final Logger log = LoggerFactory.getLogger(DocenteController.class);

	@Autowired
	private DocenteRepository docenteRepository;


	@Value("${gestion.docente.alerta-sin-correo:false}")
	private boolean alertaSinCorreo;

	@GetMapping("/docentes")
	public List<Docente> obtenerDocentes() {
		return docenteRepository.findAll();
	}

	@GetMapping("/docente/{id}")
	public Optional<Docente> busDocente(@PathVariable("id") String id) {
		Long id_docente = Long.parseLong(id);
		return docenteRepository.findById(id_docente);
	}

	@PostMapping("/docente")
	public Docente crearDocente(@RequestBody Docente nDocente) {
		Docente tDocente = new Docente();
		tDocente.setNombre(nDocente.getNombre());
		tDocente.setAp_paterno(nDocente.getAp_paterno());
		tDocente.setAp_materno(nDocente.getAp_materno());
		tDocente.setUrl(nDocente.getUrl());
		tDocente.setCorreo(nDocente.getCorreo());
		tDocente.setDireccion(nDocente.getDireccion());
		tDocente.setDni(nDocente.getDni());

		Docente guardado = docenteRepository.save(tDocente);
		alertarSinCorreo(guardado);
		return guardado;
	}

	@PutMapping("/docente/{id}")
	public Optional<Docente> actualizarDocente(@PathVariable("id") String id, @RequestBody Docente aDocente) {
		Long id_docente = Long.parseLong(id);
		Optional<Docente> tDocente = docenteRepository.findById(id_docente);
		if (tDocente.isPresent()) {
			Docente t_Docente = tDocente.get();
			t_Docente.setNombre(aDocente.getNombre());
			t_Docente.setAp_paterno(aDocente.getAp_paterno());
			t_Docente.setAp_materno(aDocente.getAp_materno());
			t_Docente.setUrl(aDocente.getUrl());
			t_Docente.setCorreo(aDocente.getCorreo());
			t_Docente.setDireccion(aDocente.getDireccion());
			t_Docente.setDni(aDocente.getDni());
			docenteRepository.save(t_Docente);
			alertarSinCorreo(t_Docente);
		}
		return tDocente;

	}

	@DeleteMapping("/docente/{id}")
	public boolean deleteDocente(@PathVariable("id") String id) {
		Long id_docente = Long.parseLong(id);
		docenteRepository.deleteById(id_docente);
		return true;
	}

	private void alertarSinCorreo(Docente docente) {
		if (alertaSinCorreo && (docente.getCorreo() == null || docente.getCorreo().isBlank())) {
			log.warn("El docente {} {} fue registrado sin correo electronico", docente.getNombre(),
					docente.getAp_paterno());
		}
	}
}
