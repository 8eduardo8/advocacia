package br.com.abce.advocacia.bean;

import br.com.abce.advocacia.SituacaoProcesso;
import br.com.abce.advocacia.converter.Bean;
import io.swagger.annotations.ApiModel;

import java.beans.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ApiModel
public class ProcessoBean implements Serializable, Bean {

	private Long id;
	private String numero;
	private String area;
	private int situacao;
	private Date dataInicio;
	private String comarca;

	private Date dataCadastro;
	private Date dataAtualizacao;
	private Date dataExclusao;

	private List<UsuarioResumidoBean> listaUsuarios = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public int getSituacao() {
		return situacao;
	}

	public void setSituacao(int situacao) {
		this.situacao = situacao;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
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

	public List<UsuarioResumidoBean> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(List<UsuarioResumidoBean> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public String getComarca() {
		return comarca;
	}

	public void setComarca(String comarca) {
		this.comarca = comarca;
	}

	@Transient
	public String getDescSituacao(){
		SituacaoProcesso situacaoProcesso = SituacaoProcesso.getSituacaoProcesso(this.getSituacao());
		return situacaoProcesso != null ? situacaoProcesso.getDescricao() : "Desconhecido";
	}
}
