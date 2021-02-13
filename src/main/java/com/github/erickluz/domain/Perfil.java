
package com.github.erickluz.domain;

import java.util.concurrent.ConcurrentHashMap;

public enum Perfil {

	ADMIN(1, "ROLE_ADMIN"),
	FUNCIONARIO(2, "ROLE_FUNCIONARIO");
		
	public int codigo;
	public String descricao;
	public static final ConcurrentHashMap<Integer, Perfil> codigoToEnum = new ConcurrentHashMap<>();
	
	static {
		for(final Perfil perfil : values()) {
			codigoToEnum.put(perfil.codigo, perfil);
		}
	}
	
	Perfil(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public static Perfil fromCodigo(int codigo) {
		return codigoToEnum.get(codigo);
	}
}
