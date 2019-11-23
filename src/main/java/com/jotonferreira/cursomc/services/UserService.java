package com.jotonferreira.cursomc.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.jotonferreira.cursomc.security.UserSS;

public class UserService {
	// pega o usuario logado
	
	// retorna o usuario logado
	public static UserSS authenticated() {
		
		try {
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // pega o usuario que está logado no sistema
		}
		catch (Exception e) {
			return null;
		}
	}
	
	
}
