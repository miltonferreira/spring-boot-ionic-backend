package com.jotonferreira.cursomc.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.jotonferreira.cursomc.security.JWTAuthenticationFilter;
import com.jotonferreira.cursomc.security.JWTAuthorizationFilter;
import com.jotonferreira.cursomc.security.JWTUtil;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	// classe de autenticação para acessar os restful
	
	@Autowired
	private Environment env; // dependencia para acessaro BD do H2
	
	@Autowired
	private UserDetailsService userDetailsService; // busca a implementação da interface sendo a classe UserDetailsServiceImpl.java
	
	@Autowired
	private JWTUtil jwtUtil;

	// caminhos que estarão liberados por padrão
	private static final String[] PUBLIC_MATCHERS = { "/h2-console/**"};
	
	// caminhos que estarão liberados para recuperar e ler os dados
		private static final String[] PUBLIC_MATCHERS_GET = { "/produtos/**", "/categorias/**", "/clientes/**" };

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// acesso ou não aos restful's conforme antMatchers
		
		if(Arrays.asList(env.getActiveProfiles()).contains("test")) { // se o profile estiver no test
			http.headers().frameOptions().disable(); // libera acesso ao H2
		}
		
		http.cors().and().csrf().disable(); // permite multiplo acesso com web e mobile
		
		http.authorizeRequests()
			.antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll() // o que tiver em PUBLIC_MATCHERS_GET tem permissão somente leitura
			.antMatchers(PUBLIC_MATCHERS).permitAll() // o que tiver em PUBLIC_MATCHERS tem permissão
			.anyRequest().authenticated(); // o resto exige autenticação
		
		// authenticationManager() esta dentro da classe WebSecurityConfigurerAdapter
		http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil));
		
		// usado para autorizar acesso ao endPoint/restful requisitado
		http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService));
		
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // garante que o backEnd não vai criar sessão de usuario

	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder()); // indica quem é o usuario e senha para fazer autenticação
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues()); // acesso básico de multiplas fontes, para todos os caminhos em "/**"
		return source;
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new  BCryptPasswordEncoder(); // codificador de senha
	}

}
