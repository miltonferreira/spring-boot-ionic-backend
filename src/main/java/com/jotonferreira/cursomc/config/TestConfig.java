package com.jotonferreira.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.jotonferreira.cursomc.services.DBService;
import com.jotonferreira.cursomc.services.EmailService;
import com.jotonferreira.cursomc.services.MockEmailService;

@Configuration
@Profile("test")
public class TestConfig {
	// faz acesso ao banco de dados conforme configuração do profile "test"
	
	@Autowired
	private DBService bdService;
	
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		// instancia o BD no profile de teste
		
		bdService.instantiateTestDatabase(); // cria as infos do Banco de dados de teste
		
		return true;
	}
	
	@Bean // usando Bean essa classe fica disponivel como componenete no sistema, quando alguma classe chamar através do @Autowired, esse metodo é invocado para uso
	public EmailService emailService() {
		return new MockEmailService(); // quando o EmailService for usado na classe PedidoService, se cria a classe abstrata MockEmailService para ser usada
	}
	
}
