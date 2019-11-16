package com.jotonferreira.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.jotonferreira.cursomc.domain.Cliente;
import com.jotonferreira.cursomc.dto.ClienteDTO;
import com.jotonferreira.cursomc.dto.ClienteNewDTO;
import com.jotonferreira.cursomc.services.ClienteService;

/*
	"Controlador REST"
	End Point para ser usado no Json
	Gera um Endpoint/categorias{id} para ser acessado
 */

@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {
	
	//acessa a camada de serviço
	@Autowired
	private ClienteService service;
	
	//Controlador REST, encontra uma categoria com id indicado
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Cliente> find(@PathVariable Integer id) {
		
		Cliente obj = service.find(id);
		return ResponseEntity.ok().body(obj);
		
	}
	
	// HTTP = 201 indica que foi criado nova categoria
	@RequestMapping(method = RequestMethod.POST) // indica que é uma inserção de nova categoria
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO objDto){ // @RequestBody faz obj Json ser convertido para java - @Valid indica que existe requisitos para add nova categoria
			
		Cliente obj = service.fromDTO(objDto); // pegas as infos do CategoriaDTO e cria uma Categoria sem lista de produtos associados
			
		obj = service.insert(obj);
			
		// cria o caminho URL do ID da nova categoria - ex: http://localhost:8080/categorias/3 << 3 é a nova categoria
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
			
		return ResponseEntity.created(uri).build(); // retorna resposta 202 do HTTP
	}
	
	// HTTP = 204 No Content indica que foi atualizado categoria
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO objDto, @PathVariable Integer id){
			
		Cliente obj = service.fromDTO(objDto); // pegas as infos do ClienteDTO e cria uma Cliente sem lista de produtos associados
			
		obj.setId(id); // pega a id da categoria
			
		obj = service.update(obj);
		return ResponseEntity.noContent().build(); // retorna um conteudo vazio por ser void
	}
		
	//Controlador REST, encontra uma categoria com id indicado
		@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
		public ResponseEntity<Void> delete(@PathVariable Integer id) {
				
			service.delete(id); // escolhe id da categoria que vai deletar
				
			return ResponseEntity.noContent().build(); // retorna um conteudo vazio por ser void
				
		}
		
		//Controlador REST, encontra uma lista de categorias
		@RequestMapping(method=RequestMethod.GET)
		public ResponseEntity<List<ClienteDTO>> findAll() {
				
			List<Cliente> list = service.findAll();
				
			List<ClienteDTO> listDtio = list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList()); // transforma categorias em categoriasDto
				
			return ResponseEntity.ok().body(listDtio); // retorna a lista de categoriasDto
				
		}
			
		//Controlador REST, encontra uma lista de categorias
		@RequestMapping(value="/page", method=RequestMethod.GET)
		public ResponseEntity<Page<ClienteDTO>> findPage(
				@RequestParam(value="page", defaultValue="0") Integer page, // @RequestParam é um parametro opcional
				@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, //linesPerPage = linhas por pagina
				@RequestParam(value="orderBy", defaultValue="id") String orderBy, //nome indica qual campo ordena a lista
				@RequestParam(value="direction", defaultValue="ASC") String direction) { // ASC = ordenação ascedente
						
			Page<Cliente> list = service.findPage(page, linesPerPage, orderBy, direction); //
						
			Page<ClienteDTO> listDtio = list.map(obj -> new ClienteDTO(obj)); // transforma categorias em paginação
						
			return ResponseEntity.ok().body(listDtio); // retorna a lista de categoriasDto
						
		}
	
}
