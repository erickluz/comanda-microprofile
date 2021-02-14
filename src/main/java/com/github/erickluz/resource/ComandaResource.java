package com.github.erickluz.resource;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import com.github.erickluz.commons.Util;
import com.github.erickluz.domain.Comanda;
import com.github.erickluz.dto.ComandaItensDTO;
import com.github.erickluz.exception.AuthorizationException;
import com.github.erickluz.exception.DataIntegrityException;
import com.github.erickluz.exception.ObjectNotFoundException;
import com.github.erickluz.service.ComandaService;

import io.netty.handler.codec.http.HttpResponseStatus;

@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/comandas")
public class ComandaResource {

	@Inject
	ComandaService service;
	@Inject
	Util util;

	@GET
	@Path(value = "/usuarios/{idUsuario}")
	public Response listarTodasComandasPorUsuario(@PathParam("idUsuario") final Long idUsuario) {
		List<Comanda> comandas = service.listarTodasComandasPorUsuario(idUsuario);
		List<ComandaItensDTO> comandasDTO = comandas.stream().map(c -> new ComandaItensDTO(c, null)).collect(Collectors.toList());
		return (!comandas.isEmpty()) ? Response.ok(comandasDTO).build() : Response.noContent().build();
	}

	@GET
	@Path(value = "/ativas/{idUsuario}")
	public Response listarTodasComandasAtivasPorUsuario(@PathParam("idUsuario") Long idUsuario) {
		List<Comanda> comandas = service.listarTodasComandasAtivasPorUsuario(idUsuario);
		List<ComandaItensDTO> comandasDTO = comandas.stream().map(c -> new ComandaItensDTO(c, null)).collect(Collectors.toList());
		return (!comandas.isEmpty()) ? Response.ok(comandasDTO).build() : Response.noContent().build();
	}

	@GET
	@Path(value = "/fechadas/{idUsuario}")
	public Response listarTodasComandasFechadasPorUsuario(@PathParam("idUsuario") Long idUsuario) {
		List<Comanda> comandas = service.listarTodasComandasFechadasPorUsuario(idUsuario);
		List<ComandaItensDTO> comandasDTO = comandas.stream().map(c -> new ComandaItensDTO(c, null)).collect(Collectors.toList());
		return (!comandas.isEmpty()) ? Response.ok(comandasDTO).build() : Response.noContent().build();
	}

	@GET
	@Path(value = "/{id}")
	public Response buscarComandaPorId(@PathParam("id") Long id) throws ObjectNotFoundException {
		ComandaItensDTO comanda = service.buscarComandaCompletaPorId(id);
		return (comanda != null) ? Response.ok(comanda).build() : Response.status(HttpResponseStatus.NOT_FOUND.code()).build();
	}

	@POST
	public Response salvarComanda(ComandaItensDTO comandaItens) throws DataIntegrityException, ObjectNotFoundException, AuthorizationException {
		Comanda comanda = service.salvarComanda(comandaItens);
		URI uri = UriBuilder.fromPath(util.getUrlServidor() + comanda.getIdComanda()).build();
		return Response.created(uri).build();
	}

	@PUT
	public Response editarComanda(ComandaItensDTO comanda) throws DataIntegrityException, ObjectNotFoundException {
		service.editarComanda(comanda);
		return Response.noContent().build();
	}

	@PATCH
	public Response acrescentarItemsEmUmaComanda(ComandaItensDTO comanda) throws DataIntegrityException, ObjectNotFoundException {
		service.acrescentarItensEmUmaComanda(comanda);
		return Response.noContent().build();
	}

	@DELETE
	@Path(value = "/{id}/status")
	public Response fecharComanda(@PathParam("{id}") Long id) throws DataIntegrityException, ObjectNotFoundException {
		service.fecharComanda(id);
		return Response.noContent().build();
	}

	@DELETE
	@Path(value = "/{id}")
	public Response removerComanda(@PathParam("{id}") Long id) {
		service.excluirComanda(id);
		return Response.noContent().build();
	}
}