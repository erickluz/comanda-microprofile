package com.github.erickluz.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="ItensComanda") 
public class ItensComanda implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@JsonIgnore
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long idItemComanda;
	@JsonProperty("quantidade")
    public Integer quantidade;

	@JsonIgnore
    @ManyToOne
    @JoinColumn(name = "idComanda")
    public Comanda comanda;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idProduto")
    @JsonProperty("produto")
    public Produto produto;

    public ItensComanda(){

    }

    public Long getIdItemComanda() {
        return idItemComanda;
    }

    public void setIdItemComanda(Long idItemComanda) {
        this.idItemComanda = idItemComanda;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Comanda getComanda() {
        return comanda;
    }

    public void setComanda(Comanda comanda) {
        this.comanda = comanda;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

	@Override
	public String toString() {
		return "ItensComanda [idItemComadna=" + idItemComanda + ", quantidade=" + quantidade + ", comanda=" + comanda + ", produto=" + produto
				+ "]";
	}   
    
}