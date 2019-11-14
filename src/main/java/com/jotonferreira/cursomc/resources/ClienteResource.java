package com.jotonferreira.cursomc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jotonferreira.cursomc.domain.Cliente;
import com.jotonferreira.cursomc.services.ClienteService;

/*
	"Controlador REST"
	End Point para ser usado no Json
	Gera um Endpoint/categorias{id} para ser acessado
 */

@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {
	
	//acessa a camada de serviço
	@Autowired
	private ClienteService service;
	
	//Controlador REST, encontra uma categoria com id indicado
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Cliente> find(@PathVariable Integer id) {
		
		Cliente obj = service.find(id);
		return ResponseEntity.ok().body(obj);
		
	}
	
}
