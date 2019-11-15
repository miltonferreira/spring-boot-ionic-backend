package com.jotonferreira.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.jotonferreira.cursomc.domain.Categoria;
import com.jotonferreira.cursomc.repositories.CategoriaRepository;
import com.jotonferreira.cursomc.services.exceptions.DataIntegrityException;
import com.jotonferreira.cursomc.services.exceptions.ObjectNotFoundException;

/*
	"Camada de serviço

*/

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repo;					//Classe é interface
	
	// Metodo que procura o obj pelo id indicado
	public Categoria find(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		//Caso não encontre/não exista o id, o "orElseThrow()" lança mensagem de erro personalizada
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! id: " + id + ", Tipo: " + Categoria.class.getName()));	
	}
	
	// salva uma nova categoria no BD
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repo.save(obj); // se tiver um id será uma atualização e não uma nova categoria
	}
	
	// atualiza a categoria
	public Categoria update(Categoria obj) {
		
		find(obj.getId()); // verifica se a categoria existe
		
		return repo.save(obj); //atualiza a categoria
	}
	
	public void delete(Integer id) {
		
		find(id); // checa se id existe, caso contrario lança exceção
		
		try {
			repo.deleteById(id);
		}
		catch(DataIntegrityViolationException e) {
			// adiciona uma exceção personalizada
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos");
		}
	}
	
	// retorna uma lista de categorias
	public List<Categoria> findAll(){
		return repo.findAll();
	}
	
	// retorna uma paginação de categorias
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
		
	}
	
}
