package br.com.abce.advocacia.resource.v1;

import br.com.abce.advocacia.bean.NotaBean;
import br.com.abce.advocacia.bean.ProcessoBean;
import br.com.abce.advocacia.exceptions.InfraestruturaException;
import br.com.abce.advocacia.exceptions.RecursoNaoEncontradoException;
import br.com.abce.advocacia.exceptions.ValidacaoException;
import br.com.abce.advocacia.resource.StandardRestDefinition;
import br.com.abce.advocacia.service.impl.NotaService;
import br.com.abce.advocacia.service.impl.ProcessoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/v1/processo")
@Api(value = "Operações de consulta em processo(s).")
public class ProcessoResource implements StandardRestDefinition {

    @Inject
    private ProcessoService processoService;

    @Inject
    private NotaService notaService;

    @GET
    @ApiOperation(value = "Listar os processos por Id usuário.")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public List<ProcessoBean> getByIdUsuario(@QueryParam("id-usuario") final Long idUsuario) throws ValidacaoException, RecursoNaoEncontradoException, InfraestruturaException {
        return processoService.listar(idUsuario);
    }

    @GET
    @Path("/{id}")
    @ApiOperation(value = "Busca o processo pelo id.")
    public ProcessoBean get(@PathParam("id") final Long idProcesso) throws ValidacaoException, RecursoNaoEncontradoException {
        return processoService.buscar(idProcesso);
    }

    @GET
    @ApiOperation(value = "Busca o processo pelo numero do processo.")
    public ProcessoBean getByNumeroProcesso(@QueryParam("num-processo") final Long idProcesso) throws ValidacaoException, RecursoNaoEncontradoException, InfraestruturaException {
        return processoService.buscarPorNumero(idProcesso);
    }

    @GET
    @Path("/{id}/nota")
    @ApiOperation(value = "Lista notas no processo.")
    public List<NotaBean> registrarNota(@PathParam("id") final Long idProcesso) throws InfraestruturaException, ValidacaoException, RecursoNaoEncontradoException {
        return notaService.listar(idProcesso);
    }

    @POST
    @Path("/{id}/nota")
    @ApiOperation(value = "Cadastrar nota no processo.")
    public void registrarNota(@BeanParam final NotaBean notaBean) throws ValidacaoException {
        notaService.registrarNota(notaBean);
    }
}