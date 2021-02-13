package com.github.erickluz.domain;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Usuario") 
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idUsuario;
	private String nome;
	private String sobrenome;
	@Column(unique = true)
	private String cpf;
	private String rg;
	private Date dataNascimento;
	@Column(unique = true)
	private String email;
	private String login;
	private String senha;
	public TipoAutenticacao tipoAutenticacao;
	public String urlFotoPerfil;
	
	public Usuario() {

	}
	
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setId(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public TipoAutenticacao getTipoAutenticacao() {
		return tipoAutenticacao;
	}

	public void setTipoAutenticacao(TipoAutenticacao tipoAutenticacao) {
		this.tipoAutenticacao = tipoAutenticacao;
	}

	public String getUrlFotoPerfil() {
		return urlFotoPerfil;
	}

	public void setUrlFotoPerfil(String urlFotoPerfil) {
		this.urlFotoPerfil = urlFotoPerfil;
	}

	@Override
	public String toString() {
		return "Usuario [idUsuario=" + idUsuario + ", nome=" + nome + ", sobrenome=" + sobrenome + ", cpf=" + cpf
				+ ", rg=" + rg + ", dataNascimento=" + dataNascimento + ", email=" + email
				+ ", login=" + login + ", senha=" + senha 
				+ ", tipoAutenticacao=" + tipoAutenticacao + ", urlFotoPerfil=" + urlFotoPerfil + "]";
	}
	
}