package com.jotonferreira.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.jotonferreira.cursomc.domain.Categoria;
import com.jotonferreira.cursomc.domain.Produto;
import com.jotonferreira.cursomc.repositories.CategoriaRepository;
import com.jotonferreira.cursomc.repositories.ProdutoRepository;
import com.jotonferreira.cursomc.services.exceptions.ObjectNotFoundException;

/*
	"Camada de serviço

*/

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository repo;					//Classe é interface
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	//Metodo que procura o obj pelo id indicado
	public Produto find(Integer id) {
		Optional<Produto> obj = repo.findById(id);
		//Caso não encontre/não exista o id, o "orElseThrow()" lança mensagem de erro personalizada
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! id: " + id + ", Tipo: " + Produto.class.getName()));	
	}
	
	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction){
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
		List<Categoria> categorias = categoriaRepository.findAllById(ids); // busca lista de ID conforme pedido pela lista do parametro acima
		
		return repo.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
	}
	
}
