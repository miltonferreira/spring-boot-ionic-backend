package com.jotonferreira.cursomc.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.jotonferreira.cursomc.domain.enums.Perfil;

public class UserSS implements UserDetails{
	private static final long serialVersionUID = 1L;
	// classe para enviar autenticação de cliente no sistema
	
	private Integer id;
	private String email;
	private String senha;
	
	private Collection<? extends GrantedAuthority> authorities; // lista de perfis
	
	public UserSS() {}
	
	public UserSS(Integer id, String email, String senha, Set<Perfil> perfis) {
		super();
		this.id = id;
		this.email = email;
		this.senha = senha;
		this.authorities = perfis.stream().map(x -> new SimpleGrantedAuthority(x.getDescricao())).collect(Collectors.toList()); // pega os perfis e convertem para authorities
	}
	
	public Integer getId() {
		return id;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities; // retorna a lista de perfis
	}

	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true; // indica que a conta não expirou
	}

	@Override
	public boolean isAccountNonLocked() {
		return true; // indica que a conta não está bloqueada
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true; // indica que as credenciais da conta não expirou
	}

	@Override
	public boolean isEnabled() {
		return true; // indica que a conta está ativa
	}
	
	// verifica se é um determninado perfil do sistema *** cliente, admin
	public boolean hasRole(Perfil perfil) {
		return getAuthorities().contains(new SimpleGrantedAuthority(perfil.getDescricao()));
	}

}
