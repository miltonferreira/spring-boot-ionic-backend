package com.jotonferreira.cursomc.domain;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonFormat;
//import com.fasterxml.jackson.annotation.JsonManagedReference;

//faz mapeamento da tabela "Pedido" atraves do ID
@Entity
public class Pedido implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)			//geração automatica de ID (chave primaria) dos clientes
	private Integer id;
	
	//Mascara de formataçao para data em json
	@JsonFormat(pattern = "dd/MM/yyyy hh:mm")
	private Date instante;
	
	//evita erro de entidade transiente quando for salva pedido e pagamento
	//@JsonManagedReference										//permite serializar o pagamento
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "pedido") 	//indica que foi mapeado pela classe Pagamento
	private Pagamento pagamento;
	
	//@JsonManagedReference										//permite serializar o cliente
	@ManyToOne
	@JoinColumn(name = "cliente_id")							//nome chave-estrangeira
	private Cliente cliente;
	
	@ManyToOne
	@JoinColumn(name = "endereco_de_entrega")					//nome chave-estrangeira
	private Endereco enderecoDeEntrega;
	
	//cria uma coleçao de itens do pedido e evita que tenha produtos repedidos
	@OneToMany(mappedBy = "id.pedido")		//indica que a classe ItemPedido fez o mapeamento (id embutido)
	private Set<ItemPedido> itens = new HashSet<>();
	
	public Pedido() {}

	public Pedido(Integer id, Date instante, Cliente cliente, Endereco enderecoDeEntrega) {
		super();
		this.id = id;
		this.instante = instante;
		this.cliente = cliente;
		this.enderecoDeEntrega = enderecoDeEntrega;
	}
	
	// soma total do pediddos os itens
	public double getValorTotal() {
		
		double soma = 0.0;
		
		for(ItemPedido ip : itens) {
			soma += ip.getSubtotal();
		}
		
		return soma;
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getInstante() {
		return instante;
	}

	public void setInstante(Date instante) {
		this.instante = instante;
	}

	public Pagamento getPagamento() {
		return pagamento;
	}

	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Endereco getEnderecoDeEntrega() {
		return enderecoDeEntrega;
	}

	public void setEnderecoDeEntrega(Endereco enderecoDeEntrega) {
		this.enderecoDeEntrega = enderecoDeEntrega;
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
		Pedido other = (Pedido) obj;
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
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		
		builder.append("Pedido número: ");
		builder.append(getId());
		builder.append(", Instante: ");
		builder.append(sdf.format(getInstante()));
		builder.append(", Cliente: ");
		builder.append(getCliente().getNome());
		builder.append(", Situação do pagamento: ");
		builder.append(getPagamento().getEstado().getDescricao());
		builder.append("\nDetalhes:\n");
		
		for(ItemPedido ip : getItens()) {
			builder.append(ip.toString());
		}
		
		builder.append("Valor total: ");
		builder.append(nf.format(getValorTotal()));
		
		return builder.toString();
	}
	
	
	
}
