package com.franklin.apirest.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.franklin.apirest.model.Usuario;
import com.franklin.apirest.service.IUsuarioService;

//TokenEnhancer permite potenciar el token agregando mas informacion
//Este componente debe ser registrado en AuthServerConfig
@Component
public class InfoAditionalToken implements TokenEnhancer{
	// inyectamos finalmente la interfaz del servicio para poder acceder al metodo
    // findByUsername()
	@Autowired
	public IUsuarioService usuarioService;
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		Usuario user = usuarioService.findByUsername(authentication.getName());
		Map<String,Object> info = new HashMap<String, Object>();
		info.put("nombre", user.getNombre());
		info.put("apellido", user.getApellido());
		info.put("email", user.getEmail());
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
		
		return accessToken;
	}

}
