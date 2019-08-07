package com.jotonferreira.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jotonferreira.cursomc.domain.Categoria;
import com.jotonferreira.cursomc.domain.Cidade;
import com.jotonferreira.cursomc.domain.Cliente;
import com.jotonferreira.cursomc.domain.Endereco;
import com.jotonferreira.cursomc.domain.Estado;
import com.jotonferreira.cursomc.domain.Pagamento;
import com.jotonferreira.cursomc.domain.PagamentoComBoleto;
import com.jotonferreira.cursomc.domain.PagamentoComCartao;
import com.jotonferreira.cursomc.domain.Pedido;
import com.jotonferreira.cursomc.domain.Produto;
import com.jotonferreira.cursomc.domain.enums.EstadoPagamento;
import com.jotonferreira.cursomc.domain.enums.TipoCliente;
import com.jotonferreira.cursomc.repositories.CategoriaRepository;
import com.jotonferreira.cursomc.repositories.CidadeRepository;
import com.jotonferreira.cursomc.repositories.ClienteRepository;
import com.jotonferreira.cursomc.repositories.EnderecoRepository;
import com.jotonferreira.cursomc.repositories.EstadoRepository;
import com.jotonferreira.cursomc.repositories.PagamentoRepository;
import com.jotonferreira.cursomc.repositories.PedidoRepository;
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
	@Autowired
	private ClienteRepository	clienteRepository;		//interface responsável por busca, salvar, alterar, deletar informações
	@Autowired
	private EnderecoRepository	enderecoRepository;		//interface responsável por busca, salvar, alterar, deletar informações
	@Autowired
	private PagamentoRepository	pagamentoRepository;	//interface responsável por busca, salvar, alterar, deletar informações
	@Autowired
	private PedidoRepository	pedidoRepository;		//interface responsável por busca, salvar, alterar, deletar informações
	
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
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "36378912377", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("27363323", "93838393"));
		
		clienteRepository.saveAll(Arrays.asList(cli1));				//salva os clientes no banco de dados
		
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));			//o cliente conhece seus enderecos, mas enderecos nao conhece clientes
		
		enderecoRepository.saveAll(Arrays.asList(e1, e2));			//salva os enderecos no banco de dados
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");	//mascara de formataçao para data e hora
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);	//indica que pagto1 é de ped1
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
		ped2.setPagamento(pagto2);	//indica que pagto2 é de ped2
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));			//indica quais sao os pedidos do cliente
		
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));			//salva os pedidos no banco de dados
		
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));		//salva os pagamentos no banco de dados
		
	}

}
