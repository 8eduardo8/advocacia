package br.com.abce.advocacia.controller;

import br.com.abce.advocacia.Perfil;
import br.com.abce.advocacia.bean.UsuarioBean;
import br.com.abce.advocacia.exceptions.AdvocaciaException;
import br.com.abce.advocacia.service.impl.UsuarioService;
import br.com.abce.advocacia.util.LoggerUtil;
import br.com.abce.advocacia.util.Mensagem;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
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

			Mensagem.info("USU�RIO SALVO!");

		} catch (AdvocaciaException ex) {
			Mensagem.info(ex.getMessage());
		} catch (Exception e) {
			Mensagem.erro("ERRO AO SALVAR", e.getMessage());
			LoggerUtil.error(e);
			return "";
		}

		return novo();
	}

	public String alterarSenha() {

		if (!senhaAtual.equals(usuarioBean.getSenha())) {
			Mensagem.erro("A SENHA DIGITADA N�O CONFERE COM A SUA SENHA!", "");
			return "";
		} else if (!novaSenha.equals(confirmaSenha)) {
			Mensagem.erro("CONFIRMA��O DA SNEHA N�O CONFERE COM A NOVA SENHA!", "");
			return "";
		}

		try {
			usuarioBean = usuarioService.buscar((long) usuarioBean.getId());
			if (usuarioBean != null) {
				usuarioBean.setSenha(novaSenha);
				usuarioService.salvar(usuarioBean);
				Mensagem.info("SENHA ALTERADA!");
			}
		} catch (Exception e) {
			Mensagem.erro("ERRO AO SALVAR!", e.getMessage());
			LoggerUtil.error(e);
			return "";
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
