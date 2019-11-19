package com.jotonferreira.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.jotonferreira.cursomc.services.DBService;

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
	
}
