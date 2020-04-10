package br.com.abce.advocacia.resource.v1;

import br.com.abce.advocacia.resource.StandardRestDefinition;
import io.swagger.annotations.Api;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/v1/nota")
@Api(value = "Registro de nota em processo(s).")
public class NotaResource implements StandardRestDefinition {

    @POST
    public void cadastrar(){

    }
}
