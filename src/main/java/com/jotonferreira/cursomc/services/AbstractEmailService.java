package com.jotonferreira.cursomc.services;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.jotonferreira.cursomc.domain.Pedido;

public abstract class AbstractEmailService implements EmailService{
	// classe para enviar o email para o cliente
	
	@Value("${default.sender}") // pega o email do entregador do pedido no arquivo application.properties
	private String sender;
	
	@Autowired
	private TemplateEngine templateEngine; // processa o html
	
	@Autowired
	private JavaMailSender javaMailSender; // 
	
	
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
	
	// retorna o html em forma de string
	protected String htmlFromTemplatePedido(Pedido obj) {
		
		Context context = new Context();
		
		context.setVariable("pedido", obj); // passa as infos do cliente para o template/email/confirmacaoPedido.html
		
		return templateEngine.process("email/confirmacaoPedido", context); // retorna o html em forma de string
		
	}
	
	@Override
	public void sendOrderConfirmationHtmlEmail(Pedido obj) {
		
		try {
			
			// envia formatado em HTML
			MimeMessage mm = prepareMimeMessageFromPedido(obj); //
			sendHtmlEmail(mm);
			
		}
		catch(MessagingException e) {
			
			// envia formatado somente String/Texto
			sendOrderConfirmationEmail(obj); // envia o email plano
			
		}
		
		
		
	}

	protected MimeMessage prepareMimeMessageFromPedido(Pedido obj) throws MessagingException {
		
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
		
		mmh.setTo(obj.getCliente().getEmail()); // envia email para o cliente
		
		mmh.setFrom(sender); // remetente do email, quem envia o email
		
		mmh.setSubject("Pedido confirmado! Código: " + obj.getId());
		mmh.setSentDate(new Date(System.currentTimeMillis()));
		
		mmh.setText(htmlFromTemplatePedido(obj), true); // corpo em HTML do email, true indica que é um html
		
		
		return mimeMessage;
	}
	
}
