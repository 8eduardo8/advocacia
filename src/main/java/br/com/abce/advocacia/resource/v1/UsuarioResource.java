package br.com.abce.advocacia.resource.v1;

import br.com.abce.advocacia.bean.UsuarioBean;
import br.com.abce.advocacia.exceptions.RecursoNaoEncontradoException;
import br.com.abce.advocacia.exceptions.ValidacaoException;
import br.com.abce.advocacia.resource.StandardRestDefinition;
import br.com.abce.advocacia.service.impl.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/v1/usuario")
@Api(value = "Operações e consulta no recurso Usuário.")
public class UsuarioResource implements StandardRestDefinition {

    @Inject
    private UsuarioService usuarioService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation("Cadastro de usuário")
    public void cadastrar(@BeanParam final UsuarioBean usuarioBean) throws ValidacaoException {

        usuarioService.salvar(usuarioBean);

    }

    @GET
    @ApiOperation("Lista de usuário(s)")
    public List<UsuarioBean> listar() throws RecursoNaoEncontradoException {

        return usuarioService.listar();

    }

}
