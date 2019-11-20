package com.jotonferreira.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.jotonferreira.cursomc.services.DBService;
import com.jotonferreira.cursomc.services.EmailService;
import com.jotonferreira.cursomc.services.SmtpEmailService;

@Configuration
@Profile("dev")
public class DevConfig {
	// faz acesso ao banco de dados conforme configuração do profile "test"
	
	@Autowired
	private DBService bdService;
	
	// https://stackoverflow.com/questions/21113154/spring-boot-ddl-auto-generator
	// none = não mexe no banco de dados - create = recria tudo novamente
	@Value("${spring.jpa.hibernate.ddl-auto}") // pega o valor da chave de configuração do BD
	private String strategy; // recebe o valor do comando acima
	
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		// instancia o BD no profile de teste
		
		if(!"create".equals(strategy)) { // caso seja diferente de create, não deixa criar novamente o BD com as infos
			return false;
		}
		
		bdService.instantiateTestDatabase(); // cria as infos do Banco de dados de teste
		
		return true;
	}
	
	@Bean // usando Bean essa classe fica disponivel como componenete no sistema, quando alguma classe chamar através do @Autowired, esse metodo é invocado para uso
	public EmailService emailService() {
		return new SmtpEmailService(); // quando o EmailService for usado na classe PedidoService, se cria a classe SmtpEmailService para ser usada
	}
	
}
