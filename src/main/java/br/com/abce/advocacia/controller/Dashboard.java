package br.com.abce.advocacia.controller;

import br.com.abce.advocacia.bean.NotaBean;
import br.com.abce.advocacia.bean.ProcessoBean;
import br.com.abce.advocacia.exceptions.InfraestruturaException;
import br.com.abce.advocacia.exceptions.RecursoNaoEncontradoException;
import br.com.abce.advocacia.exceptions.ValidacaoException;
import br.com.abce.advocacia.service.impl.NotaService;
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

import static br.com.abce.advocacia.util.Consts.NAO_POSSIVEL_DADOS_USUARIO;

@Named
@SessionScoped
public class Dashboard implements Serializable {

	private List<WrapperNota> lista;

	private List<ProcessoBean> listaProcessos = new ArrayList<>();

	private List<NotaBean> listaNotas = new ArrayList<>();

	@Inject
	private ProcessoService processoService;

	@Inject
	private NotaService notaService;

	@Inject
	private Login login;

	@PostConstruct
	public void init() {

		carregaDashboard();
	}

	public String carregaDashboard() {

		final Long idUsuario = login.getUsuarioBean().getId();

		carregaProcessos(idUsuario);

		carregaNotas(idUsuario);

		return "dashboard";
	}

	private void carregaNotas(final Long idUsuario) {

		try {

			listaNotas = notaService.listar(idUsuario);

		} catch (ValidacaoException e) {
			Mensagem.info(NAO_POSSIVEL_DADOS_USUARIO);
		} catch (RecursoNaoEncontradoException e) {
			Mensagem.info(e.getMessage());
		} catch (InfraestruturaException e) {
			LoggerUtil.error(e);
			Mensagem.erro(e.getMessage());
		}
	}

	private void carregaProcessos(final Long idUsuario) {

		try {

			listaProcessos = processoService.listar(idUsuario);

		} catch (ValidacaoException e) {
			Mensagem.info(NAO_POSSIVEL_DADOS_USUARIO);
		} catch (RecursoNaoEncontradoException e) {
			Mensagem.info(e.getMessage());
		} catch (InfraestruturaException e) {
			LoggerUtil.error(e);
			Mensagem.erro(e.getMessage());
		}
	}

	public List<NotaBean> getListaNotas() {
		return listaNotas;
	}

	public void setListaNotas(List<NotaBean> listaNotas) {
		this.listaNotas = listaNotas;
	}

	public List<ProcessoBean> getListaProcessos() {
		return listaProcessos;
	}

	public void setListaProcessos(List<ProcessoBean> listaProcessos) {
		this.listaProcessos = listaProcessos;
	}

	public class WrapperNota implements Serializable {

		private NotaBean notaBean;
		private String mensagem;

		public WrapperNota() {
			super();
		}

		public String getMensagem() {
			return mensagem;
		}

		public void setMensagem(String mensagem) {
			this.mensagem = mensagem;
		}

		public NotaBean getNotaBean() {
			return notaBean;
		}

		public void setNotaBean(NotaBean notaBean) {
			this.notaBean = notaBean;
		}
	}

	public List<WrapperNota> getLista() {
		return lista;
	}

	public void setLista(List<WrapperNota> lista) {
		this.lista = lista;
	}

	public Login getLogin() {
		return login;
	}
}
