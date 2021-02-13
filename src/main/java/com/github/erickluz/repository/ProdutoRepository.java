package com.github.erickluz.repository;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import com.github.erickluz.domain.Produto;

import io.quarkus.hibernate.orm.panache.PanacheRepository;


@ApplicationScoped
public class ProdutoRepository implements PanacheRepository<Produto> {
	
    public List<Produto> listarTodosProdutosPorUsuario(Long idUsuario) {
    	return list("FROM Produto p WHERE p.usuario.idUsuario = ?1", idUsuario);
    }
}