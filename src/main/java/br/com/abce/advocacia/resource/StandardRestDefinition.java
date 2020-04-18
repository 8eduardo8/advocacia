package br.com.abce.advocacia.resource;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@ApiResponses(value = {
        @ApiResponse(code = 200, message = Messages.EXECUTADO_COM_SUCESSO),
        @ApiResponse(code = 400, message = Messages.VALIDATION_ERROR),
        @ApiResponse(code = 401, message = Messages.UNAUTHORIZED),
        @ApiResponse(code = 403, message = Messages.TOKEN_NO_FOUND),
        @ApiResponse(code = 404, message = Messages.NO_FOUND_INFO),
        @ApiResponse(code = 500, message = Messages.INTERNAL_ERROR)})
@Produces(MediaType.APPLICATION_JSON)
public interface StandardRestDefinition {

}
