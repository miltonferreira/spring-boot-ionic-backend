package com.jotonferreira.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jotonferreira.cursomc.domain.Cliente;
import com.jotonferreira.cursomc.repositories.ClienteRepository;
import com.jotonferreira.cursomc.services.exceptions.ObjectNotFoundException;

/*
	"Camada de serviço
	
*/

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo;					//Classe é interface
	
	//Metodo que procura o obj pelo id indicado
	public Cliente find(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		//Caso não encontre/não exista o id, o "orElseThrow()" lança mensagem de erro personalizada
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! id: " + id + ", Tipo: " + Cliente.class.getName()));	
	}
	
}
