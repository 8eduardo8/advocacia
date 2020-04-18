package br.com.abce.advocacia.bean;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.util.Date;

@ApiModel
public class NotaDocumento implements Serializable {

	private Long idDocumento;
	private String nome;
	private String descricao;
	private byte[] arquivo;
	private String formato;
	private Long idUsuario;
	private Long idProcesso;

	private Date dataCadastro;

	public NotaDocumento() {
		super();
	}

	public Long getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(Long idDocumento) {
		this.idDocumento = idDocumento;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public byte[] getArquivo() {
		return arquivo;
	}

	public void setArquivo(byte[] arquivo) {
		this.arquivo = arquivo;
	}

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Long getIdProcesso() {
		return idProcesso;
	}

	public void setIdProcesso(Long idProcesso) {
		this.idProcesso = idProcesso;
	}
}
