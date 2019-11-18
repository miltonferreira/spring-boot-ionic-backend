package com.jotonferreira.cursomc.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.jotonferreira.cursomc.domain.enums.EstadoPagamento;

//abstract evita que a classe seja instanciada, somente as subClasses sao instanciadas
//faz mapeamento da tabela "Pagamento" atraves do ID
@Entity
@Inheritance(strategy = InheritanceType.JOINED)	//faz tabela separadas para as subClasses
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@type") // classe pagamento vai ter um campo adicional chamado @type
public abstract class Pagamento implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private Integer id;
	private Integer estado;
	
	//@JsonBackReference			//Nao permite que pedidos sejam serializados ***apagar porque usa o @JsonIgnore
	@JsonIgnore
	@OneToOne
	@JoinColumn(name="pedido_id")	//nome da tabela
	@MapsId							//garante que id seja o mesmo para pedido/pagamento
	private Pedido pedido;
	
	public Pagamento() {}

	public Pagamento(Integer id, EstadoPagamento estado, Pedido pedido) {
		super();
		this.id = id;
		this.estado = (estado == null) ? null : estado.getCod();			//integer pega um enum de EstadoPagamento - se o tipo for nulo, atribui nulo, caso contrario add o tipo
		this.pedido = pedido;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public EstadoPagamento getEstado() {
		return EstadoPagamento.toEnum(estado);	//o integer pega um enum de EstadoPagamento	
	}

	public void setEstado(EstadoPagamento estado) {
		this.estado = estado.getCod();			//integer pega um enum de EstadoPagamento	
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
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
		Pagamento other = (Pagamento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}
