package br.com.abce.advocacia.resource.v1;

import br.com.abce.advocacia.bean.EscritorioBean;
import br.com.abce.advocacia.exceptions.InfraestruturaException;
import br.com.abce.advocacia.exceptions.RecursoNaoEncontradoException;
import br.com.abce.advocacia.exceptions.ValidacaoException;
import br.com.abce.advocacia.resource.StandardRestDefinition;
import br.com.abce.advocacia.security.ApiSecurity;
import br.com.abce.advocacia.service.impl.EscritorioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import java.util.List;

@Path("/v1/escritorio")
@Api(value = "Operações e consulta no recurso Escritório.")
@ApiSecurity
public class EscritorioResource extends AbstractResource implements StandardRestDefinition {


    @Inject
    private EscritorioService escritorioService;

    @GET
    @ApiOperation(value = "Listar os escritórios por Id usuário.")
    public List<EscritorioBean> getByIdUsuario(@QueryParam("id-usuario") final Long idUsuario) throws ValidacaoException, RecursoNaoEncontradoException, InfraestruturaException {
        return escritorioService.listar(idUsuario);
    }

    @GET
    @Path("/{id}")
    @ApiOperation(value = "Busca o escritório pelo id.")
    public EscritorioBean get(@PathParam("id") final Long idEscritorio) throws ValidacaoException, RecursoNaoEncontradoException {
        return escritorioService.buscar(idEscritorio);
    }

}
