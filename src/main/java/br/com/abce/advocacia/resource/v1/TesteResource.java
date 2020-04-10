package br.com.abce.advocacia.resource.v1;

import br.com.abce.advocacia.resource.StandardRestDefinition;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/v1/teste")
@Api(value = "Doc teste api.")
public class TesteResource implements StandardRestDefinition {

    @GET
    @ApiOperation(value = "Teste api.")
    public String get(){
        return new Gson().toJson("OK");
    }

}
