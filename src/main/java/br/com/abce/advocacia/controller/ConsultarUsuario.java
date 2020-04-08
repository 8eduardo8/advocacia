package br.com.abce.advocacia.controller;

import br.com.abce.advocacia.dao.UsuarioDao;
import br.com.abce.advocacia.model.Usuario;
import br.com.abce.advocacia.util.Mensagem;

import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class ConsultarUsuario {

	public String filtro;
	public String perfil;
	public boolean ativo;
	public List<Usuario> lista;

	@PostConstruct
	public void init() {
		consultar();
	}

	public String consultar() {
		lista = new ArrayList<>();

		try {
			lista = new UsuarioDao().get();
		} catch (Exception e) {
			Mensagem.Erro("ERRO AO CONSULTAR!", e.getMessage());
			e.printStackTrace();
		}

		return "consultarUsuario";
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	public List<Usuario> getLista() {
		return lista;
	}

	public void setLista(List<Usuario> lista) {
		this.lista = lista;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

}
