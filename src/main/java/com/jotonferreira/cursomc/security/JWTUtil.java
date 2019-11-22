package com.jotonferreira.cursomc.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component // essa classe pode ser injetada em outras classes como componente
public class JWTUtil {
	// gera o token de autenticação do usuario/cliente
	
	@Value("${jwt.secret}")
	private String secret; // pega o valor do application.properties
	
	@Value("${jwt.expiration}")
	private Long expiration; // pega o valor do application.properties
	
	// gera o token
	public String generateToken(String username) {
		
		return Jwts.builder()
				.setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + expiration)) // tempo para expirar o token
				.signWith(SignatureAlgorithm.HS512, secret.getBytes()) // indica como vai assinar o token
				.compact();
	}
	
}
