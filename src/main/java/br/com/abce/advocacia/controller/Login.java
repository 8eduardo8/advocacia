package br.com.abce.advocacia.controller;

import br.com.abce.advocacia.dao.UsuarioDao;
import br.com.abce.advocacia.model.Usuario;
import br.com.abce.advocacia.util.Mensagem;

import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class Login {

	public String login, senha;
	public Usuario usuario;
	boolean logado;

	@PostConstruct
	public void init() {
	}

	public String entrar() {

		usuario = null;
		try {
			usuario = new UsuarioDao().get(login);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (usuario == null) {
			Mensagem.Erro("Login n�o encontrado!", "");
			return "";
		}

		if (usuario.senha.equals(senha) == false) {
			Mensagem.Erro("Senha Inv�lida!", "");
			return "";
		}

		logado = true;
		return "menu";
	}

	public String sair() {
		logado = false;
		senha = "";
		usuario = new Usuario();
		return "login";
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public boolean isLogado() {
		return logado;
	}

	public void setLogado(boolean logado) {
		this.logado = logado;
	}

}
