package br.com.abce.advocacia.bean;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.util.Date;

@ApiModel
public class UsuarioEscritorioBean implements Serializable {

	private int usuario;
	private int escritorio;

	private Date dataCadastro;
	private Date dataAtualizacao;
	private Date dataExclusao;

	public UsuarioEscritorioBean() {
		super();
	}

	public int getUsuario() {
		return usuario;
	}

	public void setUsuario(int usuario) {
		this.usuario = usuario;
	}

	public int getEscritorio() {
		return escritorio;
	}

	public void setEscritorio(int escritorio) {
		this.escritorio = escritorio;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Date getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public Date getDataExclusao() {
		return dataExclusao;
	}

	public void setDataExclusao(Date dataExclusao) {
		this.dataExclusao = dataExclusao;
	}
}
