package com.jotonferreira.cursomc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jotonferreira.cursomc.domain.Cidade;

/*
 	"Camada de acesso a dados (Repository)"
 	Faz operações de acesso a dados no obj "cidade", sendo busca, salvar, alterar, deletar
 	estando mapeado com a tabela cidade no banco de dados
 */

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer>{
	
	// Spring Data
	@Transactional(readOnly = true) // indica que é somente leitura e faz mais rapido a pesquisa
	@Query("SELECT obj FROM Cidade obj WHERE obj.estado.id = :estadoId ORDER BY obj.nome") // ordena cidades por nomes
	public List<Cidade> findCidades(@Param("estadoId") Integer estado_id);
	
}
