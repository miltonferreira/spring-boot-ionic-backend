package com.jotonferreira.cursomc.domain;

import java.util.Date;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.jotonferreira.cursomc.domain.enums.EstadoPagamento;

@Entity
@JsonTypeName("pagamentoComBoleto") // Caso pagamento seja com boleto, indica para instanciar essa classe
public class PagamentoComBoleto extends Pagamento{
	
	private static final long serialVersionUID = 1L;
	
	//Mascara de formataçao para data em json
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date dataVencimento;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date dataPagamento;
	
	public PagamentoComBoleto() {}

	public PagamentoComBoleto(Integer id, EstadoPagamento estado, Pedido pedido, Date dataVencimento, Date dataPagamento) {
		super(id, estado, pedido);
		
		this.dataVencimento = dataVencimento;
		this.dataPagamento = dataPagamento;
		
	}

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	
	
}
