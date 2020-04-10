package br.com.abce.advocacia.bean;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.util.Date;

@ApiModel
public class ProcessoUsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private int processo;
	private int usuario;

	private Date dataCadastro;
	private Date dataAtualizacao;
	private Date dataExclusao;

	public int getProcesso() {
		return processo;
	}

	public void setProcesso(int processo) {
		this.processo = processo;
	}

	public int getUsuario() {
		return usuario;
	}

	public void setUsuario(int usuario) {
		this.usuario = usuario;
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
