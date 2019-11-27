package com.jotonferreira.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jotonferreira.cursomc.domain.Estado;
import com.jotonferreira.cursomc.repositories.EstadoRepository;

@Service
public class EstadoService {
	
	@Autowired
	private EstadoRepository repo;
	
	// busca todos os estado no BD
	public List<Estado> findAll(){
		return repo.findAllByOrderByNome();
	}

}
