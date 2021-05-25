package com.franklin.apirest.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="facturas_items")
public class ItemFactura  implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Integer cantidad;
	
	@ManyToOne(fetch = FetchType.LAZY)
	// es opcional especificar el nombre de la columna... spring genera el nombre 
	// adecuado segun el estandar recomendado
	// @JoinColumn(name="producto_id")
	
	//eliminamos las propiedades basura del json
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Producto producto;
	// se coloca get a los metodos que deseamos agregar en el JSON de respuesta
	// al final spring ejecuta el metodo y genera una propiedad segun el nombre
	// el cual se refleja en el nombre del metodo
	// para este ejemplo getImporte() spring separa las palabras "get" e "Importe"
	// y tomara siempre la segunda palabra que encuentre con inicial mayuscula y luego la convierte
	// en variable y la agrega al objeto JSON que será retornado al cliente. Todo automaticamente
	// de manera que solo debemos preocuparnos por nombrar el metodo con la palabra get al inicio
	// y luego una palabra que inicie con mayuscula seguido de get. Ejemplo public Double getImporte(){return 0;}
	
	public Double getImporte() {
		return cantidad.doubleValue()*producto.getPrecio();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	public Producto getProducto() {
		return producto;
	}
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	
	
	
}
