package com.jotonferreira.cursomc.resources;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jotonferreira.cursomc.dto.EmailDTO;
import com.jotonferreira.cursomc.security.JWTUtil;
import com.jotonferreira.cursomc.security.UserSS;
import com.jotonferreira.cursomc.services.AuthService;
import com.jotonferreira.cursomc.services.UserService;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {
	//classe que atualiza o token do usuario
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private AuthService service;
	

	@RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		
		UserSS user = UserService.authenticated(); // pega o usuario logado
		
		String token = jwtUtil.generateToken(user.getUsername()); // manda gerar novo token com data atual
		
		response.addHeader("Authorization", "Bearer " + token); // adiciona a resposta no header da requisição
		
		// Authorization é um cabeçalho personalizado, por isso é preciso dá uma instrução explicita para liberar a leitura do cabeçalho
		response.addHeader("access-control-expose-headers", "Authorization");
		
		return ResponseEntity.noContent().build();
	}
	
	// restful/endPoint para esqueci senha
	@RequestMapping(value = "/forgot", method = RequestMethod.POST)
	public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO objDTO) {
		
		service.sendNewPassword(objDTO.getEmail());
		
		return ResponseEntity.noContent().build();
	}

}
