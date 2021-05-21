package com.franklin.apirest.service;

import java.util.List;

import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.franklin.apirest.data.IUsuarioDao;
import com.franklin.apirest.model.Usuario;

// Los service pueden utilizar varios Dao e implementar sus metodos 
// bajo una misma transaccion
@Service
public class UsuarioService implements IUsuarioService, UserDetailsService{
	private Logger logger =  LoggerFactory.getLogger(UsuarioService.class);
	@Autowired
	private IUsuarioDao usuarioDao;
	
	//Estos metodos son de la interfaz de spring
	@Override
	@Transactional(readOnly = true)
	// Maneja un usuario de Spring Security
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// obtenemos el usuario que esta logeado
		Usuario user = usuarioDao.findByUsername(username);
		// evalua si el usuario no existe en la base de datos
		if(user==null) {
			// logger.error genera un error en el log y puede mostrarse hasta en otro color
			logger.error("Error en login: El usuario "+username+" no existe");
			// se retorna una excepcion manualmente para que no continue la ejecucion.
			// como buena practica es de utilizar throw para este menester
			throw new UsernameNotFoundException("Error en login: El usuario "+username+" no existe");
		}
		// obtenemos la lista de authorities
		List<GrantedAuthority> authorities = user.getRoles()
				// ciclamos todos los roles mediante stream
				.stream()
				// generamos los GrantedAuthority a partir de Role mediante map()
				.map(role-> new SimpleGrantedAuthority(role.getName()))
				// mostramos por consola el authority generado mediante peek
				.peek(authority -> logger.info(authority.getAuthority()))
				// por ultimo exportamos la respuesta final del stream en una lista con collect() 
				.collect(Collectors.toList());
					
		return new User(username, user.getPassword(), user.isEnabled(), true, true, true, authorities);
	}
	
	//Este metodo es de la interfaz personalizada
	@Override
	// El complemento de spring repository ya hace una transaccion, 
	// pero si definimos la definimos aqui primero entonces el crud se va a unir
	// a esta transaccion. Como buena practica es bueno definir el @Transaction en las
	// clases service
	@Transactional(readOnly = true)
	public Usuario findByUsername(String username) {
		// TODO Auto-generated method stub
		return usuarioDao.findByUsername(username);
	}

}
