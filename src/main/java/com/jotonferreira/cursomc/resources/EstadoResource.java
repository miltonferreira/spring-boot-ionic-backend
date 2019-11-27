package com.jotonferreira.cursomc.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jotonferreira.cursomc.domain.Cidade;
import com.jotonferreira.cursomc.domain.Estado;
import com.jotonferreira.cursomc.dto.CidadeDTO;
import com.jotonferreira.cursomc.dto.EstadoDTO;
import com.jotonferreira.cursomc.services.CidadeService;
import com.jotonferreira.cursomc.services.EstadoService;

@RestController
@RequestMapping(value="/estados")
public class EstadoResource {
	
	@Autowired
	private EstadoService service;
	
	@Autowired
	private CidadeService cidadeService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<EstadoDTO>> findAll(){
		
		List<Estado> list = service.findAll(); // pega toda lista de estados
		
		List<EstadoDTO> listDto = list.stream().map(obj -> new EstadoDTO(obj)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(listDto); // retorna o estadoDto como resposta
		
	}
	
	@RequestMapping(value="/{estadoId}/cidades", method = RequestMethod.GET)
	public ResponseEntity<List<CidadeDTO>> findCidades(@PathVariable Integer estadoId){
		
		List<Cidade> list = cidadeService.findByEstado(estadoId); // pega toda lista de estados
		
		List<CidadeDTO> listDto = list.stream().map(obj -> new CidadeDTO(obj)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(listDto); // retorna o CidadeDTO como resposta
		
	}
	
	

}
