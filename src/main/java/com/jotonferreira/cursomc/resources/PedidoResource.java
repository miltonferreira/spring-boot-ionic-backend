package com.jotonferreira.cursomc.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jotonferreira.cursomc.domain.Pedido;
import com.jotonferreira.cursomc.services.PedidoService;

/*
	"Controlador REST"
	End Point para ser usado no Json
	Gera um Endpoint/pedido{id} para ser acessado
 */

@RestController
@RequestMapping(value="/pedidos")	//nome do endPoint
public class PedidoResource {
	
	//acessa a camada de serviço
	@Autowired
	private PedidoService service;
	
	//Controlador REST, encontra uma categoria com id indicado
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) {
		
		Pedido obj = service.buscar(id);							//executa o serviço de buscar a id
		return ResponseEntity.ok().body(obj);
		
	}
	
}
