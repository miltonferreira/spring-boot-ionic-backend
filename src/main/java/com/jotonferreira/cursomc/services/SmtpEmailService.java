package com.jotonferreira.cursomc.services;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class SmtpEmailService extends AbstractEmailService{

	@Autowired
	private MailSender mailSender; // instancia um obj com as infos de acesso ao email de envio do arquivo application-dev.properties
	
	@Autowired
	private JavaMailSender javaMailSender; // usando para processar HMTL
	
	// indica que essa classe que vai enviar o log
		private static final Logger LOG = LoggerFactory.getLogger(SmtpEmailService.class);
	
	@Override
	public void sendEmail(SimpleMailMessage msg) {
		
		LOG.info("Enviando email... ");
		
		mailSender.send(msg); // envia o email para o email do cliente
		
		LOG.info("Email enviado");
		
	}

	@Override
	public void sendHtmlEmail(MimeMessage msg) {
		
		LOG.info("Enviando email HTML... ");
		
		javaMailSender.send(msg); // envia email formatado com HTML para o email do cliente
		
		LOG.info("Email enviado");
		
	}

}
