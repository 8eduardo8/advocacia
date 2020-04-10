package br.com.abce.advocacia.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class Mensagem {

	private Mensagem() {
		super();
	}

	public static void erro(String mensagem) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, ""));
	}

	public static void erro(String mensagem, String detalhe) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, detalhe));
	}

	public static void info(String mensagem) {
		info(mensagem, "");
	}

	public static void info(String mensagem, String detalhe) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, mensagem, detalhe));
	}

	public static void warn(String mensagem, String detalhe) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_WARN, mensagem, detalhe));
	}
}
