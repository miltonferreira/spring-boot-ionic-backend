package com.jotonferreira.cursomc.resources.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.jotonferreira.cursomc.services.exceptions.AuthorizationException;
import com.jotonferreira.cursomc.services.exceptions.DataIntegrityException;
import com.jotonferreira.cursomc.services.exceptions.FileException;
import com.jotonferreira.cursomc.services.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {
	// mostra exceção personalizada no padrão HTTP com codigo adequado de erro
	
	//recebe a exceção e informações da requisição
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request){
		
		StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(), "Not Found", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err); // retorna o erro fomatado
		
	}
	
	//recebe a exceção e informações da requisição
	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException e, HttpServletRequest request){
			
		StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "Data Integrity", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err); // mostra o erro fomatado em status, msg e tempo
			
	}
	
	//recebe as exceções de validação dos campos de preenchimento
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e, HttpServletRequest request){
				
		ValidationError err = new ValidationError(System.currentTimeMillis(), HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de validação", e.getMessage(), request.getRequestURI());
		
		// pega a lista de exceções em MethodArgumentNotValidException com erros de campos
		for(FieldError x : e.getBindingResult().getFieldErrors()) {
			err.addError(x.getField(), x.getDefaultMessage());
		}
		
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(err); // mostra o erro fomatado em status, msg e tempo
				
	}
	
	//recebe a exceção e informações da requisição
	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity<StandardError> authorization(AuthorizationException e, HttpServletRequest request){
			
		// indica que é um erro de acesso negado
		StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.FORBIDDEN.value(), "Acesso Negado", e.getMessage(), request.getRequestURI()); 
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err); // mostra o erro fomatado em status, msg e tempo
			
	}
	
	//recebe a exceção e informações de erro com arquivos de imagens
	@ExceptionHandler(FileException.class)
	public ResponseEntity<StandardError> file(FileException e, HttpServletRequest request){
				
		StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "Erro de arquivo", e.getMessage(), request.getRequestURI()); 
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err); // 
				
	}
	
	//recebe a exceção e informações de erro com serviço da amazon
	@ExceptionHandler(AmazonServiceException.class)
	public ResponseEntity<StandardError> AmazonService(AmazonServiceException e, HttpServletRequest request){
		
		HttpStatus code = HttpStatus.valueOf(e.getErrorCode()); // pega o codigo http do erro retornado pela amazon
					
		StandardError err = new StandardError(System.currentTimeMillis(), code.value(), "Erro Amazon Service", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(code).body(err); // retorna o erro da amazon
					
	}
	
	//recebe a exceção e informações de erro de acesso do usuario
	@ExceptionHandler(AmazonClientException.class)
	public ResponseEntity<StandardError> amazonClient(AmazonClientException e, HttpServletRequest request){
					
		StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "Erro Amazon Client", e.getMessage(), request.getRequestURI()); 
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err); // 
					
	}
	
	//recebe a exceção e informações de erro de acesso do usuario
	@ExceptionHandler(AmazonS3Exception.class)
	public ResponseEntity<StandardError> amazonS3(AmazonS3Exception e, HttpServletRequest request){
						
		StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "Erro S3", e.getMessage(), request.getRequestURI()); 
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err); // 
						
	}
	
}
