package com.franklin.apirest.data;

import org.springframework.data.repository.CrudRepository;

import com.franklin.apirest.model.Factura;
// el primer parametro es el tipo de datos que vamos a mapear
// el parametro Long es el tipo de dato de la llave primaria
public interface IFacturaDao extends CrudRepository<Factura, Long>{
	
}
