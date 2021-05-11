package com.franklin.apirest.data;

import org.springframework.data.jpa.repository.JpaRepository;

import com.franklin.apirest.model.Cliente;

public interface IClienteDao extends JpaRepository<Cliente, Long>{
	/**
	 * Sirve para llamar los metodos de spring data ya definidos
	 * por el framework, aqui se pueden escribir los metodos personalizados 
	 * de inserción, eliminacion y consulta utilizando el lenguaje de queries de 
	 * spring sql 
	 */
}
