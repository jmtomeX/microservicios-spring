package com.formacion.microservicios.app.respuestas.models.repository;

import org.springframework.data.repository.CrudRepository;

import com.formacion.microservicios.app.respuestas.models.entity.Respuesta;

public interface RespuestaRepository extends CrudRepository<Respuesta, Long> {

}
