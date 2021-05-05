package com.franklin.apirest.service;

import java.util.List;

import com.franklin.apirest.model.Cliente;

/**
 * Declara todos los metodos de IClienteDao para poder ser implementados en una clase service
 *
 */
public interface IClienteService {
	public List<Cliente> findAll();
	public Cliente findById(Long id);
	public Cliente save(Cliente cliente);
	public void delete(Long id);
	
}
