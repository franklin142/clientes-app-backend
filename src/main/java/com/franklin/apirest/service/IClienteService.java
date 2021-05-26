package com.franklin.apirest.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.franklin.apirest.model.Cliente;
import com.franklin.apirest.model.Factura;
import com.franklin.apirest.model.Producto;
import com.franklin.apirest.model.Region;

/**
 * Declara todos los metodos de IClienteDao para poder ser implementados en una clase service
 * El objetivo de los Service es administrar los archivos dao y un service puede
 * manejar uno o mas clases DAO que esten de alguna manera relacionados entre si
 */
public interface IClienteService {
	public List<Cliente> findAll();
	public Page<Cliente> findAll(Pageable pageable);
	public Cliente findById(Long id);
	public Cliente save(Cliente cliente);
	public void delete(Long id);
	public List<Region> findAllRegiones();
	
	// Definiendo metodos para el IFacturaDao
	public Factura findFacturaById(Long id);
	public Factura saveFactura(Factura factura);
	public void deleteFacturaById(Long id);
	public List<Factura> findFacturaAll();
	public List<Producto> findProductoByNombre(String nombre);
}
