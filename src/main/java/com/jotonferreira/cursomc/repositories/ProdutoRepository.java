package com.jotonferreira.cursomc.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jotonferreira.cursomc.domain.Categoria;
import com.jotonferreira.cursomc.domain.Produto;

/*
 	"Camada de acesso a dados (Repository)"
 	Faz operações de acesso a dados no obj "produto", sendo busca, salvar, alterar, deletar
 	estando mapeado com a tebla categoria no banco de dados
 */

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer>{
	
	// Forma manual usando JPQL para fazer pesquisa de produto --------------------------------------------------------------------------------------------------------------------------
	//@Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cat WHERE obj.nome LIKE %:nome% AND cat IN :categorias") // busca JPQL
	//Page<Produto> search(@Param("nome") String nome, @Param("categorias") List<Categoria>liscategorias, Pageable pageRequest); // definição do metodo de pesquisa de produtos
	// com @Param("@@@@@") indica quais parametros pegar e associar no @Query
	// Forma manual usando JPQL para fazer pesquisa de produto --------------------------------------------------------------------------------------------------------------------------
	
	
	// Forma usando recursos da framework
	// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods - Query prontas do Spring Boot para usar separados ou juntos DistinctBy - ContainingAnd - In
	@Transactional(readOnly = true) // indica que é somente leitura
	Page<Produto> findDistinctByNomeContainingAndCategoriasIn(String nome, List<Categoria>liscategorias, Pageable pageRequest); // definição do metodo de pesquisa de produtos

}
