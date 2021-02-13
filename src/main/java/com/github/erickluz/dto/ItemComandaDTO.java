package com.github.erickluz.dto;

import java.io.Serializable;

public class ItemComandaDTO implements Serializable {
    private static final long serialVersionUID = 6813623332794132459L;
    private Integer id;
    private Integer quantidade;
    private Integer idProduto;

    public ItemComandaDTO(Integer id, Integer quantidade, Integer idProduto) {
        this.id = id;
        this.quantidade = quantidade;
        this.idProduto = idProduto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQtd(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Integer getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Integer idProduto) {
        this.idProduto = idProduto;
    }
}