package com.jotonferreira.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jotonferreira.cursomc.domain.Cidade;
import com.jotonferreira.cursomc.repositories.CidadeRepository;

@Service
public class CidadeService {

	@Autowired
	private CidadeRepository repo;
	
	// procura a cidade baseado no id do estado
	public List<Cidade> findByEstado(Integer estadoId){
		return repo.findCidades(estadoId);
	}
	
}
