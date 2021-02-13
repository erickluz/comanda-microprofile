package com.github.erickluz.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.github.erickluz.commons.Util;
import com.github.erickluz.domain.Comanda;
import com.github.erickluz.domain.ItensComanda;
import com.github.erickluz.domain.StatusComanda;

public class ComandaItensDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long idComanda;
	private Integer numero;
	private String nomeCliente;
	private String horaAbertura;
	private String horaFechamento;
	private BigDecimal valorTotal;
	private Long idUsuario;
	private StatusComanda status;
	private List<ItensComanda> itensComanda = new ArrayList<>();

	public ComandaItensDTO() {

	}

	public ComandaItensDTO(Comanda comanda, List<ItensComanda> itensComanda){
        this.idComanda = comanda.getIdComanda();
        this.numero = comanda.getNumero();
        this.nomeCliente = comanda.getNomeCliente();
        if (comanda.getHoraAbertura() != null) {
        	this.horaAbertura = comanda.getHoraAbertura().format(DateTimeFormatter.ofPattern(Util.DATE_FORMAT));	
        }
        if (comanda.getHoraFechamento() != null) {
        	this.horaFechamento = comanda.getHoraFechamento().format(DateTimeFormatter.ofPattern(Util.DATE_FORMAT));	
        }
        this.valorTotal = comanda.getValorTotal();
        this.itensComanda = itensComanda;
        if (comanda.getUsuario() != null) {
        	this.idUsuario = comanda.getUsuario().getIdUsuario();	
        }
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

	public String getHoraAbertura() {
		return horaAbertura;
	}

	public void setHoraAbertura(String horaAbertura) {
		this.horaAbertura = horaAbertura;
	}

	public String getHoraFechamento() {
		return horaFechamento;
	}

	public void setHoraFechamento(String horaFechamento) {
		this.horaFechamento = horaFechamento;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public List<ItensComanda> getItensComanda() {
		return itensComanda;
	}

	public void setItensComanda(List<ItensComanda> itensComanda) {
		this.itensComanda = itensComanda;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public StatusComanda getStatus() {
		return status;
	}

	public void setStatus(StatusComanda status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ComandaItensDTO [idComanda=" + idComanda + ", numero=" + numero + ", nomeCliente=" + nomeCliente + ", horaAbertura=" + horaAbertura + ", horaFechamento="
				+ horaFechamento + ", valorTotal=" + valorTotal + ", idUsuario=" + idUsuario + ", status=" + status + ", itensComanda=" + itensComanda + "]";
	}

}