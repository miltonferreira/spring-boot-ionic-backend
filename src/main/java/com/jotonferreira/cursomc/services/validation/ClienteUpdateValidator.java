package com.jotonferreira.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.jotonferreira.cursomc.domain.Cliente;
import com.jotonferreira.cursomc.dto.ClienteDTO;
import com.jotonferreira.cursomc.repositories.ClienteRepository;
import com.jotonferreira.cursomc.resources.exception.FieldMessage;

// ClienteInsert é o nome da anotação e o ClienteNewDTO é o tipo da classe que aceita a anotação
public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {
	 // Valitator personalizado para esta anotação e para o nosso DTO
	 // Validator, fazendo testes e inserindo as mensagens de erro
	
	@Autowired
	private HttpServletRequest request; // necessário para pegar o ID do novo cliente
	
	@Autowired
	private ClienteRepository repo; // dependencia para a classe
	
	@Override
	public void initialize(ClienteUpdate ann) {}

	@Override
	public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {
		
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = Integer.parseInt(map.get("id")); // pega o ID do novo cliente
		
		List<FieldMessage> list = new ArrayList<>(); // lista para adicionar mensagens de erros
		
		Cliente aux = repo.findByEmail(objDto.getEmail()); // faz pesquisa no BD para checar se o email existe
		
		// evita que atualize um cliente com um email de outro já existe
		if(aux != null && !aux.getId().equals(uriId)) { // se não for diferente de null e o ID não for igual, lança uma exceção.
			list.add(new FieldMessage("email", "Email já existente"));
		}
		
		//percorre as mensagens de erros e envia para a lista de erros do spring boot, que é recebida pela classe ResourceExceptionHandler
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty(); // se a lista estiver vazia retona true, pois não houve erros
	}
}
