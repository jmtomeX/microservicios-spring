package com.formacion.microservicios.app.cursos.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.formacion.microservicios.app.cursos.models.entity.Curso;
import com.formacion.microservicios.app.cursos.services.CursoService;
import com.formacion.microservicios.commons.alumnos.models.entity.Alumno;
import com.formacion.microservicios.commons.controllers.CommonController;

@RestController
public class CursoController extends CommonController<Curso, CursoService> {

	@PutMapping("/{id")
	public ResponseEntity<?> editar(@RequestBody Curso curso, @PathVariable Long id){
		// busca el curso
		Optional<Curso> o = this.service.findById(id);
		if (!o.isPresent()) { 
			return ResponseEntity.notFound().build();
		}
		// si existe lo modifica
		Curso dbCurso = o.get();
		dbCurso.setNombre(curso.getNombre());
		return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(dbCurso));
	}
	
	// método para asignar alumnos al curso
	@PutMapping("/{id}/asignar-alumnos")
	public ResponseEntity<?> asignarAlumnos(@RequestBody List<Alumno> alumnos, @PathVariable Long id){
		Optional<Curso> o = this.service.findById(id);
		if (!o.isPresent()) { 
			return ResponseEntity.notFound().build();
		}
		
		Curso dbCurso = o.get();
		
		alumnos.forEach(a -> {
			dbCurso.addAlumno(a);
		});
		return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(dbCurso));
	}
	
	// método para eliminar alumnos del curso de 1 en 1
	@PutMapping("/{id}/eliminar-alumno")
	public ResponseEntity<?> eliminarAlumno(@RequestBody Alumno alumno, @PathVariable Long id){
		Optional<Curso> o = this.service.findById(id);
		if (!o.isPresent()) { 
			return ResponseEntity.notFound().build();
		}
		
		Curso dbCurso = o.get();
		
		dbCurso.removeAlumno(alumno);
		return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(dbCurso));
	}
	
	// dado el id obtenemos el curso del alumno
	@GetMapping("/alumno/{id}")
	public ResponseEntity<?> buscarPorAlumnoId(@PathVariable Long id){
		Curso curso = service.findCursoByAlumnoId(id);
		System.out.print(curso);
		return ResponseEntity.ok(curso);
	}
	
	
}
