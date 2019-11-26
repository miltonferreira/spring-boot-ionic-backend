package com.jotonferreira.cursomc.services.exceptions;

public class FileException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public FileException(String msg) {
		super(msg);	//envia mensagem para a classe que usar esse construtor
	}
	
	public FileException(String msg, Throwable cause) {
		super(msg, cause);	//envia mensagem e causa de algo que aconteceu antes ???
	}
	
}
