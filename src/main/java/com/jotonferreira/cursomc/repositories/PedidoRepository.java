package com.jotonferreira.cursomc.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jotonferreira.cursomc.domain.Cliente;
import com.jotonferreira.cursomc.domain.Pedido;

/*
 	"Camada de acesso a dados (Repository)"
 	Faz operações de acesso a dados no obj "categoria", sendo busca, salvar, alterar, deletar
 	estando mapeado com a tebla categoria no banco de dados
 */

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
	
	@Transactional(readOnly = true) // indica que é somente leitura e faz mais rapido a pesquisa
	Page<Pedido> findByCliente(Cliente cliente, Pageable pageRequest); // retorna os pedidos do cliente paginado
	
}
