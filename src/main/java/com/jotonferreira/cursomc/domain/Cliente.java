package com.jotonferreira.cursomc.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

//import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.jotonferreira.cursomc.domain.enums.TipoCliente;

/*
"Camada de dominio"

*/

//faz mapeamento da tabela "Endereco" atraves do ID
@Entity
public class Cliente implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)	//geração automatica de ID (chave primaria) dos clientes
	private Integer id;
	private String nome;
	
	@Column(unique = true) // garante que não vai ter repedição de email, caso tente criar outra conta com o email
	private String email;
	
	private String cpfOuCnpj;
	private Integer tipo;					//pega os enums id ***Em vez de pegar um TipoCliente, pega um inteiro
	
	//@JsonManagedReference					//libera a serialização dos endereços ***apagar porque usa o @JsonIgnore 				
	@OneToMany(mappedBy="cliente", cascade = CascadeType.ALL)			//cliente da classe Endereco que fez o mapeamento, qualquer alteração em cliente, reflete em endereços com o CascadeType.ALL, inclusive deletar
	private List<Endereco> enderecos = new ArrayList<>();
	
	//Set é uma coleção e não aceita repetição, sendo um conjunto de strings
	@ElementCollection						
	@CollectionTable(name="TELEFONE")		//tabela auxiliar para guardar os telefones
	private Set<String> telefones = new HashSet<>();
	
	//@JsonBackReference					//Nao permite que pedidos sejam serializados ***apagar porque usa o @JsonIgnore
	@JsonIgnore
	@OneToMany(mappedBy = "cliente")		//mapeamento feito pela classe Pedido
	private List<Pedido> pedidos = new ArrayList<>();
	
	public Cliente() {}

	public Cliente(Integer id, String nome, String email, String cpfOuCnpj, TipoCliente tipo) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.cpfOuCnpj = cpfOuCnpj;
		this.tipo = (tipo == null) ? null : tipo.getCod(); // se o tipo for nulo, atribui nulo, caso contrario add o tipo
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}

	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}
	
	// -------------------------------------------------
	public TipoCliente getTipo() {
		return TipoCliente.toEnum(tipo);
	}

	public void setTipo(TipoCliente tipo) {
		this.tipo = tipo.getCod();
	}
	// --------------------------------------------------

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public Set<String> getTelefones() {
		return telefones;
	}

	public void setTelefones(Set<String> telefones) {
		this.telefones = telefones;
	}
	
	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
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
		Cliente other = (Cliente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
}
