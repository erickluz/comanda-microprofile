package com.github.erickluz.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.github.erickluz.domain.Produto;

public class ProdutoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long idProduto;
	private String nome;
	private BigDecimal preco;
	private Long idUsuario;
	
	public ProdutoDTO() {
		
	}

	public ProdutoDTO(Long idProduto, String nome, BigDecimal preco, Long idUsuario) {
		this.idProduto = idProduto;
		this.nome = nome;
		this.preco = preco;
		this.idUsuario = idUsuario;
	}

	public ProdutoDTO(Produto produto) {
		if (produto == null) {
			return;
		}
		this.idProduto = produto.getIdProduto();
		this.nome = produto.getNome();
		this.preco = produto.getPreco();
		this.idUsuario = produto.getUsuario().getIdUsuario();
	}
	
	public Long getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(Long idProduto) {
		this.idProduto = idProduto;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	
}
