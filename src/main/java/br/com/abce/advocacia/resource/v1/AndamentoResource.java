package br.com.abce.advocacia.resource.v1;

import br.com.abce.advocacia.bean.NotaAndamento;
import br.com.abce.advocacia.exceptions.InfraestruturaException;
import br.com.abce.advocacia.exceptions.ValidacaoException;
import br.com.abce.advocacia.resource.StandardRestDefinition;
import br.com.abce.advocacia.security.ApiSecurity;
import br.com.abce.advocacia.service.impl.NotaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.inject.Inject;
import javax.ws.rs.BeanParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/v1/andamento")
@Api(value = "Operações de andamento de processo")
@ApiSecurity
public class AndamentoResource extends AbstractResource implements StandardRestDefinition {

    @Inject
    private NotaService notaService;


    @POST
    @ApiOperation("Salvar novo andamento de um processo")
    public void salvarAndamento(@BeanParam final NotaAndamento notaAndamento) throws InfraestruturaException, ValidacaoException {
        notaService.salvarAndamento(notaAndamento);
    }

}
