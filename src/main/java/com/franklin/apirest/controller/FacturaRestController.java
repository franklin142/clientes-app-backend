package com.franklin.apirest.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.franklin.apirest.model.Factura;
import com.franklin.apirest.model.Producto;
import com.franklin.apirest.service.IClienteService;
@CrossOrigin(origins = {
		"http://localhost:4200", //local desarrollo
		"http://localhost:8181", //local pruebas
		"https://clientes-app-udemy.web.app" //remoto produccion
		})
@RestController
@RequestMapping("/api")
public class FacturaRestController {
	@Autowired
	private IClienteService clienteService;
	
	@GetMapping("/facturas")
	public List<Factura> index(){
		return clienteService.findFacturaAll();
	}
	//@Secured({"ROLE_ADMIN","ROLE_SELER"})
	@GetMapping("/facturas/{id}")
	public ResponseEntity<?> show(@PathVariable Long id){
		Factura factura= null;
		Map<String, Object> response = new HashMap<>();
		//manejamos un posible error de la base de datos
		try {
			factura =clienteService.findFacturaById(id);
		}catch(DataAccessException ex) {
			response.put("message", "Error en la base de datos");
			response.put("error", ex.getMostSpecificCause().getMessage());//obtenemos la causa mas especifica del error
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);			
		}
		if(factura==null) {
			response.put("message", "La factura que intenta cargar no existe");
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Factura>(factura,HttpStatus.OK);
	}
	
	@Secured({"ROLE_ADMIN"})
	@PostMapping("/facturas")
	//@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<?> create(@RequestBody @Valid Factura factura, BindingResult result) {
		
		Map<String, Object> response = new HashMap<>();
		try {
			if(result.hasErrors()) {
				//obtenemos los errores de validacion de @valid a travez del objeto result
				List<String> errors = result.getFieldErrors()
						.stream()
						.map(err-> "'"+err.getField()+ "' "+err.getDefaultMessage())
						.collect(Collectors.toList());
				
				response.put("errors",errors);
				return new ResponseEntity<Map<String, Object>>(response,HttpStatus.BAD_REQUEST);	
			}
			factura = clienteService.saveFactura(factura);
			response.put("message", "Factura guardada correctamente");
			// esta es una manera de pasar el objeto a travez de un Map<> que al final se convierte en json
			response.put("factura", factura);
		}catch(DataAccessException ex) {
			response.put("message", "Error en la base de datos al insertar");
			response.put("error", ex.getMessage()+": "+ex.getMostSpecificCause().getMessage());
			response.put("error2", result.toString());

			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);			
		}
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);
	}
	
	@Secured({"ROLE_ADMIN"})
	@DeleteMapping("/facturas/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();

		try {
			clienteService.delete(id);
			response.put("message", "Factura eliminado correctamente.");
		}catch(DataAccessException ex) {
			response.put("message", "Error en la base de datos al borrar el registro");
			response.put("error", ex.getMessage()+": "+ex.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}
	@Secured({"ROLE_SALES"})
	@GetMapping("/facturas/filtrar-productos/{nombre}")
	public List<Producto> getProductos(@PathVariable String nombre){
		return clienteService.findProductoByNombre(nombre);
	}}
