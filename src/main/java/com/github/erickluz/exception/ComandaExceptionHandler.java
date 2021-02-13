package com.github.erickluz.exception;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import io.netty.handler.codec.http.HttpResponseStatus;

@Produces(MediaType.APPLICATION_JSON)
@Provider
public class ComandaExceptionHandler implements ExceptionMapper<Exception> {
	@Override
	public Response toResponse(Exception exception) {
		exception.printStackTrace();
		ResponseException resposta = new ResponseException(exception);
		Response response = Response.status(HttpResponseStatus.INTERNAL_SERVER_ERROR.code()).type(MediaType.APPLICATION_JSON).entity(resposta).build();
		if (exception instanceof ObjectNotFoundException) {
			response = Response.status(HttpResponseStatus.NOT_FOUND.code()).type(MediaType.APPLICATION_JSON).entity(resposta).build();
		} else if (exception instanceof AuthorizationException) {
			response = Response.status(HttpResponseStatus.FORBIDDEN.code()).type(MediaType.APPLICATION_JSON).entity(resposta).build();
		} else if (exception instanceof DataIntegrityException) {
			response = Response.status(HttpResponseStatus.BAD_REQUEST.code()).type(MediaType.APPLICATION_JSON).entity(resposta).build();
		}
		return response;
	}
}
