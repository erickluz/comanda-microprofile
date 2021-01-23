package com.github.erickluz.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.github.erickluz.domain.Endereco;
import com.github.erickluz.exception.ObjectNotFoundException;
import com.github.erickluz.repository.EnderecoRepository;

@ApplicationScoped
public class EnderecoService {
	@Inject
	private EnderecoRepository dao;
	
	public Endereco buscar(Long id) throws ObjectNotFoundException {
		return dao.findById(id);
	}
	
	public List<Endereco> listar() {
		return dao.findAll().list();
	}
	
	public Endereco salvar(Endereco endereco) {
		dao.persist(endereco);
		return endereco;
	}
	
	public List<Endereco> salvarLista(List<Endereco> endereco) {
		dao.persist(endereco);
		return endereco;
	}
	
	public void excluir(Long id) {
		dao.deleteById(id);
	}
}
