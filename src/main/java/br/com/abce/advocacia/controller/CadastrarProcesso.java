package br.com.abce.advocacia.controller;

import br.com.abce.advocacia.bean.ProcessoBean;
import br.com.abce.advocacia.bean.UsuarioBean;
import br.com.abce.advocacia.service.impl.ProcessoService;
import br.com.abce.advocacia.service.impl.UsuarioService;
import br.com.abce.advocacia.util.LoggerUtil;
import br.com.abce.advocacia.util.Mensagem;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class CadastrarProcesso implements Serializable {

	private ProcessoBean processoBean;
	private UsuarioBean usuarioSelecionado;
	private List<UsuarioBean> listaUsuarioBean;

	@Inject
	private UsuarioService usuarioService;

	@Inject
	private ProcessoService processoService;

	@PostConstruct
	public void init() {
		novo();
	}

	public String editar() {
		try {
			ProcessoBean aux = processoBean;
			novo();
			processoBean = aux;
		} catch (Exception e) {
			LoggerUtil.error(e);
		}
		return "cadastrarProcesso";
	}

	public String novo() {
		try {
			listaUsuarioBean = usuarioService.listar();
		} catch (Exception e) {
			LoggerUtil.error(e);
		}
		processoBean = new ProcessoBean();
		return "cadastrarProcesso";
	}

	public String salvar() {
		try {

			processoService.salvar(processoBean);

			Mensagem.info("ESCRITORIO SALVO!");
		} catch (Exception e) {
			Mensagem.erro("ERRO AO SALVAR", e.getMessage());
			LoggerUtil.error(e);
			return "";
		}

		return novo();
	}

	public String adicionar() {
		try {

			processoBean.getListaUsuarios().add(usuarioSelecionado);
		} catch (Exception e) {
			Mensagem.warn("ERRO ADICIONAR", e.getMessage());
			LoggerUtil.error(e);
		}
		return "";
	}

	public String remover(UsuarioBean item) {
		processoBean.getListaUsuarios().remove(item);
		return "";
	}

	public ProcessoBean getProcessoBean() {
		return processoBean;
	}

	public void setProcessoBean(ProcessoBean processoBean) {
		this.processoBean = processoBean;
	}

	public List<UsuarioBean> getListaUsuarioBean() {
		return listaUsuarioBean;
	}

	public void setListaUsuarioBean(List<UsuarioBean> listaUsuarioBean) {
		this.listaUsuarioBean = listaUsuarioBean;
	}

	public UsuarioBean getUsuarioSelecionado() {
		return usuarioSelecionado;
	}

	public void setUsuarioSelecionado(UsuarioBean usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}
}
