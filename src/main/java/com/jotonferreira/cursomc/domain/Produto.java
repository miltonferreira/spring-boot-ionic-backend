package com.jotonferreira.cursomc.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

/*
"Camada de dominio"

*/

//faz mapeamento da tabela "Produto" atraves do ID
@Entity
public class Produto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)	//geração automatica de ID (chave primaria) das categorias
	private Integer id;
	private String nome;
	private Double preco;
	
	@JsonBackReference						//Omite a lista de categorias para cada produto, já que classe Categoria mostra associados 
	@ManyToMany								//mapear os relacionamentos entre tabelas, nesse caso entre Produto e Categoria
	@JoinTable(name = "PRODUTO_CATEGORIA",	//cria uma tabela intermédiaria entre as tabelas para usar as chaves estrangeiras
		joinColumns = @JoinColumn(name = "produto_id"),
		inverseJoinColumns = @JoinColumn(name = "categoria_id")
	)
	
	private List<Categoria> categorias = new ArrayList<>();	//nome indicado no diagrama de classe
	
	//cria uma coleçao de itens do pedido e evita que tenha produtos repedidos
	private Set<ItemPedido> itens = new HashSet<>();
	
	public Produto() {}
	
	public Produto(Integer id, String nome, Double preco) {
		super();
		this.id = id;
		this.nome = nome;
		this.preco = preco;
	}
	
	//Faz uma lista de itens do pedido
	public List<Pedido> getPedidos(){
		List<Pedido> lista = new ArrayList<>();
		
		for(ItemPedido x : itens) {
			lista.add(x.getPedido());
		}
		
		return lista;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}
	
	public Set<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(Set<ItemPedido> itens) {
		this.itens = itens;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
