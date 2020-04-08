package br.com.abce.advocacia.controller;

import br.com.abce.advocacia.dao.EscritorioDao;
import br.com.abce.advocacia.model.Escritorio;
import br.com.abce.advocacia.util.Mensagem;

import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class CadastrarEscritorio {

	public Escritorio escritorio;

	@PostConstruct
	public void init() {
		novo();
	}

	public String editar() {
		return "cadastrarEscritorio";
	}

	public String novo() {
		escritorio = new Escritorio();
		return "cadastrarEscritorio";
	}

	public String salvar() {
		try {
			escritorio.cnpj = escritorio.cnpj.toUpperCase().trim();
			escritorio.razao = escritorio.razao.toUpperCase().trim();
			escritorio.fantasia = escritorio.fantasia.toUpperCase().trim();
			escritorio.endereco = escritorio.endereco.toUpperCase().trim();

			new EscritorioDao().salvar(escritorio);
			Mensagem.Info("ESCRIT�RIO SALVO!");
		} catch (Exception e) {
			Mensagem.Erro("ERRO AO SALVAR", e.getMessage());
			e.printStackTrace();
			return "";
		}

		return novo();
	}

	public Escritorio getEscritorio() {
		return escritorio;
	}

	public void setEscritorio(Escritorio escritorio) {
		this.escritorio = escritorio;
	}

}
