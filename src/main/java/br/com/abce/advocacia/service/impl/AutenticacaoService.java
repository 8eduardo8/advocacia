package br.com.abce.advocacia.service.impl;

import br.com.abce.advocacia.bean.UsuarioBean;
import br.com.abce.advocacia.exceptions.InfraestruturaException;
import br.com.abce.advocacia.exceptions.RecursoNaoEncontradoException;
import br.com.abce.advocacia.exceptions.ValidacaoException;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import java.io.Serializable;

public class AutenticacaoService implements Serializable {

    @Inject
    private UsuarioService usuarioService;

    public UsuarioBean login(final String usuario, final String senha) throws ValidacaoException, RecursoNaoEncontradoException, InfraestruturaException {

        if (StringUtils.isBlank(usuario))
            throw new ValidacaoException("Favor informar o usuário.");

        if (StringUtils.isBlank(senha))
            throw new ValidacaoException("Favor informar a senha.");

        final UsuarioBean usuarioBean = usuarioService.buscar(usuario);

        if (usuarioBean == null)
            throw new RecursoNaoEncontradoException("Usuário não encontrado.");

        if (!usuarioBean.getSenha().equals(senha))
            throw new ValidacaoException("Senha incorreta.");

        return usuarioBean;
    }


    public void logout(String idSessao) {

    }

    public void senhaProvisoria(String login, String email) {
    }
}
