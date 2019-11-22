package com.jotonferreira.cursomc.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter{
	// classe que autoriza o usuario acessar o endPoint/Restful
	
	private JWTUtil jwtUtil;
	
	private UserDetailsService userDetailsService; // faz busca de usuario pelo email no BD e compara com o token recebido
	
	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil, UserDetailsService userDetailsService) {
		super(authenticationManager);
		
		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService;
		
	}
	
	// intercepta a requisição de acesso
	@Override
	protected void doFilterInternal(HttpServletRequest request, // pega o cabeçalho da requisição
			HttpServletResponse response, 
			FilterChain chain)  throws IOException, ServletException {
		
		String header = request.getHeader("Authorization"); // pega o token de acesso do usuario
		
		if(header != null && header.startsWith("Bearer ")) {
			
			UsernamePasswordAuthenticationToken auth = getAuthentication(header.substring(7)); // pega o token ignorando o "Bearer "
			
			if(auth != null) {
				SecurityContextHolder.getContext().setAuthentication(auth); // libera o acesso ao restful/EndPoint
			}
		}
		
		chain.doFilter(request, response); // indica que pode continuar a execução da requisição
		
	}

	private UsernamePasswordAuthenticationToken getAuthentication(String token) {
		
		// se o token for valido o usuario pode acessar o endPoint
		if(jwtUtil.tokenValido(token)) {
			
			String username = jwtUtil.getUsername(token); // pega o username dentro do token
			UserDetails user = userDetailsService.loadUserByUsername(username); // busca no BD o email do cliente
			return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()); // 
			
		}
		
		return null;
	}
	

}
