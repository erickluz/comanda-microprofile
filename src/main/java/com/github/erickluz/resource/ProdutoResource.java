package com.github.erickluz.resource;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import com.github.erickluz.commons.Util;
import com.github.erickluz.domain.Produto;
import com.github.erickluz.dto.ProdutoDTO;
import com.github.erickluz.exception.AuthorizationException;
import com.github.erickluz.exception.DataIntegrityException;
import com.github.erickluz.exception.ObjectNotFoundException;
import com.github.erickluz.service.ProdutoService;

import io.netty.handler.codec.http.HttpResponseStatus;

@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/produtos")
public class ProdutoResource {
 
    @Inject
    ProdutoService service;
    @Inject
	Util util;

    @GET
    @Path(value="/usuarios/{idUsuario}")
    public Response listarProdutosPorUsuario(@PathParam("idUsuario") Long idUsuario){
    	List<Produto> produtos = service.listarProdutosPorUsuario(idUsuario);
    	List<ProdutoDTO> produtosDTO = produtos.stream().map(p -> new ProdutoDTO(p)).collect(Collectors.toList()); 
        return (produtosDTO.isEmpty()) ? Response.noContent().build() : Response.ok(produtosDTO).build();
    }

    @GET
    @Path(value="/{id}")
    public Response buscar(@PathParam("id") Long id) throws ObjectNotFoundException {
        ProdutoDTO produto =  new ProdutoDTO(service.buscar(id));
        return (produto != null) ? Response.ok(produto).build() : Response.status(HttpResponseStatus.NOT_FOUND.code()).build();
    }

    @POST
    public Response criarProduto(ProdutoDTO obj) throws ObjectNotFoundException, AuthorizationException, DataIntegrityException {
    	Long idProduto = service.salvar(service.fromDTO(obj)).getIdProduto();
        URI uri = UriBuilder.fromPath(util.getUrlServidor() + idProduto).build();
        return Response.created(uri).build();
    }

    @PUT
    public Response editarProduto(ProdutoDTO obj) throws ObjectNotFoundException, AuthorizationException {
        service.editar(service.fromDTO(obj));
        return Response.noContent().build();
    }   

    @DELETE
    @Path(value="/{id}")
    public Response excluirProduto(@PathParam("id") Long id) throws DataIntegrityException {
        service.excluir(id);
        return Response.noContent().build();
    }
}