package com.formacion.microservicios.app.usuarios.controllers;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.formacion.microservicios.app.usuarios.models.entity.Alumno;
import com.formacion.microservicios.app.usuarios.services.AlumnoService;
import com.formacion.microservicios.commons.controllers.CommonController;

@RestController
public class AlumnoController extends CommonController<Alumno, AlumnoService> {

	@PutMapping("/{id}")
	// alumno que se quiere guardar con RequestBody, capturar el id con el PathVariable
	public ResponseEntity<?> editar(@RequestBody Alumno alumno, @PathVariable Long id) {
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
		
		// se le pasa al cuerpo de la respuesta, CREATED  es de tipo 201
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(alumnoDb));
	}
	
	
}