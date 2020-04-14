package br.com.abce.advocacia.controller;

import br.com.abce.advocacia.bean.ProcessoBean;
import br.com.abce.advocacia.exceptions.AdvocaciaException;
import br.com.abce.advocacia.service.impl.ProcessoService;
import br.com.abce.advocacia.util.LoggerUtil;
import br.com.abce.advocacia.util.Mensagem;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class ConsultarProcesso implements Serializable {

	private String filtro;
	private String situacao;
	private List<ProcessoBean> lista;

	@Inject
	private ProcessoService processoService;

	@PostConstruct
	public void init() {
	}

	public String consultar() {
		lista = new ArrayList<>();

		try {

			lista = processoService.listar();

		} catch (AdvocaciaException ex) {
			Mensagem.info(ex.getMessage());
		} catch (Exception e) {
			LoggerUtil.error(e);
			Mensagem.erro("ERRO AO CONSULTAR!", e.getMessage());
		}

		return "consultarProcesso";
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public List<ProcessoBean> getLista() {
		return lista;
	}

	public void setLista(List<ProcessoBean> lista) {
		this.lista = lista;
	}

}
