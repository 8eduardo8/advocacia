package br.com.abce.advocacia.resource.v1;

import br.com.abce.advocacia.bean.NotaBean;
import br.com.abce.advocacia.exceptions.InfraestruturaException;
import br.com.abce.advocacia.exceptions.RecursoNaoEncontradoException;
import br.com.abce.advocacia.exceptions.ValidacaoException;
import br.com.abce.advocacia.resource.StandardRestDefinition;
import br.com.abce.advocacia.security.ApiSecurity;
import br.com.abce.advocacia.service.impl.NotaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.util.List;


@Path("/v1/notificacao")
@Api(value = "Notificação de usuários.")
@ApiSecurity
public class NotificacaoResource extends AbstractResource implements StandardRestDefinition {

    @Inject
    private NotaService notaService;

    @GET
    @ApiOperation(value = "Lista notificações dos processos exceto do id do usuário.")
    public List<NotaBean> listaNotasExcetoUsuario(@QueryParam("id-usuario") final Long idUsuario,  @QueryParam("page-number") int pageNumber, @QueryParam("page-size") int pageSize) throws InfraestruturaException, ValidacaoException, RecursoNaoEncontradoException {
        return notaService.listarNotasProcessosDoUsuario(idUsuario, pageNumber, pageSize);
    }
}
