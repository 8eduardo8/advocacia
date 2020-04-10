package br.com.abce.advocacia.exceptions;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class InfraestruturaException extends AdvocaciaException implements ExceptionMapper<InfraestruturaException> {

    public InfraestruturaException() {
        super();
    }

    public InfraestruturaException(String message) {
        super(message);
    }

    public InfraestruturaException(String message, Throwable cause) {
        super(message, cause);
    }

    public Response toResponse(InfraestruturaException exception) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(exception.getMessage())
                .type(MediaType.APPLICATION_JSON).build();
    }

    public String getMessage() {
        return "Falha durante a operação, favor entrar em contato com os administradores do sistema!";
    }
}
