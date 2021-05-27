package com.franklin.apirest.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="facturas")
public class Factura implements Serializable{


	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String descripcion;
	private String observacion;
	@Column(name = "created_at")
	@Temporal(TemporalType.DATE)
	private Calendar createdAt;
	
	// La opcion lazy carga los datos del cliente solo si
	// ejecutamos el metodo getCliente(); esto es mas optimo para
	// no generar recursividad infinita al obtener un listado de facturas
	// La opcion eager obtiene los datos completos del objeto mapeado,
	// esta opcion es buena cuando no hay riesgo de recursividad infinita
	@ManyToOne(fetch = FetchType.LAZY)
	// Es el nombre que va adquirir el campo de llave foranea
	// hacia clientes en la tabla facturas. Si se deja vacio, se coloca un
	// nombre automaticamente segun el estandar recomendado para nombrar campos
	// en base de datos manejado por spring.
	// @JoinColumn(name = "id_cliente")
	
	//ignoramos la relacion con el cliente para evitar hacer un ciclo infinito de consultas
	@JsonIgnoreProperties(value = {"facturas",
								   "hibernateLazyInitializer",
								   "handler"},
	// para evitar error de recursividad al actualizar registros desde el front-end
						  allowSetters = true)
	private Cliente cliente;
	@OneToMany(fetch = FetchType.LAZY,
			// importante definir cascade all para que al 
			// borrar o insertar en orden padre - hijos
			cascade=CascadeType.ALL)
	// Definimos el join column aqui ya que no nos interesa crear una instancia inversa
	// hasta esta clase desde itemFactura que genere la columna de forma automática.
	// Para que la relacion sea creada se necesita agregar esta configuracion aqui
	
	//eliminamos las propiedades basura del json
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	@JoinColumn(name ="factura_id")
	private List<ItemFactura> items;
	@PrePersist
	private void prePersist() {
		this.createdAt = Calendar.getInstance();
	}
	public Factura() {
		this.items = new ArrayList<ItemFactura>();
	}
	
	public Double getTotal() {
		Double total = 00.0;
		for (ItemFactura item: items) {
			total +=item.getImporte();
		}
		return total;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	public Calendar getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Calendar createdAt) {
		this.createdAt = createdAt;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public void setItems(List<ItemFactura> items) {
		this.items = items;
	}
	public List<ItemFactura> getItems() {
		return items;
	}
	
	
}
