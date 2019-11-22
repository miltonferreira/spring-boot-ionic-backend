package com.jotonferreira.cursomc.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jotonferreira.cursomc.dto.CredenciaisDTO;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	// filtro de autenticação para verificar se o usuario/email e senha estão corretos
	// endPoint /login já reservado pelo spring security
	
	private AuthenticationManager authenticationManager;
	
	private JWTUtil jwtUtil;
	
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
		setAuthenticationFailureHandler(new JWTAuthenticationFailureHandler()); // retorna que houve erro de autenticação do email ou senha
		this.authenticationManager =authenticationManager;
		this.jwtUtil = jwtUtil;
	}
	
	@Override // metodo tenta autenticar usuario
    public Authentication attemptAuthentication(HttpServletRequest  req, HttpServletResponse res) throws AuthenticationException {
		// pega email e senha no json do POST
		
		try {
			CredenciaisDTO creds = new ObjectMapper()
	                .readValue(req.getInputStream(), CredenciaisDTO.class); // tenta pegar no objeto de requisição o email e senha, convertendo para CredenciaisDTO.java
			
			// instancia um obj com as infos sendo um token do spring security
	        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getSenha(), new ArrayList<>());
	        
	        
	        Authentication auth = authenticationManager.authenticate(authToken); // verifica se usuario/email e senha são validos
	        return auth;
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	@Override // caso a autenticação seja bem sucedida, esse metodo entra em ação gerando um token
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
		
		String username = ((UserSS) auth.getPrincipal()).getUsername(); // retorna um usuario do spring security, pegando o email de quem fez o login
        String token = jwtUtil.generateToken(username); // pega o token gerado do usuario
        res.addHeader("Authorization", "Bearer " + token); // acrescenta o token como cabeçalho da resposta
		
	}
	
	private class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler {
		 
        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
                throws IOException, ServletException {
            response.setStatus(401);
            response.setContentType("application/json"); 
            response.getWriter().append(json());
        }
        
        private String json() {
            long date = new Date().getTime();
            return "{\"timestamp\": " + date + ", "
                + "\"status\": 401, " //401 é o log padrão para erro ao digitar login ou senha
                + "\"error\": \"Não autorizado\", "
                + "\"message\": \"Email ou senha inválidos\", "
                + "\"path\": \"/login\"}";
        }
    }
	
}
