package com.franklin.apirest.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
//Escribimos la notacion de spring security para configurar
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//Configuraciones de Spring Security
@EnableGlobalMethodSecurity(securedEnabled = true)
@Configuration
//Extendemos de WebSecurityConfigurerAdapter
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	// @Autowired es para inyectar
	// Como UserDetailsService esta implementada en UsuarioService
	// entonces spring tomara esta clase como primera implementacion
	@Autowired
	private UserDetailsService usuarioService;
	// @Bean Sirve para guardar un elemento retornado 
	// por el metodo anotado a componente de spring
	@Bean
	public BCryptPasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	};
	//sobre escribimos el metodo configure de la clase padre WebSecurityConfigurerAdapter
	@Autowired
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.usuarioService).passwordEncoder(passwordEncoder());
	}
	// sobrescribimos tambien el metodo authenticationnManager 
	//@Bean va a guaradar la respuesta del metodo con el mismo nombre especificado
	@Bean("authenticationManager")
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	//Implementa todas las reglas de seguridad de las rutas
	@Override
	public void configure(HttpSecurity http) throws Exception {
		// Se deshabilita el manejo de sesiones y verificacion CSRF
		http.authorizeRequests()
		// esto va despues de cada configuracion de ruta
		.anyRequest().authenticated()
		// sirve para unir otra configuracion
		.and()
		// sirve para deshabilitar la verificacion csrf utilizada en proyectos spring MVC
		// esto es util para permitir las conexiones de aplicaciones de terceros como angular
		.csrf().disable()
		// es necesario establecer STATELESS ya que el usuario se autentica con token y no con sesiones
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		;
	}
}
