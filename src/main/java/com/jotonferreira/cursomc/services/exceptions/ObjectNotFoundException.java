package com.jotonferreira.cursomc.services.exceptions;

public class ObjectNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ObjectNotFoundException(String msg) {
		super(msg);	//envia mensagem para a classe que usar esse construtor
	}
	
	public ObjectNotFoundException(String msg, Throwable cause) {
		super(msg, cause);	//envia mensagem e causa de algo que aconteceu antes ???
	}
	
}
