package com.jotonferreira.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jotonferreira.cursomc.domain.Categoria;
import com.jotonferreira.cursomc.repositories.CategoriaRepository;

/*
	"Camada de serviço

*/

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repo;					//Classe é interface
	
	//Metodo que procura o obj pelo id indicado
	public Categoria buscar(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElse(null);
	}
	
}
