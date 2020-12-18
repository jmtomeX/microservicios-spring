package com.formacion.microservicios.app.usuarios.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.formacion.microservicios.app.usuarios.models.entity.Alumno;
import com.formacion.microservicios.app.usuarios.services.AlumnoService;

// marca la clase como controlador de tipo REST
@RestController
public class AlumnoController {
	@Autowired
	private AlumnoService service;
	
	// permite mapear una ruta url al método, la petición es del tipo get. Al no poner ninguna ruta coge la ráiz
	@GetMapping
	// se la pasan objetos al body del response, y el status
	public ResponseEntity<?> listar(){
		//pasamos al cuerpo de la respuesta una lista de Entity
		return ResponseEntity.ok(service.findAll());
	}
	// Buscar ruta por ID
	@GetMapping("/{id}")
	// en el caso de que se llamase distinto se índica: (@PathVariable() Long id)
	public ResponseEntity<?> ver(@PathVariable()  Long id){
		//buscar el alumno que exista
		Optional<Alumno> o = service.findById(id);
		if(o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		//si existe lo devolvemos
		return ResponseEntity.ok(o.get());
	}
}
