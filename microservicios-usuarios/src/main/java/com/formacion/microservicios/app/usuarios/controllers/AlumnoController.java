package com.formacion.microservicios.app.usuarios.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.formacion.microservicios.app.usuarios.models.entity.Alumno;
import com.formacion.microservicios.app.usuarios.services.AlumnoService;

// marca la clase como controlador de tipo REST
@RestController
public class AlumnoController {
	@Autowired
	private AlumnoService service;

	// permite mapear una ruta url al método, la petición es del tipo get. Al no
	// poner ninguna ruta coge la ráiz
	@GetMapping
	// se la pasan objetos al body del response, y el status
	public ResponseEntity<?> listar() {
		// pasamos al cuerpo de la respuesta una lista de Entity
		return ResponseEntity.ok(service.findAll());
	}

	// Buscar ruta por ID
	@GetMapping("/{id}")
	// en el caso de que se llamase distinto se índica: (@PathVariable() Long id)
	public ResponseEntity<?> ver(@PathVariable() Long id) {
		// buscar el alumno que exista
		Optional<Alumno> o = service.findById(id);
		if (o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		// si existe lo devolvemos,el ok es de tipo 200
		return ResponseEntity.ok(o.get());
	}

	@PostMapping
	public ResponseEntity<?> crear(@RequestBody Alumno alumno) {
		Alumno alumnoDb = service.save(alumno);
		// se le pasa al cuerpo de la respuesta
		return ResponseEntity.status(HttpStatus.CREATED).body(alumnoDb);
	}

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
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminar(@PathVariable Long id){
		service.deleteById(id);
		//retorna void, no pasa nada al cuerpo, noContent es de tipo 204
		return ResponseEntity.noContent().build();
	}
	
}
