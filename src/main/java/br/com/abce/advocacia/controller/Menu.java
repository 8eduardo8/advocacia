package br.com.abce.advocacia.controller;

import br.com.abce.advocacia.bean.NotaBean;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class Menu implements Serializable {

	List<WrapperNota> lista;

	@PostConstruct
	public void init() {
		menu();
	}

	public String menu() {

		lista = new ArrayList<>();
		lista.add(new WrapperNota());
		lista.add(new WrapperNota());
		lista.add(new WrapperNota());
		lista.add(new WrapperNota());
		lista.add(new WrapperNota());
		lista.add(new WrapperNota());
		lista.add(new WrapperNota());
		lista.add(new WrapperNota());
		lista.add(new WrapperNota());
		lista.add(new WrapperNota());
		lista.add(new WrapperNota());
		lista.add(new WrapperNota());
		lista.add(new WrapperNota());
		lista.add(new WrapperNota());
		lista.add(new WrapperNota());
		lista.add(new WrapperNota());
		lista.add(new WrapperNota());
		lista.add(new WrapperNota());
		lista.add(new WrapperNota());
		lista.add(new WrapperNota());
		lista.add(new WrapperNota());
		lista.add(new WrapperNota());
		lista.add(new WrapperNota());
		lista.add(new WrapperNota());
		lista.add(new WrapperNota());
		lista.add(new WrapperNota());
		lista.add(new WrapperNota());

		return "menu";
	}

	public class WrapperNota {

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

}
