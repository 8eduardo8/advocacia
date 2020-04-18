package br.com.abce.advocacia.resource.v1;

import br.com.abce.advocacia.bean.NotaDocumento;
import br.com.abce.advocacia.exceptions.InfraestruturaException;
import br.com.abce.advocacia.exceptions.RecursoNaoEncontradoException;
import br.com.abce.advocacia.exceptions.ValidacaoException;
import br.com.abce.advocacia.resource.StandardRestDefinition;
import br.com.abce.advocacia.security.ApiSecurity;
import br.com.abce.advocacia.service.impl.NotaDocumentoService;
import br.com.abce.advocacia.service.impl.NotaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.inject.Inject;
import javax.ws.rs.*;

@Path("/v1/documento/")
@Api(value = "Operações de upload de documento")
@ApiSecurity
public class DocumentoResource extends AbstractResource implements StandardRestDefinition {

    @Inject
    private NotaDocumentoService notaDocumentoService;

    @Inject
    private NotaService notaService;

    @GET
    @Path("/{id}")
    @ApiOperation("Recupera o documento do processo")
    public NotaDocumento getDocumento(@PathParam("id") final Long id) throws InfraestruturaException, RecursoNaoEncontradoException {
        return notaDocumentoService.buscar(id);
    }

    @POST
    @ApiOperation("Salvar novo documento de um processo")
    public void salvarDocumento(@BeanParam final NotaDocumento notaDocumento) throws InfraestruturaException, ValidacaoException {
        notaService.salvarDocumento(notaDocumento);
    }

}
