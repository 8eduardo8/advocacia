package br.com.abce.advocacia.bean;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@ApiModel
public class ProcessoBean implements Serializable, Bean {

	private Long id;
	private String numero;
	private String area;
	private String situacao;
	private Date dataInicio;
	private String comarca;

	private Date dataCadastro;
	private Date dataAtualizacao;
	private Date dataExclusao;

	private List<UsuarioBean> listaUsuarios = new ArrayList<>();

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

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
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

	public List<UsuarioBean> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(List<UsuarioBean> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public String getComarca() {
		return comarca;
	}

	public void setComarca(String comarca) {
		this.comarca = comarca;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ProcessoBean that = (ProcessoBean) o;
		return Objects.equals(getId(), that.getId()) &&
				Objects.equals(getNumero(), that.getNumero()) &&
				Objects.equals(getArea(), that.getArea()) &&
				Objects.equals(getSituacao(), that.getSituacao()) &&
				Objects.equals(getDataInicio(), that.getDataInicio()) &&
				Objects.equals(getComarca(), that.getComarca()) &&
				Objects.equals(getDataCadastro(), that.getDataCadastro()) &&
				Objects.equals(getDataAtualizacao(), that.getDataAtualizacao()) &&
				Objects.equals(getDataExclusao(), that.getDataExclusao()) ;
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getNumero(), getArea(), getSituacao(), getDataInicio(), getComarca(), getDataCadastro(), getDataAtualizacao(), getDataExclusao());
	}
}
