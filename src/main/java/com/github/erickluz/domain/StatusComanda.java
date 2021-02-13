
package com.github.erickluz.domain;

public enum StatusComanda {

	ABERTA(0, "Aberta"),
	FECHADA(1, "Fechada");
		
	public int codigo;
	public String descricao;
	
	StatusComanda(int codigo, String descricao) {
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
	
}
