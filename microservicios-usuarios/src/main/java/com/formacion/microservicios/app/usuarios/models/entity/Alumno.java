package com.formacion.microservicios.app.usuarios.models.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "alumnos")
public class Alumno {
	// asignar el campo id como identidad
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

// al llamarse igual los nombres en la tabla no hace falta usar Column
	private String nombre;
	private String apellido;
	private String email;

// Cambiar nombre en la tabla
	@Column(name = "create_at")
// indicar que la fecha sea completa un TimeStamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date createAt;
	
//asignar la fecha en la base de datos antes de hacer un insert
	@PrePersist
	public void prePersist() {
		this.createAt = new Date();
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	
	

}
