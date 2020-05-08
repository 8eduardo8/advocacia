package br.com.abce.advocacia.controller;

import br.com.abce.advocacia.bean.EscritorioBean;
import br.com.abce.advocacia.exceptions.ValidacaoException;
import br.com.abce.advocacia.service.impl.EscritorioService;
import br.com.abce.advocacia.util.Consts;
import br.com.abce.advocacia.util.LoggerUtil;
import br.com.abce.advocacia.util.Mensagem;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@RequestScoped
public class CadastrarEscritorio extends GenericController implements Serializable {

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

			Mensagem.info(Consts.OPERACO_REALIZADA_SUCESSO);

		} catch (ValidacaoException e) {
			Mensagem.erro(e.getMessage());
			return "";
		} catch (Exception e) {
			Mensagem.erro(e.getMessage());
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
