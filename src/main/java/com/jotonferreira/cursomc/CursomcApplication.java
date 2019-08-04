package com.jotonferreira.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jotonferreira.cursomc.domain.Categoria;
import com.jotonferreira.cursomc.domain.Cidade;
import com.jotonferreira.cursomc.domain.Estado;
import com.jotonferreira.cursomc.domain.Produto;
import com.jotonferreira.cursomc.repositories.CategoriaRepository;
import com.jotonferreira.cursomc.repositories.CidadeRepository;
import com.jotonferreira.cursomc.repositories.EstadoRepository;
import com.jotonferreira.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{
	
	@Autowired
	private CategoriaRepository	categoriaRepository;	//interface responsável por busca, salvar, alterar, deletar informações
	@Autowired
	private ProdutoRepository	produtoRepository;		//interface responsável por busca, salvar, alterar, deletar informações
	@Autowired
	private EstadoRepository	estadoRepository;		//interface responsável por busca, salvar, alterar, deletar informações
	@Autowired
	private CidadeRepository	cidadeRepository;		//interface responsável por busca, salvar, alterar, deletar informações
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}
	
	
	@Override
	public void run(String... args) throws Exception {
		//Executa criação das categorias com id e nome
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));		//salva as categorias no banco de dados
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "mouse", 80.00);
		
		//Categorias sabem quais produtos tem
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));			//produtos associados a categoria informatica
		cat2.getProdutos().addAll(Arrays.asList(p2));				//produtos associados a categoria escritorio
		
		//Produtos sabem quais categorias pertencem
		p1.getCategorias().addAll(Arrays.asList(cat1));				//Computador pertence a categoria informatica
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));		//impressora pertence a categoria informatica e escritorio
		p3.getCategorias().addAll(Arrays.asList(cat1));				//mouse pertence a categoria informatica
		
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));		//salva os produtos no banco de dados
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "Sao Paulo");
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));		//salva os estados no banco de dados
		
		Cidade c1 = new Cidade(null, "Uberlandia", est1);
		Cidade c2 = new Cidade(null, "Sao Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));				//Faz o estado saber quais cidades possui
		est2.getCidades().addAll(Arrays.asList(c2, c3));			//Faz o estado saber quais cidades possui
		
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));		//salva as cidades no banco de dados
		
	}

}
