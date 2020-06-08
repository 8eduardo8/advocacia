package br.com.abce.advocacia.controller;

import br.com.abce.advocacia.bean.UsuarioBean;
import br.com.abce.advocacia.exceptions.InfraestruturaException;
import br.com.abce.advocacia.exceptions.RecursoNaoEncontradoException;
import br.com.abce.advocacia.service.impl.UsuarioService;
import br.com.abce.advocacia.util.LoggerUtil;
import br.com.abce.advocacia.util.Mensagem;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class ConsultarUsuario implements Serializable {

	private String filtro;
	private int perfil;
	private boolean ativo;
	private List<UsuarioBean> lista;

	@Inject
	private UsuarioService usuarioService;

	@PostConstruct
	public void init() {

		ativo = true;

//		consultar();
	}

	public String consultar() {

		lista = new ArrayList<>();

		try {

			lista = usuarioService.listar(filtro, perfil, ativo);

		} catch (RecursoNaoEncontradoException ex) {
			Mensagem.info(ex.getMessage());
		} catch (InfraestruturaException ex) {
			LoggerUtil.error(ex);
			Mensagem.erro(ex.getMessage());
		}

		return "consultarUsuario";
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	public List<UsuarioBean> getLista() {
		return lista;
	}

	public void setLista(List<UsuarioBean> lista) {
		this.lista = lista;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public boolean getAtivo(){
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public int getPerfil() {
		return perfil;
	}

	public void setPerfil(int perfil) {
		this.perfil = perfil;
	}

}
