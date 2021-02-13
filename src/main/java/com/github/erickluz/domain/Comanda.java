package com.github.erickluz.domain;


import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Comanda")
public class Comanda implements Serializable {
    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long idComanda;
    public Integer numero;
    public String nomeCliente;
    public LocalDateTime horaAbertura;
    public LocalDateTime horaFechamento;
    public BigDecimal valorTotal;
    @Enumerated(EnumType.ORDINAL)
    public StatusComanda status;
    @ManyToOne
    @JoinColumn(name="id_usuario", nullable = false)
    public Usuario usuario;

    public Comanda() {
        this.horaAbertura = LocalDateTime.now();
    }

    public Comanda(Integer numero, String nomeCliente, BigDecimal valorTotal){
    	this.numero = numero;
    	this.nomeCliente = nomeCliente;
    	this.horaAbertura = LocalDateTime.now();
    	this.valorTotal = valorTotal;
    }

    public Long getIdComanda() {
        return idComanda;
    }

    public void setIdComanda(Long idComanda) {
        this.idComanda = idComanda;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public LocalDateTime getHoraAbertura() {
        return horaAbertura;
    }

    public void setHoraAbertura(LocalDateTime horaAbertura) {
        this.horaAbertura = horaAbertura;
    }

    public LocalDateTime getHoraFechamento() {
        return horaFechamento;
    }

    public void setHoraFechamento(LocalDateTime horaFechamento) {
        this.horaFechamento = horaFechamento;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

	public StatusComanda getStatus() {
		return status;
	}

	public void setStatus(StatusComanda status) {
		this.status = status;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}