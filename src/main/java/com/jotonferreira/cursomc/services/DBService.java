package com.jotonferreira.cursomc.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jotonferreira.cursomc.domain.Categoria;
import com.jotonferreira.cursomc.domain.Cidade;
import com.jotonferreira.cursomc.domain.Cliente;
import com.jotonferreira.cursomc.domain.Endereco;
import com.jotonferreira.cursomc.domain.Estado;
import com.jotonferreira.cursomc.domain.ItemPedido;
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
import com.jotonferreira.cursomc.repositories.ItemPedidoRepository;
import com.jotonferreira.cursomc.repositories.PagamentoRepository;
import com.jotonferreira.cursomc.repositories.PedidoRepository;
import com.jotonferreira.cursomc.repositories.ProdutoRepository;

@Service
public class DBService {
	
	@Autowired
	private CategoriaRepository	categoriaRepository;			//interface responsável por busca, salvar, alterar, deletar informações
	@Autowired
	private ProdutoRepository	produtoRepository;				//interface responsável por busca, salvar, alterar, deletar informações
	@Autowired
	private EstadoRepository	estadoRepository;				//interface responsável por busca, salvar, alterar, deletar informações
	@Autowired
	private CidadeRepository	cidadeRepository;				//interface responsável por busca, salvar, alterar, deletar informações
	@Autowired
	private ClienteRepository	clienteRepository;				//interface responsável por busca, salvar, alterar, deletar informações
	@Autowired
	private EnderecoRepository	enderecoRepository;				//interface responsável por busca, salvar, alterar, deletar informações
	@Autowired
	private PagamentoRepository	pagamentoRepository;			//interface responsável por busca, salvar, alterar, deletar informações
	@Autowired
	private PedidoRepository	pedidoRepository;				//interface responsável por busca, salvar, alterar, deletar informações
	@Autowired
	private ItemPedidoRepository	itemPedidoRepository;		//interface responsável por busca, salvar, alterar, deletar informações
	
	public void instantiateTestDatabase() throws ParseException{
		
		//Executa criação das categorias com id e nome
		
				Categoria cat1 = new Categoria(null, "Informática");
				Categoria cat2 = new Categoria(null, "Escritório");
				Categoria cat3 = new Categoria(null, "Cama mesa e banho");
				Categoria cat4 = new Categoria(null, "Eletrönicos");
				Categoria cat5 = new Categoria(null, "Jardinagem");
				Categoria cat6 = new Categoria(null, "Decoração");
				Categoria cat7 = new Categoria(null, "Perfumaria");
				
				categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));		//salva as categorias no banco de dados
				
				Produto p1 = new Produto(null, "Computador", 2000.00);
				Produto p2 = new Produto(null, "Impressora", 800.00);
				Produto p3 = new Produto(null, "Mouse", 80.00);
				Produto p4 = new Produto(null, "Mesa de escritório", 300.00);
				Produto p5 = new Produto(null, "Toalha", 50.00);
				Produto p6 = new Produto(null, "Colcha", 200.00);
				Produto p7 = new Produto(null, "Tv true color", 1200.00);
				Produto p8 = new Produto(null, "Roçadeira", 800.00);
				Produto p9 = new Produto(null, "Abajour", 100.00);
				Produto p10 = new Produto(null, "Pendente", 180.00);
				Produto p11 = new Produto(null, "Shampoo", 90.00);
				
				//Categorias sabem quais produtos tem
				cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));			// produtos associados a categoria informatica
				cat2.getProdutos().addAll(Arrays.asList(p2,p4));			// produtos associados a categoria escritorio
				cat3.getProdutos().addAll(Arrays.asList(p5,p6));			// produtos associados a categoria Cama mesa e banho
				cat4.getProdutos().addAll(Arrays.asList(p1,p2,p3,p7));		// produtos associados a categoria Eletrönicos
				cat5.getProdutos().addAll(Arrays.asList(p8));				// produtos associados a categoria Jardinagem
				cat6.getProdutos().addAll(Arrays.asList(p9,p10));			// produtos associados a categoria Decoração
				cat7.getProdutos().addAll(Arrays.asList(p11));				// produtos associados a categoria Perfumaria
				
				//Produtos sabem quais categorias pertencem
				p1.getCategorias().addAll(Arrays.asList(cat1,cat4));		//
				p2.getCategorias().addAll(Arrays.asList(cat1, cat2, cat4));	//
				p3.getCategorias().addAll(Arrays.asList(cat1, cat4));		//
				p4.getCategorias().addAll(Arrays.asList(cat2));				//
				p5.getCategorias().addAll(Arrays.asList(cat3));				//
				p6.getCategorias().addAll(Arrays.asList(cat3));				//
				p7.getCategorias().addAll(Arrays.asList(cat4));				//
				p8.getCategorias().addAll(Arrays.asList(cat5));				//
				p9.getCategorias().addAll(Arrays.asList(cat6));				//
				p10.getCategorias().addAll(Arrays.asList(cat6));			//
				p11.getCategorias().addAll(Arrays.asList(cat7));			//
				
				
				produtoRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));		//salva os produtos no banco de dados
				
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
				
				ItemPedido ip1 = new ItemPedido(ped1, p1, 0.0, 1, 2000.00);
				ItemPedido ip2 = new ItemPedido(ped1, p3, 0.0, 2, 80.00);
				ItemPedido ip3 = new ItemPedido(ped2, p2, 100.0, 1, 800.00);
				
				ped1.getItens().addAll(Arrays.asList(ip1, ip2));				//indica quais sao os produtos do pedido 1
				ped2.getItens().addAll(Arrays.asList(ip3));						//indica quais sao os produtos do pedido 2
				
				p1.getItens().addAll(Arrays.asList(ip1));						//produto conhece seu pedido
				p2.getItens().addAll(Arrays.asList(ip3));						//produto conhece seu pedido
				p3.getItens().addAll(Arrays.asList(ip2));						//produto conhece seu pedido
				
				itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));		//Salva ItensPedidos no banco de dados
		
	}

}
