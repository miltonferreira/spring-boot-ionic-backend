package com.jotonferreira.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jotonferreira.cursomc.domain.Cliente;
import com.jotonferreira.cursomc.repositories.ClienteRepository;
import com.jotonferreira.cursomc.security.UserSS;

@Service // indica que é um componente do framework que pode ser injetado em classes
public class UserDetailsServiceImpl implements UserDetailsService{
	// classe busca pelo nome/email do usuario com o UserDetailsService
	
	@Autowired
	private ClienteRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		// busca o cliente pelo email
		Cliente cli = repo.findByEmail(email);
		
		if(cli == null) {
			throw new UsernameNotFoundException(email);
		}
				
		// instancia um novo obj para enviar autenticação de cliente no sistema
		return new UserSS(cli.getId(), cli.getEmail(), cli.getSenha(), cli.getPerfis()); 
	}

}
