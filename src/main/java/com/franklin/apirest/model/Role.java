package com.franklin.apirest.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
@Table(name="roles")
public class Role implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	//Los roles en spring deben comenzar con _role
	@Column(unique=true,length=40)
	private String name;
	/*
	 * como usuario es la tabla padre, entonces 
	 * para poder cuantos usuarios tiene un rol
	 * se necesta usar manyToMany e indicar el
	 * campo de la tabla padre que une con esta
	 * entidad. Lo indicamos mediante mappedBy
	 * 
	@ManyToMany(mappedBy = "roles")
	private List<Usuario> users;
	*/
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	
}
