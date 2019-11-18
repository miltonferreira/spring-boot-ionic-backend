package com.jotonferreira.cursomc.services;

import java.util.Date;
import java.util.Optional;

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
	
	//Metodo que procura o obj pelo id indicado
	public Pedido find(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		//Caso não encontre/não exista o id, o "orElseThrow()" lança mensagem de erro personalizada
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! id: " + id + ", Tipo: " + Pedido.class.getName()));	
	}
	
	public Pedido insert(Pedido obj) {
		
		obj.setId(null); // seta para ser um novo pedido
		
		obj.setInstante(new Date()); // instante que foi feito o novo pedido
		
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
			ip.setPreco(produtoService.find(ip.getProduto().getId()).getPreco()); // pega o preço do produto para salvar
			ip.setPedido(obj); // associa o ItemPedido com pedido inserido
			
		}
		
		itemPedidoRepository.saveAll(obj.getItens()); // salva os pedidos no banco de dados
		return obj;
				
	}
	
}
