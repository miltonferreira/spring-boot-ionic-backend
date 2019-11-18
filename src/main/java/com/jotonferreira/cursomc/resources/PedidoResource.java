package com.jotonferreira.cursomc.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.jotonferreira.cursomc.domain.Categoria;
import com.jotonferreira.cursomc.domain.Pedido;
import com.jotonferreira.cursomc.dto.CategoriaDTO;
import com.jotonferreira.cursomc.services.PedidoService;

/*
	"Controlador REST"
	End Point para ser usado no Json
	Gera um Endpoint/pedido{id} para ser acessado
 */

@RestController
@RequestMapping(value="/pedidos")	//nome do endPoint
public class PedidoResource {
	
	//acessa a camada de serviço
	@Autowired
	private PedidoService service;
	
	//Controlador REST, encontra uma pedido com id indicado
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Pedido> find(@PathVariable Integer id) {
		
		Pedido obj = service.find(id);							//executa o serviço de buscar a id
		return ResponseEntity.ok().body(obj);
		
	}
	
	// HTTP = 201 indica que foi criado nova pedido
	@RequestMapping(method = RequestMethod.POST) // indica que é uma inserção de nova pedido
	public ResponseEntity<Void> insert(@Valid @RequestBody Pedido obj){ // @RequestBody faz obj Json ser convertido para java - @Valid indica que existe requisitos para add nova pedido
			
			
		obj = service.insert(obj);
			
		// cria o caminho URL do ID da nova pedido - ex: http://localhost:8080/pedidos/3 << 3 é a nova pedido
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
			
		return ResponseEntity.created(uri).build(); // retorna resposta 202 do HTTP
	}
	
}
