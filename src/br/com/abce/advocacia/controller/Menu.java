package br.com.abce.advocacia.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;

import br.com.abce.advocacia.model.Nota;

@ManagedBean
@SessionScoped
public class Menu {

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
		public Nota nota;
		public String mensagem;
	}

	public List<WrapperNota> getLista() {
		return lista;
	}

	public void setLista(List<WrapperNota> lista) {
		this.lista = lista;
	}

}
