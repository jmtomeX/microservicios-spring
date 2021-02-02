package com.formacion.microservicios.commons.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import com.formacion.microservicios.commons.services.CommonService;

// para las cabeceras CORS del cliente en este caso las peticiones locales de Angular ("*") <- sería todas
@CrossOrigin("http://localhost:4200")
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
	
	// ruta para paginar
	@GetMapping("/pagina")
	// se la pasan objetos al body del response, y el status
	public ResponseEntity<?> listar(Pageable pageable) {
		// pasamos al cuerpo de la respuesta una lista de Entity
		return ResponseEntity.ok().body(service.findAll(pageable));
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
	// Validación. Através del result se obtienen los mensajes de error de la validación, el orden influye
	public ResponseEntity<?> crear(@Valid @RequestBody E entity, BindingResult result) {
		// validar antes de crear
		if (result.hasErrors()) {
			return this.validar(result);
		}
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
	
	// validación de campos
	protected ResponseEntity<?> validar (BindingResult result){
		// generar un json con el error

		Map<String, Object> errores = new HashMap<>();
		// obtebenos los mensajes de error y lo guardamos en el mapa
		result.getFieldErrors().forEach(err -> {   // Atributo 				Mensaje de error
		 errores.put(err.getField(), "El campo " +  err.getField() + " " + err.getDefaultMessage());
		});
		return ResponseEntity.badRequest().body(errores);
		
	}
	
}
