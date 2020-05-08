package br.com.abce.advocacia.controller;

import br.com.abce.advocacia.bean.EscritorioBean;
import br.com.abce.advocacia.exceptions.RecursoNaoEncontradoException;
import br.com.abce.advocacia.service.impl.EscritorioService;
import br.com.abce.advocacia.util.LoggerUtil;
import br.com.abce.advocacia.util.Mensagem;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ConversationScoped
public class ConsultarEscritorio implements Serializable {

	private String filtro;
	private boolean ativo;
	private List<EscritorioBean> lista;

	@Inject
	private EscritorioService escritorioService;

	@PostConstruct
	public void init() {

		consultar();
	}

	public String consultar() {

		lista = new ArrayList<>();

		try {

			lista = escritorioService.listar();

		} catch (RecursoNaoEncontradoException ex) {
			Mensagem.info(ex.getMessage());
		} catch (Exception e) {
			Mensagem.erro(e.getMessage());
			LoggerUtil.error(e);
		}

		return "consultarEscritorio";
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public List<EscritorioBean> getLista() {
		return lista;
	}

	public void setLista(List<EscritorioBean> lista) {
		this.lista = lista;
	}

}
