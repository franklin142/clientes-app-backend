package com.franklin.apirest.auth;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
//inicializa la configuracion manejada con springboot
@Configuration
//Convierte la clace en un servidor de autenticacion
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {
	//Es el mismo componente generado en SecurityConfig con la notacion @Bean
	@Autowired 
	public BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	// se especifica el nombre de la instancia que estamos inyectando, 
	// util cuando existen mas instancias y se requiere elegir
	@Qualifier("authenticationManager")
	public AuthenticationManager authManager;
	
	//Nos permite interceptar el token y escribirle mas informacion en su payload
	@Autowired
	public InfoAditionalToken infoAditionalToken;
	
	// Sobrescribimos los tres metodos de configuracion
	//Aqui se configuran los permisos de los endpoints
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		//Header Authorization Basic Client Id + Client Secret
		// Otorga permiso de acceso a autenticarse a todos los usuarios, anonimos o registrados
		security.tokenKeyAccess("permitAll()")
		//Otorga permiso al endpoint que se encarga de validar el token
		//Esta ruta verifica el token y su firma
		.checkTokenAccess("isAuthenticated()");
	}
	//aqui se configuran las aplicaciones que va a tener acceso al API REST
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		// el nombre de la app
		clients.inMemory().withClient("angularapp")
		// la clave encriptada de la app
		.secret(passwordEncoder.encode("apisecretkey123"))
		// se establecen los permisos de la app
		.scopes("read","write")
		//Define la forma de autenticacion, password para el primer ingreso y token para
		//Refrescar el acceso luego de iniciar sesion
		.authorizedGrantTypes("password","refresh_token")
		//Define el tiempo que dura el token de acceso
		.accessTokenValiditySeconds(3600)
		//DEfine el tiempo de acceso al refresh_token generado
		.refreshTokenValiditySeconds(3600);
	}
	
	// se encarga del proceso de autenticacion y validar el token
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		// Esta instancia permite agregar mas informacion al payload a travez de un componente 
		// inyectado con @Autowired que contenga una implementacion concreta de TokenEnhancer
		// y su metodo enhance()
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(infoAditionalToken, accessTokenConverter()));
		
		endpoints.authenticationManager(authManager)
		.accessTokenConverter(accessTokenConverter())
		// agrega la cadena con la informacion agregada en setTokenEnhancers()
		// esta accion puede omitirse y no implementar nada extra en el token.
		// si no se implementa entonces el token quedará como spring lo genere.
		.tokenEnhancer(tokenEnhancerChain);
		
	}
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter(); 
		jwtAccessTokenConverter.setSigningKey(JwtConfig.PRIVATE_RSA);
		jwtAccessTokenConverter.setVerifierKey(JwtConfig.PUBLIC_RSA);
		return jwtAccessTokenConverter;
	}
	
}
