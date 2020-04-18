package br.com.abce.advocacia.exceptions;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class AcessoNegadoException extends AdvocaciaException implements ExceptionMapper<AcessoNegadoException> {

    public AcessoNegadoException() {
        super();
    }

    public AcessoNegadoException(String message) {
        super(message);
    }

    public AcessoNegadoException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public Response toResponse(AcessoNegadoException exception) {
        return Response.status(Response.Status.UNAUTHORIZED).entity(exception.getMessage())
                .type(MediaType.APPLICATION_JSON).build();
    }
}
