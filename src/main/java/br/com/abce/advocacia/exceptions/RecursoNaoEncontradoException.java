package br.com.abce.advocacia.exceptions;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class RecursoNaoEncontradoException extends AdvocaciaException implements ExceptionMapper<RecursoNaoEncontradoException> {

    public RecursoNaoEncontradoException(String message) {
        super(message);
    }

    public RecursoNaoEncontradoException() {
        super();
    }

    @Override
    public Response toResponse(RecursoNaoEncontradoException exception) {
        return Response.status(Response.Status.NOT_FOUND).entity(exception.getMessage())
                .type(MediaType.APPLICATION_JSON).build();
    }
}
