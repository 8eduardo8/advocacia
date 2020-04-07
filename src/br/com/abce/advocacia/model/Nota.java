package br.com.abce.advocacia.model;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Nota {

	public final List<String> listaTipo = Arrays.asList("MENSAGEM", "ANDAMENTO", "DOCUMENTO");

	public int id;
	public String tipo;
	public int usuario;

	public Date dataCadastro;
	public Date dataAtualizacao;
	public Date dataExclusao;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
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

	public List<String> getListaTipo() {
		return listaTipo;
	}

}
