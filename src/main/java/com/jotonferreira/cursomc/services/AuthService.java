package com.jotonferreira.cursomc.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jotonferreira.cursomc.domain.Cliente;
import com.jotonferreira.cursomc.repositories.ClienteRepository;
import com.jotonferreira.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class AuthService {
	// classe para recuperar senha esquecida
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private EmailService emailService;
	
	private Random rand = new Random();
	
	// verifica se email existe
	 public void sendNewPassword(String email) {
		 
		 Cliente cliente = clienteRepository.findByEmail(email); // procura se existe o email no BD
		 
		 if(cliente == null) {
			 throw new ObjectNotFoundException("Email não encontrado");
		 }
		 
		 String newPass = newPassword(); // cria uma nova senha aleatoria
		 cliente.setSenha(pe.encode(newPass)); // troca a senha do cliente pela nova
		 
		 clienteRepository.save(cliente); // salva novamente cliente no BD
		 
		 emailService.sendNewPasswordEmail(cliente, newPass); //
		 
	 }

	// gera senha de 10 caracteres
	private String newPassword() {
		
		char[] vet = new char[10];
		
		for(int i=0; i<10; i++) {
			vet[i] = randomChar(); // gera uma senha randomica
		}
		
		return new String(vet); // retorna a nova senha gerada
	}
	
	// gera uma senha randomica
	private char randomChar() {
		
		int opt = rand.nextInt(3);
		
		if(opt == 0) {
			
			return (char) (rand.nextInt(10) + 48); // gera um digito
			
		} else if(opt == 1) {
			
			return (char) (rand.nextInt(26) + 65); // gera uma letra maiusculo
			
		} else {
			
			return (char) (rand.nextInt(10) + 97); // gera uma letra minuscula
			
		}
		
	}
	 
	 
	
}
