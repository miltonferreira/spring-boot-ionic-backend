package com.jotonferreira.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jotonferreira.cursomc.domain.Cidade;
import com.jotonferreira.cursomc.domain.Cliente;
import com.jotonferreira.cursomc.domain.Endereco;
import com.jotonferreira.cursomc.domain.enums.Perfil;
import com.jotonferreira.cursomc.domain.enums.TipoCliente;
import com.jotonferreira.cursomc.dto.ClienteDTO;
import com.jotonferreira.cursomc.dto.ClienteNewDTO;
import com.jotonferreira.cursomc.repositories.ClienteRepository;
import com.jotonferreira.cursomc.repositories.EnderecoRepository;
import com.jotonferreira.cursomc.security.UserSS;
import com.jotonferreira.cursomc.services.exceptions.AuthorizationException;
import com.jotonferreira.cursomc.services.exceptions.DataIntegrityException;
import com.jotonferreira.cursomc.services.exceptions.ObjectNotFoundException;

/*
	"Camada de serviço
	
*/

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo;					//Classe é interface
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private BCryptPasswordEncoder pe; // dependencia que codifica a senha do cliente, o obj esta sendo instanciado na classe SecurityConfig.java quando é requisitado
	
	//Metodo que procura o obj pelo id indicado
	public Cliente find(Integer id) {
		
		UserSS user = UserService.authenticated(); // pega o usuario logado
		
		// se o user for null ou nao for um Adm e o id nao é o id do usuario no BD
		if(user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso Negado");
		}
		
		Optional<Cliente> obj = repo.findById(id);
		//Caso não encontre/não exista o id, o "orElseThrow()" lança mensagem de erro personalizada
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! id: " + id + ", Tipo: " + Cliente.class.getName()));	
	}
	
	// salva uma nova cliente no BD
	@Transactional // como insere o cliente, endereço e telefones, o @Transactional faz de forma transacional, onde manda tudo ou nada
	public Cliente insert(Cliente obj) {
		
		obj.setId(null);
		obj = repo.save(obj); // se tiver um id será uma atualização e não um novo cliente
		
		enderecoRepository.saveAll(obj.getEnderecos()); // salva os endereços do cliente
		
		return obj;
	}
	
	// atualiza a cliente
	public Cliente update(Cliente obj) {
			
		Cliente newObj = find(obj.getId()); // verifica se a categoria existe
		
		updateData(newObj, obj); //pega infos do obj existente e passa para o novo obj
			
		return repo.save(newObj); //atualiza o cliente
	}
		
	private void updateData(Cliente newObj, Cliente obj) {
		// pega somente nome e email do cliente existente para atualizar infos
		
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
		
	}


	public void delete(Integer id) {
			
		find(id); // checa se id existe, caso contrario lança exceção
			
		try {
			repo.deleteById(id);
		}
		catch(DataIntegrityViolationException e) {
			// adiciona uma exceção personalizada
			throw new DataIntegrityException("Não é possível excluir porque existe entidades relacionadas");
		}
	}
		
	// retorna uma lista de categorias
	public List<Cliente> findAll(){
		return repo.findAll();
	}
		
	// retorna uma paginação de categorias
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
			
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
			
	}
		
	// instancia um novo cliente pegando um ClienteDTO existente
	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(),objDto.getEmail(), null, null, null);
		
	}
	
	// instancia um novo cliente pegando um ClienteDTO com infos pessoais, endereço e telefones
	public Cliente fromDTO(ClienteNewDTO objDto) {
		
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()), pe.encode(objDto.getSenha()));
		
		Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
		
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);
		
		cli.getEnderecos().add(end);
		
		cli.getTelefones().add(objDto.getTelefone1());
		
		if (objDto.getTelefone2()!=null) {
			cli.getTelefones().add(objDto.getTelefone2());
		}
		if (objDto.getTelefone3()!=null) {
			cli.getTelefones().add(objDto.getTelefone3());
		}
		
		return cli;
	}
	
}
