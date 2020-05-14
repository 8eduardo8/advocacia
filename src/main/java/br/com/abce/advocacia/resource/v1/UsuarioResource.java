package br.com.abce.advocacia.resource.v1;

import br.com.abce.advocacia.bean.UsuarioBean;
import br.com.abce.advocacia.exceptions.InfraestruturaException;
import br.com.abce.advocacia.exceptions.RecursoNaoEncontradoException;
import br.com.abce.advocacia.exceptions.ValidacaoException;
import br.com.abce.advocacia.resource.StandardRestDefinition;
import br.com.abce.advocacia.security.ApiSecurity;
import br.com.abce.advocacia.service.impl.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/v1/usuario")
@Api(value = "Operações e consulta no recurso Usuário.")
@ApiSecurity
public class UsuarioResource extends AbstractResource implements StandardRestDefinition {

    @Inject
    private UsuarioService usuarioService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation("Cadastro de usuário")
    public void cadastrar(@BeanParam final UsuarioBean usuarioBean) throws ValidacaoException, InfraestruturaException {

        usuarioService.salvar(usuarioBean);

    }

    @GET
    @ApiOperation("Lista de usuário(s)")
    public List<UsuarioBean> listar(@QueryParam("nome") final String filtro, @QueryParam("perfil") final int perfil, @QueryParam("ativo") final boolean ativo) throws RecursoNaoEncontradoException, InfraestruturaException {

        return usuarioService.listar(filtro, perfil, ativo);

    }

    @GET
    @Path("/{id}")
    @ApiOperation("Consulta usuário")
    public UsuarioBean consultar(@PathParam("id") final Long idUsuario) throws RecursoNaoEncontradoException {

        return usuarioService.buscar(idUsuario);

    }

    @PUT
    @Path("{id}/foto")
    @ApiOperation("Altera foto do usuário")
    public void alterarFotoUsuario(@PathParam("id") final Long idUsuario, @FormParam("imagemBase64") final String imagemBase64) throws ValidacaoException, InfraestruturaException {

        usuarioService.alterarFoto(imagemBase64, null, null, idUsuario);
    }

    @GET
    @Path("/{id}/foto")
    @ApiOperation("Busca foto do usuário")
    public String getFotoUsuario(@PathParam("id") final Long idUsuario) throws RecursoNaoEncontradoException, InfraestruturaException {

        return usuarioService.buscarFotoUsuario(idUsuario);

    }

}
