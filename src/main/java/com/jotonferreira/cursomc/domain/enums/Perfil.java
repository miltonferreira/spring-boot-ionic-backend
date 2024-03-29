package com.jotonferreira.cursomc.domain.enums;

public enum Perfil {
	// perfil para indicar quais acessos o usuario tem dentro do sistema
	
	ADMIN(1, "ROLE_ADMIN"),
	CLIENTE(2, "ROLE_CLIENTE");
	
	private int cod;
	private String descricao;
	
	Perfil(){}

	private Perfil(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}
	
	public int getCod() {
		return cod;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public static Perfil toEnum(Integer cod) {
		
		if(cod == null) { return null;}	//codigo nulo retorna valor nulo
		
		//passa por todos os enumerados
		for(Perfil x : Perfil.values()) {
			if(cod.equals(x.getCod())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Id inválido: " + cod);	//caso não encontre o codigo indicado lança uma excessao
		
	}
	
}
