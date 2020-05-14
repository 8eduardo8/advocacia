package br.com.abce.advocacia.service.impl;

import br.com.abce.advocacia.bean.UsuarioBean;
import br.com.abce.advocacia.exceptions.InfraestruturaException;
import br.com.abce.advocacia.exceptions.RecursoNaoEncontradoException;
import br.com.abce.advocacia.exceptions.ValidacaoException;
import br.com.abce.advocacia.util.Consts;
import br.com.abce.advocacia.util.Util;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.Serializable;

public class AutenticacaoService implements Serializable {

    @Inject
    private UsuarioService usuarioService;

    @Inject
    private MailService mailService;

    @Inject
    private Util util;

    public UsuarioBean login(final String usuario, final String senha) throws ValidacaoException, RecursoNaoEncontradoException, InfraestruturaException {

        if (StringUtils.isBlank(usuario))
            throw new ValidacaoException("Favor informar o usuário.");

        if (StringUtils.isBlank(senha))
            throw new ValidacaoException("Favor informar a senha.");

        final String senhaCriptada = util.gerarHashMD5(senha);

        final UsuarioBean usuarioBean = usuarioService.buscar(usuario);

        if (usuarioBean == null)
            throw new RecursoNaoEncontradoException(Consts.USUARIO_NAO_ENCONTRADO);

        if (!usuarioBean.getSenha().equals(senhaCriptada)) {

            if (usuarioBean.isRecuperarSenha()) {

                if (!usuarioBean.getSenhaTemporaria().equals(senhaCriptada))

                    throw new ValidacaoException("Senha incorreta.");

            } else {

                throw new ValidacaoException("Senha incorreta.");
            }
        }

        return usuarioBean;
    }


    public void logout(String idSessao) {

    }

    @Transactional
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

        String novaSenha = util.gerarNovaSenha();

        usuarioBean.setSenhaTemporaria(util.gerarHashMD5(novaSenha));
        usuarioBean.setRecuperarSenha(true);

        usuarioService.salvar(usuarioBean);

        final String corpo = String.format(Consts.CORPO_EMAIL_SENHA_PROVISORIA,
                usuarioBean.getNome(), novaSenha, Consts.URL_SISTEMA);

        mailService.enviarEmail(usuarioBean.getEmail(), Consts.ASSUNTO_SENHA_PROVISORIA, corpo);
    }
}
