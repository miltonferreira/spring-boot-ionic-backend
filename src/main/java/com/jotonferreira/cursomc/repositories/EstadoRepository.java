package com.jotonferreira.cursomc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jotonferreira.cursomc.domain.Estado;



/*
 	"Camada de acesso a dados (Repository)"
 	Faz operações de acesso a dados no obj "Estado", sendo busca, salvar, alterar, deletar
 	estando mapeado com a tabela Estado no banco de dados
 */

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer>{
	
	// findAllByOrderBy é um padrão de busca do spring nossa lista vai procurar os estados por nome
	@Transactional(readOnly = true) // indica que é somente leitura e faz mais rapido a pesquisa
	public List<Estado> findAllByOrderByNome();

}
