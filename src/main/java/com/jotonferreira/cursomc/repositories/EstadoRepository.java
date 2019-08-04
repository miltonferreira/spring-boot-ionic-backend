package com.jotonferreira.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jotonferreira.cursomc.domain.Estado;

/*
 	"Camada de acesso a dados (Repository)"
 	Faz operações de acesso a dados no obj "Estado", sendo busca, salvar, alterar, deletar
 	estando mapeado com a tabela Estado no banco de dados
 */

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer>{

}
