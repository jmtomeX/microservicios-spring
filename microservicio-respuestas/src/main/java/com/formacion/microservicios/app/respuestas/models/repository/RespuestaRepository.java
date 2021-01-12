package com.formacion.microservicios.app.respuestas.models.repository;

import org.springframework.data.repository.CrudRepository;

import com.formacion.microservicios.app.respuestas.models.entity.Respuesta;

public interface RespuestRepository extends CrudRepository<Respuesta, Long> {

}
