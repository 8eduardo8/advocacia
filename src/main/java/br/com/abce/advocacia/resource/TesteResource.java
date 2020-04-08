package br.com.abce.advocacia.resource;

import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/v1/teste")
@Api(value = "Doc teste api.")
public class TesteResource {

    @GET
    @ApiOperation(value = "Teste api.")
    public String get(){
        return new Gson().toJson("OK");
    }

}
