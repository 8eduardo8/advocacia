package br.com.abce.advocacia.controller;

import br.com.abce.advocacia.bean.EscritorioBean;
import br.com.abce.advocacia.exceptions.AdvocaciaException;
import br.com.abce.advocacia.service.impl.EscritorioService;
import br.com.abce.advocacia.util.LoggerUtil;
import br.com.abce.advocacia.util.Mensagem;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class CadastrarEscritorio implements Serializable {

	public static final String CADASTRAR_ESCRITORIO = "cadastrarEscritorio";

	private EscritorioBean escritorioBean;

	@Inject
	private EscritorioService escritorioService;

	@PostConstruct
	public void init() {
		novo();
	}

	public String editar() {
		return CADASTRAR_ESCRITORIO;
	}

	public String novo() {
		escritorioBean = new EscritorioBean();
		return CADASTRAR_ESCRITORIO;
	}

	public String salvar() {

		try {

			escritorioService.salvar(escritorioBean);

			Mensagem.info("ESCRITÓRIO SALVO!");

		} catch (AdvocaciaException e) {
			Mensagem.erro("ERRO AO SALVAR", e.getMessage());
			return "";
		} catch (Exception e) {
			Mensagem.erro("ERRO AO SALVAR", e.getMessage());
			LoggerUtil.error(e);
			return "";
		}

		return novo();
	}

	public EscritorioBean getEscritorioBean() {
		return escritorioBean;
	}

	public void setEscritorioBean(EscritorioBean escritorioBean) {
		this.escritorioBean = escritorioBean;
	}

}
