package br.com.abce.advocacia.bean;

import br.com.abce.advocacia.util.Consts;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;

import java.beans.Transient;
import java.io.Serializable;

@ApiModel
public class NotaMensagem implements Serializable {

	private int nota;
	private String mensagem;
	private int tipo;

	public NotaMensagem() {
		super();
	}

	public int getNota() {
		return nota;
	}

	public void setNota(int nota) {
		this.nota = nota;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	@Transient
	@JsonIgnoreProperties
	public boolean isTipoMensagem() {
		return this.tipo == Consts.TIPO_MENSAGEM;
	}

}
