package com.jotonferreira.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.jotonferreira.cursomc.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);

}
