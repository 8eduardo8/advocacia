package br.com.abce.advocacia.controller;

import br.com.abce.advocacia.exceptions.InfraestruturaException;
import br.com.abce.advocacia.exceptions.ValidacaoException;
import br.com.abce.advocacia.service.impl.AutenticacaoService;
import br.com.abce.advocacia.util.Mensagem;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@RequestScoped
public class EsqueciSenha implements Serializable {


    private String userLogin;

    private String email;

    @Inject
    private AutenticacaoService autenticacaoService;

    @PostConstruct
    public void init() {
    }

    public String gerarSenhaProvisoria() {

        try {

            autenticacaoService.senhaProvisoria(userLogin, email);

			return "login";

        } catch (InfraestruturaException e) {
            Mensagem.erro(e.getMessage());
        } catch (ValidacaoException e) {
            Mensagem.info(e.getMessage());
        }

        return "";
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
