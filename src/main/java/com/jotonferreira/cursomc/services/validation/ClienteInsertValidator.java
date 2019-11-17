package com.jotonferreira.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.jotonferreira.cursomc.domain.Cliente;
import com.jotonferreira.cursomc.domain.enums.TipoCliente;
import com.jotonferreira.cursomc.dto.ClienteNewDTO;
import com.jotonferreira.cursomc.repositories.ClienteRepository;
import com.jotonferreira.cursomc.resources.exception.FieldMessage;
import com.jotonferreira.cursomc.services.validation.utils.BR;

// ClienteInsert é o nome da anotação e o ClienteNewDTO é o tipo da classe que aceita a anotação
public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
	 // Valitator personalizado para esta anotação e para o nosso DTO
	 // Validator, fazendo testes e inserindo as mensagens de erro
	
	@Autowired
	private ClienteRepository repo; // dependencia para a classe
	
	@Override
	public void initialize(ClienteInsert ann) {}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		
		List<FieldMessage> list = new ArrayList<>(); // lista para adicionar mensagens de erros

		// verifica se o CPF é valido
		if(objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CPF inválido")); // se não for valido, lança um exceção na lista
		}
		
		// verifica se o CNPJ é valido
		if(objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido")); // se não for valido, lança um exceção na lista
		}
		
		Cliente aux = repo.findByEmail(objDto.getEmail()); // faz pesquisa no BD para checar se o email existe
		
		if(aux != null) { // se for diferente de null, lança uma exceção
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
