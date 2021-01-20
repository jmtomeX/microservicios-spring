package com.formacion.microservicios.app.cursos.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.formacion.microservicios.app.cursos.models.entity.Curso;
import com.formacion.microservicios.app.cursos.models.entity.CursoAlumno;
import com.formacion.microservicios.app.cursos.services.CursoService;
import com.formacion.microservicios.commons.alumnos.models.entity.Alumno;
import com.formacion.microservicios.commons.controllers.CommonController;
import com.formacion.microservicios.commons.examenes.models.entity.Examen;

@RestController
public class CursoController extends CommonController<Curso, CursoService> {

	// inyectamos la variable de sistema del balanceador de carga de gateway
	@Value("${config.balanceador.test}")
	private String balanceadorTest;

	// Modificar el listado
	@GetMapping
	@Override
	public ResponseEntity<?> listar() {
		List<Curso> cursos = ((List<Curso>) service.findAll()).stream().map(c -> {
			c.getCursoAlumnos().forEach(ca -> {
				// por cada uno se crea un objeto alumno, llenamos la colección de alumnos del curso
				Alumno alumno = new Alumno();
				alumno.setId(ca.getAlumnoId());
				c.addAlumno(alumno);
			});
			return c;
			// convertir a un tipo list porque es un string
		}).collect(Collectors.toList());
		// pasamos al cuerpo de la respuesta una lista de Entity
		return ResponseEntity.ok().body(cursos);
	}

	@GetMapping("/balanceador-test")
	public ResponseEntity<?> balanceadorTest() {
		// valor para testear el balanceador de carga spring cloud load balancer
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("balanceador", balanceadorTest);
		response.put("cursos", service.findAll());
		return ResponseEntity.ok(response);
	}

	@PutMapping("/{id")
	public ResponseEntity<?> editar(@Valid @RequestBody Curso curso, BindingResult result, @PathVariable Long id) {
		if (result.hasErrors()) {
			return this.validar(result);
		}
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
	public ResponseEntity<?> asignarAlumnos(@RequestBody List<Alumno> alumnos, @PathVariable Long id) {
		Optional<Curso> o = this.service.findById(id);
		if (!o.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		Curso dbCurso = o.get();

		alumnos.forEach(a -> {
			CursoAlumno cursoAlumno = new CursoAlumno();
			// Asignar un alumno
			cursoAlumno.setAlumnoId(a.getId());
			cursoAlumno.setCurso(dbCurso);
			dbCurso.addCursoAlumno(cursoAlumno);
			// dbCurso.addAlumno(a); relación con mysql que ya no está
		});
		return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(dbCurso));
	}

	// método para eliminar alumnos del curso de 1 en 1
	@PutMapping("/{id}/eliminar-alumno")
	public ResponseEntity<?> eliminarAlumno(@RequestBody Alumno alumno, @PathVariable Long id) {
		Optional<Curso> o = this.service.findById(id);
		if (!o.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		Curso dbCurso = o.get();
		CursoAlumno cursoAlumno = new CursoAlumno();
		cursoAlumno.setAlumnoId(alumno.getId());
		dbCurso.removeCursoAlumno(cursoAlumno);
		// dbCurso.removeAlumno(alumno); relación con mysql que ya no está
		return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(dbCurso));
	}

	// dado el id obtenemos el curso del alumno
	@GetMapping("/alumno/{id}")
	public ResponseEntity<?> buscarPorAlumnoId(@PathVariable Long id) {
		Curso curso = service.findCursoByAlumnoId(id);

		if (curso != null) {
			// obtener examenes respondidos del alumno, List tiene métodos como containts()
			// para preguntarle si existe
			// retorna un Iterable por lo que se le castea a List
			List<Long> examenesIds = (List<Long>) service.obtenerExamenesIdsConRespuestasAlumno(id);

			// nueva lista de examenes respondidos con map
			List<Examen> examenes = curso.getExamenes().stream().map(examen -> {
				if (examenesIds.contains(examen.getId())) {
					// si está contenido lo pasamos a true
					examen.setRespondido(true);
				}
				// retorna un string
				return examen;
				// se le pasa a a tipo List
			}).collect(Collectors.toList());
			// le enviamos la nueva lista, los examenes respondidos
			curso.setExamenes(examenes);
		}
		return ResponseEntity.ok(curso);
	}

	// método para asignar examenes al curso
	@PutMapping("/{id}/asignar-examenes")
	public ResponseEntity<?> asignarExamenes(@RequestBody List<Examen> examenes, @PathVariable Long id) {
		Optional<Curso> o = this.service.findById(id);
		if (!o.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		Curso dbCurso = o.get();

		examenes.forEach(e -> {
			dbCurso.addExamen(e);
		});
		return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(dbCurso));
	}

	// método para eliminar examenes del curso de 1 en 1
	@PutMapping("/{id}/eliminar-examen")
	public ResponseEntity<?> eliminarExamen(@RequestBody Examen examen, @PathVariable Long id) {
		Optional<Curso> o = this.service.findById(id);
		if (!o.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		Curso dbExamen = o.get();

		dbExamen.removeExamen(examen);
		return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(dbExamen));
	}

}
