package com.github.erickluz.repository;


import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

import com.github.erickluz.domain.Usuario;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

@Transactional
@ApplicationScoped
public class UsuarioRepository implements PanacheRepository<Usuario>{
	
	public Usuario findByEmail(String email) {
		return find("email", email).firstResult();
	}
}