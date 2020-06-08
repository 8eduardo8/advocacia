package br.com.abce.advocacia.controller;

import br.com.abce.advocacia.bean.*;
import br.com.abce.advocacia.exceptions.InfraestruturaException;
import br.com.abce.advocacia.exceptions.RecursoNaoEncontradoException;
import br.com.abce.advocacia.exceptions.ValidacaoException;
import br.com.abce.advocacia.service.impl.NotaService;
import br.com.abce.advocacia.service.impl.ProcessoService;
import br.com.abce.advocacia.service.impl.UsuarioService;
import br.com.abce.advocacia.util.Consts;
import br.com.abce.advocacia.util.LoggerUtil;
import br.com.abce.advocacia.util.Mensagem;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Named
@RequestScoped
public class ConsultarProcesso implements Serializable {

	private String filtro;
	private Integer situacao;
	private Date dataInicio;
	private Long usuarioId;

	private List<ProcessoBean> lista;
	private List<NotaAndamento> listaAndamentos;
	private List<NotaDocumento> listaDocumentos;
	private List<UsuarioResumidoBean> listaUsuarioResumido;

	private ProcessoBean processoBean;

	private Long idProcesso;

	private ProcessoCompletoBean processoCompletoBean;

	@Inject
	private ProcessoService processoService;

	@Inject
	private NotaService notaService;

	@Inject
	private UsuarioService usuarioService;

	@PostConstruct
	public void init() {

		consultar();
	}

	public String consultar() {

		lista = new ArrayList<>();

		try {

			lista = processoService.listar(getFiltro(), getSituacao(), getDataInicio(), getUsuarioId());

			listaUsuarioResumido = usuarioService.listarResumido();

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

			if (processoBean == null
				&& idProcesso != null) {

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

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Integer getSituacao() {
		return situacao;
	}

	public void setSituacao(Integer situacao) {
		this.situacao = situacao;
	}

	public List<UsuarioResumidoBean> getListaUsuarioResumido() {
		return listaUsuarioResumido;
	}

	public void setListaUsuarioResumido(List<UsuarioResumidoBean> listaUsuarioResumido) {
		this.listaUsuarioResumido = listaUsuarioResumido;
	}

	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}
}
