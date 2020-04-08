package br.com.abce.advocacia.controller;

import br.com.abce.advocacia.dao.UsuarioDao;
import br.com.abce.advocacia.model.Usuario;
import br.com.abce.advocacia.util.Mensagem;

import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class CadastrarUsuario {

	public Usuario usuario;

	public String senhaAtual, novaSenha, confirmaSenha;

	@PostConstruct
	public void init() {
		novo();
	}

	public String editar() {
		senhaAtual = novaSenha = confirmaSenha = "";
		return "cadastrarUsuario";
	}

	public String novo() {
		usuario = new Usuario();
		senhaAtual = novaSenha = confirmaSenha = "";
		return "cadastrarUsuario";
	}

	public String salvar() {

		try {

			usuario.nome = usuario.nome.toUpperCase().trim();
			usuario.sobrenome = usuario.sobrenome.toUpperCase().trim();
			usuario.email = usuario.email.toLowerCase().trim();
			usuario.login = usuario.login.toLowerCase().trim();

			new UsuarioDao().salvar(usuario);
			Mensagem.Info("USU�RIO SALVO!");
		} catch (Exception e) {
			Mensagem.Erro("ERRO AO SALVAR", e.getMessage());
			e.printStackTrace();
			return "";
		}

		return novo();
	}

	public String alterarSenha() {
		if (senhaAtual.equals(usuario.senha) == false) {
			Mensagem.Erro("A SENHA DIGITADA N�O CONFERE COM A SUA SENHA!", "");
			return "";
		} else if (novaSenha.equals(confirmaSenha) == false) {
			Mensagem.Erro("CONFIRMA��O DA SNEHA N�O CONFERE COM A NOVA SENHA!", "");
			return "";
		}

		try {
			usuario = new UsuarioDao().get(usuario.id);
			if (usuario != null) {
				usuario.senha = novaSenha;
				new UsuarioDao().salvar(usuario);
				Mensagem.Info("SENHA ALTERADA!");
			}
		} catch (Exception e) {
			Mensagem.Erro("ERRO AO SALVAR!", e.getMessage());
			e.printStackTrace();
			return "";
		}
		return "";
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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

}
