package com.jotonferreira.cursomc.resources.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.jotonferreira.cursomc.services.exceptions.DataIntegrityException;
import com.jotonferreira.cursomc.services.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {
	// mostra exceção personalizada no padrão HTTP com codigo adequado de erro
	
	//recebe a exceção e informações da requisição
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request){
		
		StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err); // mostra o erro fomatado em status, msg e tempo
		
	}
	
	//recebe a exceção e informações da requisição
		@ExceptionHandler(DataIntegrityException.class)
		public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException e, HttpServletRequest request){
			
			StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err); // mostra o erro fomatado em status, msg e tempo
			
		}
	
}
