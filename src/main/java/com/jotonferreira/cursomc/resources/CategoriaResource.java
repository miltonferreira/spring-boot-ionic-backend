package com.jotonferreira.cursomc.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.jotonferreira.cursomc.domain.Categoria;
import com.jotonferreira.cursomc.services.CategoriaService;

/*
	"Controlador REST"
	End Point para ser usado no Json
	Gera um Endpoint/clientes{id} para ser acessado
 */

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {
	
	//acessa a camada de serviço
	@Autowired
	private CategoriaService service;
	
	//Controlador REST, encontra uma categoria com id indicado
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) {
		
		Categoria obj = service.buscar(id);
		return ResponseEntity.ok().body(obj);
		
	}
	
	// HTTP = 201 indica que foi criado nova categoria
	@RequestMapping(method = RequestMethod.POST) // indica que é uma inserção de nova categoria
	public ResponseEntity<Void> insert(@RequestBody Categoria obj){ // @RequestBody faz obj Json ser convertido para java
		
		obj = service.insert(obj);
		
		// cria o caminho URL do ID da nova categoria - ex: http://localhost:8080/categorias/3 << 3 é a nova categoria
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		
		return ResponseEntity.created(uri).build(); // retorna resposta 202 do HTTP
	}
	
}
