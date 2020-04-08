package br.com.abce.advocacia.controller;

import br.com.abce.advocacia.dao.EscritorioDao;
import br.com.abce.advocacia.model.Escritorio;
import br.com.abce.advocacia.util.Mensagem;

import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class ConsultarEscritorio {

	public String filtro;
	public boolean ativo;
	public List<Escritorio> lista;

	@PostConstruct
	public void init() {
		consultar();
	}

	public String consultar() {
		lista = new ArrayList<>();

		try {
			lista = new EscritorioDao().get();
		} catch (Exception e) {
			Mensagem.Erro("ERRO AO CONSULTAR!", e.getMessage());
			e.printStackTrace();
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

	public List<Escritorio> getLista() {
		return lista;
	}

	public void setLista(List<Escritorio> lista) {
		this.lista = lista;
	}

}
