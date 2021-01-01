package com.formacion.microservicios.app.examenes.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.formacion.microservicios.app.examenes.services.ExamenService;
import com.formacion.microservicios.commons.controllers.CommonController;
import com.formacion.microservicios.commons.examenes.models.entity.Examen;

@RestController
public class ExamenController extends CommonController<Examen, ExamenService> {

	// modificar, añadir preguntas al examen
	@PutMapping("/{id}")
	public ResponseEntity<?> editar(@RequestBody Examen examen, @PathVariable Long id) {
		// obtener examen por id
		Optional<Examen> o = service.findById(id);
		if (!o.isPresent()) {
			// si no lo encuentra envia un 404 con una respuesta vacia
			return ResponseEntity.notFound().build();
		}
		// si está presente lo obtenemos del request
		Examen examenDb = o.get();
		// modificamos el nombre
		examenDb.setNombre(examen.getNombre());

		/*
		 * reflejar el cambio del json que se envia y la bbdd, se recibe un json el cual
		 * se puede modificar porque se han borrado preguntas o añadido por lo que no
		 * sera el mismo
		 */

		// preguntas eliminadas
		/*
		 * List<Pregunta> eliminidas = new ArrayList<>();
		 * 
		 * examenDb.getPreguntas().forEach(pdb -> { // si una pregunta no existe en el
		 * examen se le añade para eliminarla // con constains importante tener
		 * sobreescrito el equals ya que lo utiliza if
		 * 
		 * (!examen.getPreguntas().contains(pdb)) { eliminidas.add(pdb); } });
		 * eliminidas.forEach(p -> { examenDb.removePregunta(p); });
		 */

		// Código de arriba obtimizado con stream de java8
		examenDb.getPreguntas().stream()
				// examen se le añade para eliminarla // con constains importante tener
				.filter(pdb -> !examen.getPreguntas().contains(pdb))
				// recibe pdb y se le pasa como argumento a remove
				.forEach(examenDb::removePregunta);

		examenDb.setPreguntas(examen.getPreguntas());

		// agregar las preguntas, modificar las existentes o las que no se tocan se
		// dejan como están
		examenDb.setPreguntas(examen.getPreguntas());
		// examenDb es el que contiene todos los cambios
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(examenDb));
	}
}
