package br.com.abce.advocacia.exceptions;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class ValidacaoException extends AdvocaciaException implements ExceptionMapper<ValidacaoException> {

    public ValidacaoException(String message) {
        super(message);
    }

    public ValidacaoException() {
        super();
    }

    @Override
    public Response toResponse(ValidacaoException exception) {
        return Response.status(Response.Status.BAD_REQUEST).entity(exception.getMessage())
                .type(MediaType.APPLICATION_JSON).build();
    }
}
