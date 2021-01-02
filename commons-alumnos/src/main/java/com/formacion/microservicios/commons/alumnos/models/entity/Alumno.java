package com.formacion.microservicios.commons.alumnos.models.entity;

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
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;


@Entity
@Table(name="alumnos")
public class Alumno {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	private String nombre;
	
	@NotEmpty
	private String apellido;
	
	@NotEmpty
	@Email
	private String email;
	
	@Column(name="create_at")
	// pasar a timeStamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date createAt;
	
	// asignar fecha antes de hacer un insert asigne la fecha
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

	@Override
	// Eliminar un objeto de la relación con la entidad curso, método para compobrar que sea el mismo alumno, buscando en la  lista de la clase curso.
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		// comprobar que sean una instancia del mismo tipo Alumno
		if(!(obj instanceof Alumno)) {
			return false;
		}
		//comparar los ids casteando a Alumno
		Alumno a = (Alumno) obj;
		return this.id != null && this.id.equals(a.getId());
	}
	
	
	
	
	
}
