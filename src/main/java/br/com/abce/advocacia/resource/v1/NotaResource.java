package br.com.abce.advocacia.resource.v1;

import br.com.abce.advocacia.bean.NotaAndamento;
import br.com.abce.advocacia.bean.NotaBean;
import br.com.abce.advocacia.bean.NotaDocumento;
import br.com.abce.advocacia.exceptions.InfraestruturaException;
import br.com.abce.advocacia.exceptions.RecursoNaoEncontradoException;
import br.com.abce.advocacia.exceptions.ValidacaoException;
import br.com.abce.advocacia.resource.StandardRestDefinition;
import br.com.abce.advocacia.security.ApiSecurity;
import br.com.abce.advocacia.service.impl.NotaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;

@Path("/v1/nota")
@Api(value = "Registro de nota em processo(s).")
@ApiSecurity
public class NotaResource extends AbstractResource implements StandardRestDefinition {

    @Inject
    private NotaService notaService;


    @GET
    @Path("/nota")
    @ApiOperation(value = "Lista notas no processo.")
    public List<NotaBean> registrarNota(@QueryParam("id") final Long idProcesso) throws InfraestruturaException, ValidacaoException, RecursoNaoEncontradoException {
        return notaService.listar(idProcesso);
    }

    @POST
    @Path("/nota")
    @ApiOperation(value = "Cadastrar nota no processo.")
    public void registrarNota(@BeanParam final NotaBean notaBean) throws ValidacaoException, InfraestruturaException {
        notaService.salvarNota(notaBean);
    }

    @POST
    @Path("/documento")
    @ApiOperation("Salvar novo documento de um processo")
    public void salvarDocumento(@BeanParam final NotaDocumento notaDocumento) throws InfraestruturaException, ValidacaoException {
        notaService.salvarDocumento(notaDocumento);
    }

    @POST
    @Path("/andamento")
    @ApiOperation("Salvar novo andamento de um processo")
    public void salvarAndamento(@BeanParam final NotaAndamento notaAndamento) throws InfraestruturaException, ValidacaoException {
        notaService.salvarAndamento(notaAndamento);
    }
}
