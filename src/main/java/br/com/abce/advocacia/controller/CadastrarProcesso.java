package br.com.abce.advocacia.controller;

import br.com.abce.advocacia.bean.ProcessoBean;
import br.com.abce.advocacia.bean.UsuarioBean;
import br.com.abce.advocacia.bean.UsuarioResumidoBean;
import br.com.abce.advocacia.exceptions.AdvocaciaException;
import br.com.abce.advocacia.service.impl.ProcessoService;
import br.com.abce.advocacia.service.impl.UsuarioService;
import br.com.abce.advocacia.util.LoggerUtil;
import br.com.abce.advocacia.util.Mensagem;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@RequestScoped
public class CadastrarProcesso implements Serializable {

	private ProcessoBean processoBean;
	private UsuarioBean usuarioSelecionado;
	private List<UsuarioResumidoBean> listaUsuarioBean;

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

			listaUsuarioBean = usuarioService.listarResumido();

		} catch (AdvocaciaException ex) {
			Mensagem.info(ex.getMessage());
		} catch (Exception e) {
			LoggerUtil.error(e);
		}
		processoBean = new ProcessoBean();
		return "cadastrarProcesso";
	}

	public String salvar() {
		try {

			processoService.salvar(processoBean);

			processoBean = processoService.buscarPorNumero(processoBean.getNumero());

			Mensagem.info("PROCESSO SALVO!");

		} catch (AdvocaciaException ex) {
			Mensagem.info(ex.getMessage());
		} catch (Exception e) {
			Mensagem.erro("ERRO AO SALVAR", e.getMessage());
			LoggerUtil.error(e);
			return "";
		}

		return novo();
	}

	public String adicionar() {

		try {

			if (!processoBean.getListaUsuarios().contains(usuarioSelecionado)) {
				if (processoBean.getId() != null)
					processoService.addUsuario(usuarioSelecionado.getId(), processoBean.getId());
				processoBean.getListaUsuarios().add(usuarioSelecionado);
			} else {
				Mensagem.info("Usuário já consta na relação de envolvidos do processo.");
			}

		} catch (AdvocaciaException ex) {
			Mensagem.info(ex.getMessage());
		} catch (Exception e) {
			Mensagem.erro("ERRO AO SALVAR", e.getMessage());
			LoggerUtil.error(e);
			return "";
		}
		return "";
	}

	public String remover(UsuarioBean item) {

		try {

			if (processoBean.getListaUsuarios().contains(usuarioSelecionado)) {
				if (processoBean.getId() != null)
					processoService.remover(usuarioSelecionado.getId(), processoBean.getId());
				processoBean.getListaUsuarios().remove(item);
			}  else {
				Mensagem.info("Usuário não consta na relação de envolvidos do processo.");
			}

		} catch (AdvocaciaException ex) {
			Mensagem.info(ex.getMessage());
		} catch (Exception e) {
			Mensagem.erro("ERRO AO SALVAR", e.getMessage());
			LoggerUtil.error(e);
			return "";
		}

		return "";
	}

	public ProcessoBean getProcessoBean() {
		return processoBean;
	}

	public void setProcessoBean(ProcessoBean processoBean) {
		this.processoBean = processoBean;
	}

	public List<UsuarioResumidoBean> getListaUsuarioBean() {
		return listaUsuarioBean;
	}

	public void setListaUsuarioBean(List<UsuarioResumidoBean> listaUsuarioBean) {
		this.listaUsuarioBean = listaUsuarioBean;
	}

	public UsuarioBean getUsuarioSelecionado() {
		return usuarioSelecionado;
	}

	public void setUsuarioSelecionado(UsuarioBean usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}
}
