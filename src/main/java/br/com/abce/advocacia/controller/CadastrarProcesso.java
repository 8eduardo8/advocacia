package br.com.abce.advocacia.controller;

import br.com.abce.advocacia.dao.ProcessoDao;
import br.com.abce.advocacia.dao.ProcessoUsuarioDao;
import br.com.abce.advocacia.dao.UsuarioDao;
import br.com.abce.advocacia.model.Processo;
import br.com.abce.advocacia.model.ProcessoUsuario;
import br.com.abce.advocacia.model.Usuario;
import br.com.abce.advocacia.util.Mensagem;

import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class CadastrarProcesso {

	public Processo processo;
	public int usuarioSelecionado;
	public List<WrapperProcessoUsuario> listaProcessoUsuario;
	public List<Usuario> listaUsuario;

	@PostConstruct
	public void init() {
		novo();
	}

	public String editar() {
		try {
			Processo aux = processo;
			novo();
			processo = aux;
			List<ProcessoUsuario> lista = new ProcessoUsuarioDao().get(processo.id);
			listaProcessoUsuario = new ArrayList<>();
			for (ProcessoUsuario pu : lista) {
				listaProcessoUsuario.add(new WrapperProcessoUsuario(pu));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "cadastrarProcesso";
	}

	public String novo() {
		try {
			listaUsuario = new UsuarioDao().get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		processo = new Processo();
		listaProcessoUsuario = new ArrayList<>();
		return "cadastrarProcesso";
	}

	public String salvar() {
		try {
			processo.numero = processo.numero.toUpperCase().trim();
			processo.area = processo.area.toUpperCase().trim();

			new ProcessoDao().salvar(processo);

			for (WrapperProcessoUsuario wrapper : listaProcessoUsuario) {
				wrapper.processoUsuario.processo = processo.id;
				new ProcessoUsuarioDao().salvar(wrapper.processoUsuario);
			}

			Mensagem.Info("ESCRITÓRIO SALVO!");
		} catch (Exception e) {
			Mensagem.Erro("ERRO AO SALVAR", e.getMessage());
			e.printStackTrace();
			return "";
		}

		return novo();
	}

	public String adicionar() {
		try {
			ProcessoUsuario pu = new ProcessoUsuario();
			pu.usuario = usuarioSelecionado;

			WrapperProcessoUsuario wrapper = new WrapperProcessoUsuario(pu);
			listaProcessoUsuario.add(wrapper);
		} catch (Exception e) {
			Mensagem.Warn("ERRO ADICIONAR", e.getMessage());
			e.printStackTrace();
		}
		return "";
	}

	public String remover(WrapperProcessoUsuario item) {
		listaProcessoUsuario.remove(item);
		return "";
	}

	public Processo getProcesso() {
		return processo;
	}

	public void setProcesso(Processo processo) {
		this.processo = processo;
	}

	public List<WrapperProcessoUsuario> getListaProcessoUsuario() {
		return listaProcessoUsuario;
	}

	public void setListaProcessoUsuario(List<WrapperProcessoUsuario> listaProcessoUsuario) {
		this.listaProcessoUsuario = listaProcessoUsuario;
	}

	public List<Usuario> getListaUsuario() {
		return listaUsuario;
	}

	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}

	public int getUsuarioSelecionado() {
		return usuarioSelecionado;
	}

	public void setUsuarioSelecionado(int usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}

	//
	public class WrapperProcessoUsuario {
		public ProcessoUsuario processoUsuario;
		public Usuario usuario;

		public WrapperProcessoUsuario(ProcessoUsuario pu) throws Exception {
			this.processoUsuario = pu;
			usuario = new UsuarioDao().get(pu.usuario);
		}

		public ProcessoUsuario getProcessoUsuario() {
			return processoUsuario;
		}

		public void setProcessoUsuario(ProcessoUsuario processoUsuario) {
			this.processoUsuario = processoUsuario;
		}

		public Usuario getUsuario() {
			return usuario;
		}

		public void setUsuario(Usuario usuario) {
			this.usuario = usuario;
		}
	}
}
