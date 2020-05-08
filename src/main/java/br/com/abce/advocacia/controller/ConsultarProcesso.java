package br.com.abce.advocacia.controller;

import br.com.abce.advocacia.bean.NotaAndamento;
import br.com.abce.advocacia.bean.NotaDocumento;
import br.com.abce.advocacia.bean.ProcessoBean;
import br.com.abce.advocacia.bean.ProcessoCompletoBean;
import br.com.abce.advocacia.exceptions.InfraestruturaException;
import br.com.abce.advocacia.exceptions.RecursoNaoEncontradoException;
import br.com.abce.advocacia.exceptions.ValidacaoException;
import br.com.abce.advocacia.service.impl.NotaService;
import br.com.abce.advocacia.service.impl.ProcessoService;
import br.com.abce.advocacia.util.Consts;
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
public class ConsultarProcesso implements Serializable {

	private String filtro;
	private String situacao;
	private List<ProcessoBean> lista;
	private List<NotaAndamento> listaAndamentos;
	private List<NotaDocumento> listaDocumentos;

	private ProcessoBean processoBean;

	private Long idProcesso;

	private ProcessoCompletoBean processoCompletoBean;

	@Inject
	private ProcessoService processoService;

	@Inject
	private NotaService notaService;

	@PostConstruct
	public void init() {

		consultar();
	}

	public String consultar() {

		lista = new ArrayList<>();

		try {

			lista = processoService.listar();

		} catch (RecursoNaoEncontradoException ex) {
			Mensagem.info(ex.getMessage());
		} catch (Exception e) {
			LoggerUtil.error(e);
			Mensagem.erro(e.getMessage());
		}

		return "consultarProcesso";
	}

	public String detalhar(){

		try {

			if (processoBean == null) {

				if (idProcesso != null)

					processoBean = processoService.buscar(idProcesso);

			}

			listaAndamentos = carregaListaAndamentos(processoBean.getId());

			listaDocumentos = carregaListaDocumentos(processoBean.getId());

		} catch (RecursoNaoEncontradoException e) {
			Mensagem.info(e.getMessage());
		} catch (ValidacaoException e) {
			Mensagem.info(Consts.NAO_POSSIVEL_DADOS_PROCESSO);
		} catch (Exception e) {
			LoggerUtil.error(e.getMessage(), e);
			Mensagem.erro(e.getMessage());
		}

		return "detalharProcesso";

	}

	private List<NotaDocumento> carregaListaDocumentos(final Long idProcesso) throws InfraestruturaException, ValidacaoException {

		List<NotaDocumento> list = null;

		try {

			list = notaService.listarDocumentos(idProcesso);

		} catch (RecursoNaoEncontradoException e) {
			// ignore
		}

		return list;
	}

	private List<NotaAndamento> carregaListaAndamentos(final Long idProcesso) throws InfraestruturaException, ValidacaoException {

		List<NotaAndamento> list = null;

		try {

			list = notaService.listarAndamentos(idProcesso);

		} catch (RecursoNaoEncontradoException e) {
			// ignore
		}

		return list;
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

	public ProcessoBean getProcessoBean() {
		return processoBean;
	}

	public void setProcessoBean(ProcessoBean processoBean) {
		this.processoBean = processoBean;
	}

	public ProcessoCompletoBean getProcessoCompletoBean() {
		return processoCompletoBean;
	}

	public void setProcessoCompletoBean(ProcessoCompletoBean processoCompletoBean) {
		this.processoCompletoBean = processoCompletoBean;
	}

	public ProcessoService getProcessoService() {
		return processoService;
	}

	public void setProcessoService(ProcessoService processoService) {
		this.processoService = processoService;
	}

	public NotaService getNotaService() {
		return notaService;
	}

	public void setNotaService(NotaService notaService) {
		this.notaService = notaService;
	}

	public void setListaAndamentos(List<NotaAndamento> listaAndamentos) {
		this.listaAndamentos = listaAndamentos;
	}

	public void setListaDocumentos(List<NotaDocumento> listaDocumentos) {
		this.listaDocumentos = listaDocumentos;
	}

	public List<NotaAndamento> getListaAndamentos() {
		return listaAndamentos;
	}

	public List<NotaDocumento> getListaDocumentos() {
		return listaDocumentos;
	}

	public Long getIdProcesso() {
		return idProcesso;
	}

	public void setIdProcesso(Long idProcesso) {
		this.idProcesso = idProcesso;
	}
}
