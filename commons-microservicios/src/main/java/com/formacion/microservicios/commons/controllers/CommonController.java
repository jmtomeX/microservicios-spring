package com.formacion.microservicios.commons.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.formacion.microservicios.commons.services.CommonService;


public class CommonController<E, S extends CommonService<E>> {
	// inyectar el repositorio
	@Autowired
	protected S service;

	/* permite mapear una ruta url al método, la petición es del tipo get. Al no
	 poner ninguna ruta coge la ráiz */
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
		Optional<E> o = service.findById(id);
		if (o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		// si existe lo devolvemos,el ok es de tipo 200
		return ResponseEntity.ok(o.get());
	}

	@PostMapping
	public ResponseEntity<?> crear(@RequestBody E entity) {
		E entityDb = service.save(entity);
		// se le pasa al cuerpo de la respuesta
		return ResponseEntity.status(HttpStatus.CREATED).body(entityDb);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminar(@PathVariable Long id){
		service.deleteById(id);
		//retorna void, no pasa nada al cuerpo, noContent es de tipo 204
		return ResponseEntity.noContent().build();
	}
	
}