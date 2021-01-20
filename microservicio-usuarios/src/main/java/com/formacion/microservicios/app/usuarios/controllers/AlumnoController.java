package com.formacion.microservicios.app.usuarios.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.formacion.microservicios.app.usuarios.services.AlumnoService;
import com.formacion.microservicios.commons.alumnos.models.entity.Alumno;
import com.formacion.microservicios.commons.controllers.CommonController;

@RestController
public class AlumnoController extends CommonController<Alumno, AlumnoService> {

	@GetMapping("/alumnos-por-curso")
	public ResponseEntity<?> obtenerAlumnosPorCurso(@RequestParam List<Long> ids) {
		return ResponseEntity.ok(service.findAllById(ids));
	}
	
	
	@GetMapping("/uploads/img/{id}")
	public ResponseEntity<?> verFoto(@PathVariable Long id) {
		// buscar el alumno que exista en la bbdd
		Optional<Alumno> o = service.findById(id);
		if (o.isEmpty() || o.get().getFoto() == null) {
			// notFound es de tipo 400
			return ResponseEntity.notFound().build();
		}
		// crear el recurso de imagen de spring ByteArrayResource
		Resource imagen = new ByteArrayResource(o.get().getFoto());
		
		return ResponseEntity.ok()
				.contentType(MediaType.IMAGE_JPEG)
				.body(imagen); //pasamos la imagen en el body
		
	}

	@PutMapping("/{id}")
	// alumno que se quiere guardar con RequestBody, capturar el id con el
	// PathVariable
	// Validación. Através del result se obtienen los mensajes de error de la
	// validación, el orden influye tiene que ir después de alumno
	public ResponseEntity<?> editar(@Valid @RequestBody Alumno alumno, BindingResult result, @PathVariable Long id) {
		// validar antes de crear
		if (result.hasErrors()) {
			return this.validar(result);
		}
		// buscar el alumno que exista en la bbdd
		Optional<Alumno> o = service.findById(id);
		if (o.isEmpty()) {
			// notFound es de tipo 400
			return ResponseEntity.notFound().build();
		}

		// si existe lo modificamos, solo cambios necesarios
		Alumno alumnoDb = o.get();
		alumnoDb.setNombre(alumno.getNombre());
		alumnoDb.setApellido(alumno.getApellido());
		alumnoDb.setEmail(alumno.getEmail());

		// se le pasa al cuerpo de la respuesta, CREATED es de tipo 201
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(alumnoDb));
	}

	@GetMapping("/filtrar/{term}")
	public ResponseEntity<?> filtrar(@PathVariable String term) {
		return ResponseEntity.ok(service.findByNombreOrApellido(term));
	}

	@PostMapping("/crear-con-foto")
	public ResponseEntity<?> crearConFoto(@Valid Alumno alumno, BindingResult result,
			@RequestParam MultipartFile archivo) throws IOException {
		// si recogemos foto se la pasamos
		if (!archivo.isEmpty()) {
			// se envia en el request y hay que controlar las excepciones se lanza en la
			// declaración del método
			alumno.setFoto(archivo.getBytes());
		}
		// todo lo demás se lo pasamos por la clase padre
		return super.crear(alumno, result);
	}

	@PutMapping("/editar-con-foto/{id}")
	// no se recibe como Json es tipo de request MultipartFormData por lo que
	// @ResquestBody no va
	public ResponseEntity<?> editarConFoto(@Valid Alumno alumno, BindingResult result, @PathVariable Long id,
			@RequestParam MultipartFile archivo) throws IOException {
		// validar antes de crear
		if (result.hasErrors()) {
			return this.validar(result);
		}
		// buscar el alumno que exista en la bbdd
		Optional<Alumno> o = service.findById(id);
		if (o.isEmpty()) {
			// notFound es de tipo 400
			return ResponseEntity.notFound().build();
		}

		// si existe lo modificamos, solo cambios necesarios
		Alumno alumnoDb = o.get();
		alumnoDb.setNombre(alumno.getNombre());
		alumnoDb.setApellido(alumno.getApellido());
		alumnoDb.setEmail(alumno.getEmail());

		// si recogemos foto se la pasamos
		if (!archivo.isEmpty()) {
			// se envia en el request y hay que controlar las excepciones se lanza en la
			// declaración del método
			alumnoDb.setFoto(archivo.getBytes());
		}

		// se le pasa al cuerpo de la respuesta, CREATED es de tipo 201
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(alumnoDb));
	}

}
