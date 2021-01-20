package com.formacion.microservicios.app.cursos.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//Comunicación con el microservicio respuestas
@FeignClient(name = "microservicio-respuestas")
public interface RespuestaFeignClient {
	// indicar que endpoint nos comunicamos, parámetros que se pasan,tipo de retorno,...
	
	@GetMapping("/alumno/{alumnoId}/examenes-respondidos")
	public Iterable<Long> obtenerExamenesIdsConRespuestasAlumno(@PathVariable Long alumnoId);
}
