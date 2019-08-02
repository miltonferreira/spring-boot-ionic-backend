package com.jotonferreira.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jotonferreira.cursomc.domain.Categoria;
import com.jotonferreira.cursomc.repositories.CategoriaRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{
	
	@Autowired
	private CategoriaRepository	categoriaRepository;	//interface responsável por busca, salvar, alterar, deletar informações
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}
	
	
	@Override
	public void run(String... args) throws Exception {
		//Executa criação das categorias com id e nome
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));		//salva as categorias do banco de dados
		
	}

}
