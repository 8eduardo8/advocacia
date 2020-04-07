package br.com.abce.advocacia.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;

import br.com.abce.advocacia.dao.ProcessoDao;
import br.com.abce.advocacia.model.Processo;
import br.com.abce.advocacia.util.Mensagem;

@ManagedBean
@SessionScoped
public class ConsultarProcesso {

	public String filtro;
	public String situacao;
	public List<Processo> lista;

	@PostConstruct
	public void init() {
		consultar();
	}

	public String consultar() {
		lista = new ArrayList<>();

		try {
			lista = new ProcessoDao().get();
		} catch (Exception e) {
			Mensagem.Erro("ERRO AO CONSULTAR!", e.getMessage());
			e.printStackTrace();
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

	public List<Processo> getLista() {
		return lista;
	}

	public void setLista(List<Processo> lista) {
		this.lista = lista;
	}

}
