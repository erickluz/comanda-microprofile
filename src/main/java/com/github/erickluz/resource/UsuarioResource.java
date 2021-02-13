package com.github.erickluz.resource;

import java.net.URI;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

import com.github.erickluz.commons.Util;
import com.github.erickluz.domain.Usuario;
import com.github.erickluz.dto.UsuarioDTO;
import com.github.erickluz.exception.AuthorizationException;
import com.github.erickluz.exception.DataIntegrityException;
import com.github.erickluz.exception.ObjectNotFoundException;
import com.github.erickluz.service.UsuarioService;

import io.netty.handler.codec.http.HttpResponseStatus;

@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Path("/usuarios")
public class UsuarioResource {

	@Inject
	UsuarioService service;
	@Inject
	Util util;
	
	@GET
	@Path(value = "/{id}")
	public Response buscar(@PathParam("id") Long id) throws ObjectNotFoundException, AuthorizationException {
		Usuario usuario = service.buscar(id);
		return (usuario == null) ? Response.status(HttpResponseStatus.NOT_FOUND.code()).build() : Response.ok(new UsuarioDTO(usuario)).build();
	}

	@GET
	public Response buscarUsuarioPorEmail(@QueryParam("email") String email) throws ObjectNotFoundException, AuthorizationException {
		UsuarioDTO usuarioDTO = null;
		if (email != null) {
			Usuario usuario = service.buscarUsuarioPorEmail(email);
			usuarioDTO = new UsuarioDTO(usuario);
		}
		return (usuarioDTO != null) ? Response.ok(usuarioDTO).build() : Response.status(HttpResponseStatus.NOT_FOUND.code()).build();
	}
	
	@POST
	public Response salvarUsuario(Usuario usuario) {
		Usuario objUsuario = service.salvar(usuario);
		URI uri = UriBuilder.fromPath(util.getUrlServidor() + objUsuario.getIdUsuario()).build();
		return Response.created(uri).build();
	}

	@PUT
	public Response editarUsuario(Usuario usuario) throws DataIntegrityException {
		service.editar(usuario);
		return Response.noContent().build();
	}

	@DELETE
	@Path(value = "/{id}")
	public Response removerUsuario(@PathParam("id") Long id) {
		service.excluir(id);
		return Response.noContent().build();
	}
	
//	@POST
//	@Path(value="/oauth")
//	public Response autenticarUsuarioOAuth(@RequestBody CredenciaisDTO credenciais) throws AuthorizationException {
//		Usuario usuario = service.autencicacaoOAuth(credenciais);
//		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}").buildAndExpand(usuario.getIdUsuario()).toUri();
//		return ResponseEntity.created(uri).build();
//	}
}