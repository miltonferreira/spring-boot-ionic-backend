package com.jotonferreira.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jotonferreira.cursomc.domain.ItemPedido;

/*
 	"Camada de acesso a dados (Repository)"
 	Faz operações de acesso a dados no obj "itemPedido", sendo busca, salvar, alterar, deletar
 	estando mapeado com a tebla categoria no banco de dados
 */

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Integer>{

}
