package com.franklin.apirest.auth;

import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

//Configuraciones de OAuth2
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
	
	
	//1 Es la confuguracion de cors para que spring security retorne el header Access-Control-Allow-Origin
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		//Hacer dinamico con base de datos
		CorsConfiguration corsConfig = new CorsConfiguration();
		corsConfig.setAllowedOrigins(Arrays.asList("http://localhost:4200","http://localhost:81"));
		corsConfig.setAllowedMethods(Arrays.asList("GET","PUT","POST","DELETE","OPTIONS"));
		corsConfig.setAllowCredentials(true);
		corsConfig.setAllowedHeaders(Arrays.asList("Content-Type","Authorization"));

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfig);
		
		return source;
	}
	
	//2 Se crea un filtro para cors
	@Bean
	public FilterRegistrationBean<CorsFilter> coorsFilter(){
		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(corsConfigurationSource()));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return bean;
	}
	
	// 3 Se configuran los permisos para rutas comunes
	// Implementa todas las reglas de seguridad de las rutas
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
		.antMatchers("/api/clientes/{id}").permitAll()
		.antMatchers("/api/facturas/**").permitAll()
		//Las siguientes reglas fueron llevadas a anotaciones en el controller
		//primero se debe asigunar @EnableGlobalMethodSecurity en la clase de SecurityConfig
		/*
		.antMatchers(HttpMethod.GET,"api/clientes/{id}").hasAnyRole("ADMIN","SELLER")
		.antMatchers(HttpMethod.POST,"api/clientes/upload").hasAnyRole("ADMIN","SELLER")
		.antMatchers(HttpMethod.POST,"api/clientes","api/").hasRole("ADMIN")
		.antMatchers("api/clientes/**").hasRole("ADMIN")*/
		// esto va despues de cada configuracion de rutas
		.anyRequest().authenticated()
		//configura cors para spring security
		.and().cors().configurationSource(corsConfigurationSource());
	}
}
