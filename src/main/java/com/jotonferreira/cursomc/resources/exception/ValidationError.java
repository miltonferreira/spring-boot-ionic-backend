package com.jotonferreira.cursomc.resources.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {
	private static final long serialVersionUID = 1L;
	
	// classe personalizada para lança erros de validação, mostrando o que é exigido
	
	private List<FieldMessage> errors = new ArrayList<>(); // acrescenta os erros dentro da lista
	
	public ValidationError(Long timestamp, Integer status, String error, String message, String path) {
		super(timestamp, status, error, message, path);
	}

	public List<FieldMessage> getErrors() {
		return errors;
	}
	
	// add o erro na lista
	public void addError(String fieldName, String message) {
		errors.add(new FieldMessage(fieldName, message));
	}

}
