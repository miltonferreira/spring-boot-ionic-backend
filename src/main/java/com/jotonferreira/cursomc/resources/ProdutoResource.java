package com.jotonferreira.cursomc.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jotonferreira.cursomc.domain.Produto;
import com.jotonferreira.cursomc.dto.ProdutoDTO;
import com.jotonferreira.cursomc.resources.utils.URL;
import com.jotonferreira.cursomc.services.ProdutoService;

/*
	"Controlador REST"
	End Point para ser usado no Json
	Gera um Endpoint/pedido{id} para ser acessado
 */

@RestController
@RequestMapping(value="/produtos")	//nome do endPoint
public class ProdutoResource {
	
	//acessa a camada de serviço
	@Autowired
	private ProdutoService service;
	
	//Controlador REST, encontra uma categoria com id indicado
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Produto> find(@PathVariable Integer id) {
		
		Produto obj = service.find(id);							//executa o serviço de buscar a id
		return ResponseEntity.ok().body(obj);
		
	}
	
	//Controlador REST, encontra uma lista de categorias
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> findPage(
			@RequestParam(value="nome", defaultValue="") String nome, // nome do produto pesquisado//
			@RequestParam(value="categorias", defaultValue="") String categorias, // categoria do produto pesquisado
			@RequestParam(value="page", defaultValue="0") Integer page, // @RequestParam é um parametro opcional
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, //linesPerPage = linhas por pagina
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy, //nome indica qual campo ordena a lista
			@RequestParam(value="direction", defaultValue="ASC") String direction) { // ASC = ordenação ascedente
		
		String nomeDecoded = URL.DecodeParam(nome); // nome do produto pesquisado
		List<Integer> ids = URL.decodeInList(categorias); // categoria do produto pesquisado
		
		Page<Produto> list = service.search(nomeDecoded, ids, page, linesPerPage, orderBy, direction); //
						
		Page<ProdutoDTO> listDtio = list.map(obj -> new ProdutoDTO(obj)); // transforma categorias em paginação
						
		return ResponseEntity.ok().body(listDtio); // retorna a lista de categoriasDto
						
	}
	
}
