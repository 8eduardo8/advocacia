package br.com.abce.advocacia.controller;

import br.com.abce.advocacia.bean.UsuarioBean;
import br.com.abce.advocacia.exceptions.AdvocaciaException;
import br.com.abce.advocacia.service.impl.AutenticacaoService;
import br.com.abce.advocacia.util.LoggerUtil;
import br.com.abce.advocacia.util.Mensagem;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class Login implements Serializable {

	private String userLogin;
	private String senha;
	private UsuarioBean usuarioBean;
	private boolean logado;

	@Inject
	private AutenticacaoService autenticacaoService;

	@PostConstruct
	public void init() {
	}

	public String entrar() {

		usuarioBean = null;
		String retorno = null;

		try {

			usuarioBean = autenticacaoService.login(userLogin, senha);

			logado = true;
			retorno = "menu";

		} catch (AdvocaciaException e) {
			Mensagem.erro(e.getMessage());
			retorno = "";
		} catch (Exception e) {
			LoggerUtil.error(e);
			Mensagem.erro(e.getMessage());
			retorno = "";
		}

		return retorno;
	}

	public String sair() {
		logado = false;
		senha = "";
		usuarioBean = new UsuarioBean();
		return "userLogin";
	}

	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public UsuarioBean getUsuarioBean() {
		return usuarioBean;
	}

	public void setUsuarioBean(UsuarioBean usuarioBean) {
		this.usuarioBean = usuarioBean;
	}

	public boolean isLogado() {
		return logado;
	}

	public void setLogado(boolean logado) {
		this.logado = logado;
	}

}
