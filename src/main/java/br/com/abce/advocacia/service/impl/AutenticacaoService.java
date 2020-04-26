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

    @Inject
    private MailService mailService;

    public UsuarioBean login(final String usuario, final String senha) throws ValidacaoException, RecursoNaoEncontradoException, InfraestruturaException {

        if (StringUtils.isBlank(usuario))
            throw new ValidacaoException("Favor informar o usuário.");

        if (StringUtils.isBlank(senha))
            throw new ValidacaoException("Favor informar a senha.");

        final UsuarioBean usuarioBean = usuarioService.buscar(usuario);

        if (usuarioBean == null)
            throw new RecursoNaoEncontradoException("Usuário não encontrado.");

        if (!usuarioBean.getSenha().equals(senha)) {

            if (usuarioBean.isRecuperarSenha()
                    && !usuarioBean.getSenhaTemporaria().equals(senha))

                throw new ValidacaoException("Senha incorreta.");
        }

        return usuarioBean;
    }


    public void logout(String idSessao) {

    }

    public void senhaProvisoria(final String login, final String email) throws ValidacaoException, InfraestruturaException {

        if (StringUtils.isBlank(login))
            throw new ValidacaoException("Favor informar o login do usuário.");

        if (StringUtils.isBlank(email))
            throw new ValidacaoException("Favor informar o e-mail do usuário.");

        UsuarioBean usuarioBean = null;

        try {

            usuarioBean = usuarioService.buscar(login);

        } catch (RecursoNaoEncontradoException ex) {
            throw new ValidacaoException(ex.getMessage());
        }

        if (!usuarioBean.getEmail().equals(email))
            throw new ValidacaoException("E-mail informado está incorreto.");

        usuarioBean.setSenhaTemporaria("abc123");
        usuarioBean.setRecuperarSenha(true);

        usuarioService.salvar(usuarioBean);

        final String assunto = "Senha provisória";

        final String corpo = String.format("Olá %s,\n" +
                        "\n" +
                        "Uma senha provisória foi gerada para você acessar o sistema.\n" +
                        "\n" +
                        "\tsenha: %s\n" +
                        "\n" +
                        "Favor não responder e-mail automático.",
                usuarioBean.getNome(), usuarioBean.getSenhaTemporaria());

        mailService.enviarEmail(usuarioBean.getEmail(), assunto, corpo);
    }
}
