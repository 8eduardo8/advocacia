package br.com.abce.advocacia.resource.v1;

import br.com.abce.advocacia.bean.UsuarioBean;
import br.com.abce.advocacia.exceptions.InfraestruturaException;
import br.com.abce.advocacia.exceptions.RecursoNaoEncontradoException;
import br.com.abce.advocacia.exceptions.ValidacaoException;
import br.com.abce.advocacia.resource.StandardRestDefinition;
import br.com.abce.advocacia.security.ApiSecurity;
import br.com.abce.advocacia.service.impl.AutenticacaoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Path("/v1/autenticacao")
@Api(value = "Operações de autenticação")
@ApiSecurity
public class AutenticacaoResource extends AbstractResource implements StandardRestDefinition {

    @Inject
    private AutenticacaoService autenticacaoService;


    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation("Login do usuário")
    public UsuarioBean login(@FormParam("login") final String login,
                             @FormParam("senha") final String senha) throws ValidacaoException, RecursoNaoEncontradoException, InfraestruturaException {

        return autenticacaoService.login(login, senha);

    }

//    @POST
//    @Path("/login")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @ApiOperation("Login do usuário")
//    public void login(@BeanParam final LoginDto loginDto) throws ValidacaoException, RecursoNaoEncontradoException, InfraestruturaException {
//
//        autenticacaoService.login(loginDto.getUsuario(), loginDto.getSenha());
//
//    }

    @POST
    @Path("/logout")
    @ApiOperation("Logout do usuário")
    public void logout(@BeanParam final String idSessao) {

        autenticacaoService.logout(idSessao);

    }

    @POST
    @Path("/esqueci-senha")
    @ApiOperation("Solicitar senha provisório para o usuário")
    public void logout(@FormParam("login") final String login,
                       @FormParam("email") final String email) throws ValidacaoException, InfraestruturaException {

        autenticacaoService.senhaProvisoria(login, email);

    }


}
