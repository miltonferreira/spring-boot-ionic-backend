package com.jotonferreira.cursomc.domain;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

//faz mapeamento da tabela "ItemPedido" atraves do ID
@Entity
public class ItemPedido implements Serializable{
	
	private static final long serialVersionUID = 1L;

	//indica que essa classe tem como id um obj ItemPedidoPK
	@JsonIgnore		//Nao vai ser serializado pedidos nem produtos
	@EmbeddedId		//É uma (id embutido) numa classe auxilicar
	private ItemPedidoPK id = new ItemPedidoPK();	//acessa "pedido_id" e "produto_id" da classe ItemPedidoPK
	
	private Double desconto;
	private Integer quantidade;
	private Double preco;
	
	public ItemPedido() {}

	public ItemPedido(Pedido pedido, Produto produto, Double desconto, Integer quantidade, Double preco) {
		super();
		
		id.setPedido(pedido);		//Altera o pedido na classe ItemPedidoPK
		id.setProduto(produto);		//Altera o produto na classe ItemPedidoPK
		
		this.desconto = desconto;
		this.quantidade = quantidade;
		this.preco = preco;
	}
	
	// usando get aparece no Json do ItemPedido
	public double getSubtotal() {
		return (preco - desconto) * quantidade;
	}
	
	//Tem acesso ao get do Pedido na classe ItemPedidoPK
	@JsonIgnore								//Nao vai ser serializado. Tudo que começa com get é serializado
	public Pedido getPedido() {
		return id.getPedido();
	}
	
	// recebe as infos do pedido
	public void setPedido(Pedido pedido) {
		id.setPedido(pedido);
	}
	
	//Tem acesso ao get do Produto na classe ItemPedidoPK
	//@JsonIgnore								//Nao vai ser serializado. Tudo que começa com get é serializado
	public Produto getProduto() {
		return id.getProduto();
	}
	
	// recebe as infos do produto
	public void setProduto(Produto produto) {
		id.setProduto(produto);
	}

	public ItemPedidoPK getId() {
		return id;
	}

	public void setId(ItemPedidoPK id) {
		this.id = id;
	}

	public Double getDesconto() {
		return desconto;
	}

	public void setDesconto(Double desconto) {
		this.desconto = desconto;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
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
		ItemPedido other = (ItemPedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() { // formatação de infos que vão aparecer no email de pedido
		
		StringBuilder builder = new StringBuilder();
		
		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR")); // formata para valor monetário do Brasil
		
		builder.append(getProduto().getNome());
		builder.append(", Qte: ");
		builder.append("");
		builder.append(getQuantidade());
		builder.append(", Preço unitário: ");
		builder.append(nf.format(getPreco()));
		builder.append(", Subtotal: ");
		builder.append(nf.format(getSubtotal()));
		builder.append("\n");
				
		return builder.toString();
	}
	
}
