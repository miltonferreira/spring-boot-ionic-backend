package com.jotonferreira.cursomc.services;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jotonferreira.cursomc.domain.ItemPedido;
import com.jotonferreira.cursomc.domain.PagamentoComBoleto;
import com.jotonferreira.cursomc.domain.Pedido;
import com.jotonferreira.cursomc.domain.enums.EstadoPagamento;
import com.jotonferreira.cursomc.repositories.ItemPedidoRepository;
import com.jotonferreira.cursomc.repositories.PagamentoRepository;
import com.jotonferreira.cursomc.repositories.PedidoRepository;
import com.jotonferreira.cursomc.services.exceptions.ObjectNotFoundException;

/*
	"Camada de serviço

*/

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repo;					//Classe é interface
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private ClienteService clienteService;
	
	//Metodo que procura o obj pelo id indicado
	public Pedido find(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		//Caso não encontre/não exista o id, o "orElseThrow()" lança mensagem de erro personalizada
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! id: " + id + ", Tipo: " + Pedido.class.getName()));	
	}
	
	@Transactional //anotação para enviar tudo junto
	public Pedido insert(Pedido obj) {
		
		obj.setId(null); // seta para ser um novo pedido
		
		obj.setInstante(new Date()); // instante que foi feito o novo pedido
		
		obj.setCliente(clienteService.find(obj.getCliente().getId())); // pega a id do cliente e busca as infos do cliente inteiro
		
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE); // como é um novo pedido, fica pendente
		
		obj.getPagamento().setPedido(obj); // o pagamento conhece seu pedido
		
		if(obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante()); // gera uma data de vencimento do pagamento em boleto
		}
		
		obj = repo.save(obj); // salva o pedido no banco de dados
		
		pagamentoRepository.save(obj.getPagamento()); // salva o pagamento no banco de dados
		
		// copia todos os produtos do pedido e adiciona no itemPedido para salvar no banco de dados
		for(ItemPedido ip : obj.getItens()) {
			
			ip.setDesconto(0.0);
			ip.setProduto(produtoService.find(ip.getProduto().getId())); // pega infos do produto
			ip.setPreco(ip.getProduto().getPreco()); // pega o preço do produto para salvar
			ip.setPedido(obj); // associa o ItemPedido com pedido inserido
			
		}
		
		itemPedidoRepository.saveAll(obj.getItens()); // salva os pedidos no banco de dados
		
		System.out.println(obj); // imprimi infos do Pedido
		
		return obj;
				
	}
	
}
