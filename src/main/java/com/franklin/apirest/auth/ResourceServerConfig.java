package com.franklin.apirest.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

//Configuraciones de OAuth2
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	//Implementa todas las reglas de seguridad de las rutas
	@Override
	public void configure(HttpSecurity http) throws Exception {
		// Se puede restringir a un solo metodo http agregando HttpMethod.GET
		// pero tambien se puede omitir el parametro para permitir todos
		// Es importante mantener el metodo permitAll() para restringir las
		// rutas de modo que el usuario debe estar al menos registrado para poder
		// ver los recursos
		http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/clientes",
				"/api/clientes/page/**",
				"/api/uploads/img/**",
				"/api/static/img/**").permitAll()
		//Las siguientes reglas fueron llevadas a anotaciones en el controller
		//primero se debe asigunar @EnableGlobalMethodSecurity en la clase de SecurityConfig
		/*
		.antMatchers(HttpMethod.GET,"api/clientes/{id}").hasAnyRole("ADMIN","SELLER")
		.antMatchers(HttpMethod.POST,"api/clientes/upload").hasAnyRole("ADMIN","SELLER")
		.antMatchers(HttpMethod.POST,"api/clientes","api/").hasRole("ADMIN")
		.antMatchers("api/clientes/**").hasRole("ADMIN")*/
		// esto va despues de cada configuracion de rutas
		.anyRequest().authenticated();
	}
	
}
