package com.franklin.apirest.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name="clientes")
public class Cliente implements Serializable {// Serializable sirve para poder guardar el objeto en sesion con sus atributos serializados.

	private static final long serialVersionUID = 1L;// Esta linea es necesaria cuando se implementa serializable
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	@Size(min=4,max=50)
	@Column(nullable = false)
	private String nombre;
	
	@NotEmpty
	private String apellido;
	
	@NotEmpty
	@Email
	@Column(nullable = false,unique = true)
	private String email;
	
	@NotNull
	@Column(name="created_at")
	private LocalDate createdAt;
	
	private String foto;
	
	//fetch es la forma en la que se obtienen los datos en la consulta.
	//con FetchType.LAZY los datos se obtienen al llamar al metodo getRegion.
	//evitando cargar todo desde el inicio
	@ManyToOne(fetch=FetchType.LAZY)
	//Si se omite el name de la columna spring toma el nombre 
	//de la propiedad mas el id referenciado
	@JoinColumn(name="region_id"/*, columnDefinition = "bigint defualt 1"*/)
	//Al generar un objeto de tipo LAZY se genera un proxy junto a unos elementos innecesarios
	//los cuales deben ser excluidos a la hora de convertir la clase en json.
	//Estos elementos son metodos que generan error al llamar intentar retornar
	@JsonIgnoreProperties(value = {"hibernateLazyInitializer",
								   "handler"},
	// para evitar error de recursividad al actualizar registros desde el front-end
						  allowSetters = true)
	@NotNull
	private Region region;
	
	@OneToMany(fetch = FetchType.LAZY,
			// como dejamos que spring maneje el nombre del campo
			// entonces solo escribimos el nombre que le dimos a la
			// propiedad.... private Cliente cliente;
			mappedBy = "cliente",
			cascade = CascadeType.ALL)
	
	//ignoramos la propiedad cliente en las facturas para evitar 
	//hacer un ciclo infinito de consultas
	@JsonIgnoreProperties({"cliente","hibernateLazyInitializer","handler"})
	private List<Factura> facturas;
	/*
	@PrePersist
	private void prePersist() {
		//Esto es un evento que se ejecuta antes de guardar el modelo en la base de datos
		//createdAt = LocalDate.now();
		region.setId(Long.getLong("1"));
	}
	*/
	public Cliente() {
		this.facturas = new ArrayList<Factura>();
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
	public LocalDate getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
	public Region getRegion() {
		return region;
	}
	public void setRegion(Region region) {
		this.region = region;
	}
	public List<Factura> getFacturas() {
		return facturas;
	}
	public void setFacturas(List<Factura> facturas) {
		this.facturas = facturas;
	}
	
}
