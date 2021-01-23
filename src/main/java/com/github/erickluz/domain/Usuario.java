package com.github.erickluz.domain;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
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
	@JsonIgnore
	@ElementCollection
	@CollectionTable(name="TELEFONE")
	private List<String> telefones = new ArrayList<>();
	@Column(unique = true)
	private String email;
	private String login;
	private String senha;
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name="PERFIS")
	private Set<Integer> perfis = new HashSet<>();
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "USUARIO_ENDERECO",
               joinColumns = @JoinColumn(name = "idUsuario", referencedColumnName = "idUsuario"),
               inverseJoinColumns = @JoinColumn(name = "idEndereco", referencedColumnName = "idEndereco"))
	public List<Endereco> enderecos = new ArrayList<>();
	public TipoAutenticacao tipoAutenticacao;
	public String urlFotoPerfil;
	
	public Usuario() {
		addPerfil(Perfil.ADMIN);
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

	public List<String> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<String> telefones) {
		this.telefones = telefones;
	}
	
	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
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

	public Set<Perfil> getPerfils() {
		return perfis.stream().map(p -> Perfil.fromCodigo(p)).collect(Collectors.toSet());
	}
	
	public void addPerfil(Perfil perfil) {
		perfis.add(perfil.getCodigo());
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
				+ ", rg=" + rg + ", dataNascimento=" + dataNascimento + ", telefones=" + telefones + ", email=" + email
				+ ", login=" + login + ", senha=" + senha + ", perfis=" + perfis + ", enderecos=" + enderecos
				+ ", tipoAutenticacao=" + tipoAutenticacao + ", urlFotoPerfil=" + urlFotoPerfil + "]";
	}
	
}