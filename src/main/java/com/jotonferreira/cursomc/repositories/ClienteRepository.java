package com.jotonferreira.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jotonferreira.cursomc.domain.Cliente;

/*
 	"Camada de acesso a dados (Repository)"
 	Faz operações de acesso a dados no obj "cliente", sendo busca, salvar, alterar, deletar
 	estando mapeado com a tabela "cliente" no banco de dados
 */

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer>{

}
