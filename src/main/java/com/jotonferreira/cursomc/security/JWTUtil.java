package com.jotonferreira.cursomc.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
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
	
	// faz verificações para indica que o token é valido
	public boolean tokenValido(String token) {
		
		Claims claims = getClaims(token); // reivindicações do token como usuario e tempo de expiração do token
		
		if(claims != null) {
			
			String username = claims.getSubject(); // pega o email do usuario
			Date expirationDate = claims.getExpiration(); // pega a data de expiração do do token
			
			Date now = new Date(System.currentTimeMillis());
			
			if(username != null && expirationDate != null && now.before(expirationDate)) {
				return true;
			}
			
		}
		
		return false;
		
	}
	
	// se os claims não for nulo pega o email do usuario
	public String getUsername(String token) {
					
		Claims claims = getClaims(token); // reivindicações do token como usuario e tempo de expiração do token
		
		if(claims != null) {
			return claims.getSubject(); // pega o email do usuario
		}
		
		return null;
		
	}
	
	// recupera os claims apartir de um token
	private Claims getClaims(String token) {
		
		try {
			return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
		}
		catch (Exception e) {
			return null;
		}
		
	}
	
}
