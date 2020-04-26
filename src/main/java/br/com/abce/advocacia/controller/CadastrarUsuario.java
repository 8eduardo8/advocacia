package br.com.abce.advocacia.controller;

import br.com.abce.advocacia.Perfil;
import br.com.abce.advocacia.bean.UsuarioBean;
import br.com.abce.advocacia.exceptions.AdvocaciaException;
import br.com.abce.advocacia.exceptions.ValidacaoException;
import br.com.abce.advocacia.service.impl.UsuarioService;
import br.com.abce.advocacia.util.Consts;
import br.com.abce.advocacia.util.LoggerUtil;
import br.com.abce.advocacia.util.Mensagem;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@RequestScoped
public class CadastrarUsuario implements Serializable {

	private UsuarioBean usuarioBean;

	private String senhaAtual;
	private String novaSenha;
	private String confirmaSenha;

	private final Perfil[] listaPerfil = Perfil.values();

	@Inject
	private UsuarioService usuarioService;

	@PostConstruct
	public void init() {
		novo();
	}

	public String editar() {
		senhaAtual = novaSenha = confirmaSenha = "";
		return "cadastrarUsuario";
	}

	public String novo() {
		usuarioBean = new UsuarioBean();
		senhaAtual = novaSenha = confirmaSenha = "";
		return "cadastrarUsuario";
	}

	public String salvar() {

		try {

			if (usuarioBean.getId() == null)

				usuarioBean.setSenha("abc123");

			usuarioService.salvar(usuarioBean);

			Mensagem.info("USUÁRIO SALVO!");

		} catch (AdvocaciaException ex) {
			Mensagem.info(ex.getMessage());
		} catch (Exception e) {
			Mensagem.erro(e.getMessage());
			LoggerUtil.error(e);
			return "";
		}

		return novo();
	}

	public String alterarSenha() {

		try {

			usuarioService.alterarSenha(usuarioBean, novaSenha, senhaAtual, confirmaSenha);

			Mensagem.info(Consts.OPERACO_REALIZADA_SUCESSO);

			return "dashboard";

		} catch (ValidacaoException ex){
			Mensagem.info(ex.getMessage());
		} catch (Exception e) {
			Mensagem.erro(e.getMessage());
			LoggerUtil.error(e);
		}
		return "";
	}

	public UsuarioBean getUsuarioBean() {
		return usuarioBean;
	}

	public void setUsuarioBean(UsuarioBean usuarioBean) {
		this.usuarioBean = usuarioBean;
	}

	public String getSenhaAtual() {
		return senhaAtual;
	}

	public void setSenhaAtual(String senhaAtual) {
		this.senhaAtual = senhaAtual;
	}

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	public String getConfirmaSenha() {
		return confirmaSenha;
	}

	public void setConfirmaSenha(String confirmaSenha) {
		this.confirmaSenha = confirmaSenha;
	}

	public Perfil[] getListaPerfil() {
		return listaPerfil;
	}
}
