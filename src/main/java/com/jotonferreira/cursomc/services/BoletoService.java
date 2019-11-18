package com.jotonferreira.cursomc.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.jotonferreira.cursomc.domain.PagamentoComBoleto;

@Service
public class BoletoService {
	// gera uma data de vencimento do pagamento em boleto
	// *** Num caso real, pegaria um webService para gerar o vencimento do boleto ***
	
	public void preencherPagamentoComBoleto(PagamentoComBoleto pagto, Date instantePedido) {
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(instantePedido);
		cal.add(Calendar.DAY_OF_MONTH, 7); // adiciona 7 dias de vencimento para o boleto
		pagto.setDataVencimento(cal.getTime()); // envia a nova data de pagamento
	}

}
