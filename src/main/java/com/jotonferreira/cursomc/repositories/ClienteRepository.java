package com.jotonferreira.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jotonferreira.cursomc.domain.Cliente;

/*
 	"Camada de acesso a dados (Repository)"
 	Faz operações de acesso a dados no obj "cliente", sendo busca, salvar, alterar, deletar
 	estando mapeado com a tabela "cliente" no banco de dados
 */

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer>{
	
	@Transactional(readOnly = true) // indica que é somente leitura e faz mais rapido a pesquisa
	Cliente findByEmail(String email); // busca no banco de dados um cliente, passando um email como argumento *** metodo implementando pela propria IDE Spring Boot
	
}
