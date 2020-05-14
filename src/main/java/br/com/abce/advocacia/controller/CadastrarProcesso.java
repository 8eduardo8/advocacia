package br.com.abce.advocacia.controller;

import br.com.abce.advocacia.bean.ProcessoBean;
import br.com.abce.advocacia.bean.UsuarioBean;
import br.com.abce.advocacia.bean.UsuarioResumidoBean;
import br.com.abce.advocacia.exceptions.RecursoNaoEncontradoException;
import br.com.abce.advocacia.exceptions.ValidacaoException;
import br.com.abce.advocacia.service.impl.ProcessoService;
import br.com.abce.advocacia.service.impl.UsuarioService;
import br.com.abce.advocacia.util.Consts;
import br.com.abce.advocacia.util.LoggerUtil;
import br.com.abce.advocacia.util.Mensagem;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class CadastrarProcesso extends GenericController implements Serializable {

	public static final String CADASTRAR_PROCESSO = "cadastrarProcesso";

	private ProcessoBean processoBean;
	private UsuarioResumidoBean usuarioSelecionado;
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

			final String idProcesso = getProcessoIdFromRequest();

			if (StringUtils.isNotBlank(idProcesso) && processoBean == null)

				processoBean = processoService.buscar(Long.parseLong(idProcesso));

			ProcessoBean aux = processoBean;
			novo();
			processoBean = aux;

		} catch (ValidacaoException | RecursoNaoEncontradoException e) {
			Mensagem.info(e.getMessage());
		} catch (Exception e) {
			Mensagem.erro(e.getMessage());
			LoggerUtil.error(e);
		}
		return CADASTRAR_PROCESSO;
	}

	public String novo() {

		try {

			listaUsuarioBean = usuarioService.listarResumido();

		} catch (RecursoNaoEncontradoException ex) {
			Mensagem.info(ex.getMessage());
		} catch (Exception e) {
			LoggerUtil.error(e);
		}
		processoBean = new ProcessoBean();
		return CADASTRAR_PROCESSO;
	}

	public String salvar() {
		try {

			processoService.salvar(processoBean);

			processoBean = processoService.buscarPorNumero(processoBean.getNumero());

			Mensagem.info(Consts.OPERACO_REALIZADA_SUCESSO);

		} catch (ValidacaoException ex) {
			Mensagem.info(ex.getMessage());
		} catch (Exception e) {
			Mensagem.erro(e.getMessage());
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

		} catch (ValidacaoException ex) {
			Mensagem.info(ex.getMessage());
		} catch (Exception e) {
			Mensagem.erro(e.getMessage());
			LoggerUtil.error(e);
			return "";
		}
		return CADASTRAR_PROCESSO;
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

		} catch (ValidacaoException ex) {
			Mensagem.info(ex.getMessage());
		} catch (Exception e) {
			Mensagem.erro(e.getMessage());
			LoggerUtil.error(e);
			return "";
		}

		return CADASTRAR_PROCESSO;
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

	public UsuarioResumidoBean getUsuarioSelecionado() {
		return usuarioSelecionado;
	}

	public void setUsuarioSelecionado(UsuarioResumidoBean usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}
}
