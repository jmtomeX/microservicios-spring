package com.formacion.microservicios.app.cursos.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.formacion.microservicios.commons.alumnos.models.entity.Alumno;
// Comunicación con el microservicio usuarios
@FeignClient(name="microservicio-usuarios")
public interface AlumnoFeignClient {
	// indicar que endpoint nos comunicamos, parámetros que se pasan,tipo de retorno,...
	@GetMapping("/alumnos-por-curso")
	public Iterable<Alumno> obtenerAlumnosPorCurso(@RequestParam List<Long> ids);
}
