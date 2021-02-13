package com.github.erickluz.service;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.github.erickluz.domain.Usuario;
import com.github.erickluz.dto.UsuarioDTO;
import com.github.erickluz.exception.AuthorizationException;
import com.github.erickluz.exception.DataIntegrityException;
import com.github.erickluz.exception.ObjectNotFoundException;
import com.github.erickluz.repository.UsuarioRepository;

@Produces(MediaType.APPLICATION_JSON)
@Transactional
@RequestScoped
public class UsuarioService {

	@Inject
	UsuarioRepository dao;

	public List<Usuario> listar() {
		return dao.findAll().list();
	}

	public Usuario buscar(Long id) throws ObjectNotFoundException, AuthorizationException {
		Usuario usuario = dao.findById(id);
		if (usuario == null) {
			throw new ObjectNotFoundException("Usuario de ID " + id + " não encontrado");
		}
		return usuario;
	}

	public Usuario salvar(Usuario usuario) {
		if (usuario.getSenha() != null) {
			usuario.setSenha(usuario.getSenha());	
		}
//		usuario.addPerfil(Perfil.ADMIN);
		dao.persist(usuario);
		usuario.setId((Long) dao.getEntityManager().getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(usuario));
		return usuario;
	}
	
	public Usuario editar(Usuario usuario) throws DataIntegrityException {
		if (usuario != null) {
			Usuario usuarioEntity = dao.findById(usuario.getIdUsuario());
			usuarioEntity.setNome(usuario.getNome());
			usuarioEntity.setDataNascimento(usuario.getDataNascimento());
			usuarioEntity.setEmail(usuario.getEmail());
			usuarioEntity.setLogin(usuario.getLogin());
			usuarioEntity.setSenha(usuario.getSenha());
			usuarioEntity.setRg(usuario.getRg());
			usuarioEntity.setSobrenome(usuario.getSobrenome());
//			usuarioEntity.setTelefones(usuario.getTelefones());
			usuarioEntity.setUrlFotoPerfil(usuario.getUrlFotoPerfil());

			return usuarioEntity;
		} else {
			throw new DataIntegrityException("Objeto inválido");
		}
	}

	public void excluir(Long id) {
		dao.deleteById(id);
	}

	public Usuario buscarUsuarioPorEmail(String email) throws ObjectNotFoundException {
		Usuario usuario = dao.findByEmail(email);
		if (usuario == null) {
			throw new ObjectNotFoundException("Usuario não encontrado");
		}
		return usuario;
	}

	public Usuario fromDTO(UsuarioDTO usuarioDTO) {
		Usuario usuario = new Usuario();
		usuario.setId(usuarioDTO.getId());
		usuario.setNome(usuarioDTO.getNome());
		usuario.setEmail(usuarioDTO.getEmail());
		return usuario;
	}
}