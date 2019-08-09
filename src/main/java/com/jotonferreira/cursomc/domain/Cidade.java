package com.jotonferreira.cursomc.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

//import com.fasterxml.jackson.annotation.JsonManagedReference;

/*
"Camada de dominio"

*/

//faz mapeamento da tabela "Cidade" atraves do ID
@Entity
public class Cidade implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)	//geração automatica de ID (chave primaria) das cidades
	private Integer id;
	private String nome;
	
	//Mapeamento para banco de dados
	//@JsonManagedReference				//libera a serialização do estado ***apagar porque usa o @JsonIgnore
	@ManyToOne
	@JoinColumn(name="estado_id")
	private Estado estado;				//Cidade tem somente um estado, por isso nao tem lista
	
	public Cidade() {}

	public Cidade(Integer id, String nome, Estado estado) {
		super();
		this.id = id;
		this.nome = nome;
		this.estado = estado;
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

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
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
		Cidade other = (Cidade) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}
