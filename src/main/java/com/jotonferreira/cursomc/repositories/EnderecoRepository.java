package com.jotonferreira.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jotonferreira.cursomc.domain.Endereco;

/*
 	"Camada de acesso a dados (Repository)"
 	Faz operações de acesso a dados no obj "endereco", sendo busca, salvar, alterar, deletar
 	estando mapeado com a tabela "endereco" no banco de dados
 */

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Integer>{

}
