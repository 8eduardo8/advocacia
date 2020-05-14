package br.com.abce.advocacia.controller;

import br.com.abce.advocacia.bean.UsuarioBean;
import br.com.abce.advocacia.exceptions.InfraestruturaException;
import br.com.abce.advocacia.exceptions.RecursoNaoEncontradoException;
import br.com.abce.advocacia.exceptions.ValidacaoException;
import br.com.abce.advocacia.service.impl.AutenticacaoService;
import br.com.abce.advocacia.service.impl.UsuarioService;
import br.com.abce.advocacia.util.LoggerUtil;
import br.com.abce.advocacia.util.Mensagem;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.ByteArrayInputStream;
import java.io.Serializable;

@Named
@SessionScoped
public class Login implements Serializable {

	private String userLogin;
	private String senha;
	private UsuarioBean usuarioBean;
	private boolean logado;

	private StreamedContent usuarioImagem;

	@Inject
	private AutenticacaoService autenticacaoService;

	@Inject
	private UsuarioService usuarioService;;

	@PostConstruct
	public void init() {
	}

	public String esqueciSenha() {

		return "esqueciSenha";
	}

	public String entrar() {

		usuarioBean = null;
		String retorno = null;

		try {

			usuarioBean = autenticacaoService.login(userLogin, senha);

			if (usuarioBean.isRecuperarSenha()) {

				retorno = "alterarSenha";

				logado = false;

			} else {

				retorno = "dashboard";

				logado = true;
			}

		} catch (ValidacaoException | RecursoNaoEncontradoException e) {
			Mensagem.info(e.getMessage());
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
		return "login";
	}

	private void carregaImageUsuario() {

		try {

			byte[] fotoUsuarioDeserializada = usuarioService.buscarFotoUsuarioDeserializada(getUsuarioBean().getId());

			usuarioImagem = new DefaultStreamedContent(new ByteArrayInputStream(fotoUsuarioDeserializada));

		} catch (RecursoNaoEncontradoException e) {
			Mensagem.info(e.getMessage());
		} catch (InfraestruturaException e) {
			Mensagem.erro(e.getMessage());
		}
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


	public StreamedContent getUsuarioImagem() {

		if (usuarioImagem == null) 	carregaImageUsuario();

		return usuarioImagem;
	}

	public void setUsuarioImagem(StreamedContent usuarioImagem) {
		this.usuarioImagem = usuarioImagem;
	}
}
