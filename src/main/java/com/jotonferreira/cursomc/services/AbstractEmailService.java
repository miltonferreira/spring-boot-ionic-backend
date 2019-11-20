package com.jotonferreira.cursomc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.jotonferreira.cursomc.domain.Pedido;

public abstract class AbstractEmailService implements EmailService{
	// classe para enviar o email para o cliente
	
	@Value("${default.sender}") // pega o email do entregador do pedido no arquivo application.properties
	private String sender;
	
	
	@Override
	public void sendOrderConfirmationEmail(Pedido obj) { // envia o email para o cliente
		
		SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(obj);
		
		sendEmail(sm);
		
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido obj) {
		
		SimpleMailMessage sm = new SimpleMailMessage();
		
		// atributos basicos de um email
		sm.setTo(obj.getCliente().getEmail()); // envia email para o cliente
		sm.setFrom(sender); // email do entregador do pedido
		sm.setSubject("Pedido confirmado! Código: " + obj.getId());
		sm.setSentDate(new Date(System.currentTimeMillis())); // pega data do servidor
		
		// corpo do email
		sm.setText(obj.toString());
		
		return sm;
	}
	
}
