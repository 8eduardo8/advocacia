package br.com.abce.advocacia.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class Mensagem {

	public static void Erro(String mensagem, String detalhe) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, detalhe));
	}

	public static void Info(String mensagem) {
		Info(mensagem, "");
	}

	public static void Info(String mensagem, String detalhe) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, mensagem, detalhe));
	}

	public static void Warn(String mensagem, String detalhe) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_WARN, mensagem, detalhe));
	}
}
