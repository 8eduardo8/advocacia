package br.com.abce.advocacia.bean;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@ApiModel
public class NotaBean implements Serializable {

	private final List<String> listaTipo = Arrays.asList("MENSAGEM", "ANDAMENTO", "DOCUMENTO");

	private int id;
	private String tipo;
	private Long idUsuario;
	private Long idProcesso;
	private Long idProcessoUsuario;

	private Date dataCadastro;
	private Date dataAtualizacao;
	private Date dataExclusao;

	private NotaDocumento notaDocumento;
	private NotaMensagem notaMensagem;

	public NotaBean() {
		super();
	}

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

	public NotaMensagem getNotaMensagem() {
		return notaMensagem;
	}

	public void setNotaMensagem(NotaMensagem notaMensagem) {
		this.notaMensagem = notaMensagem;
	}

	public NotaDocumento getNotaDocumento() {
		return notaDocumento;
	}

	public void setNotaDocumento(NotaDocumento notaDocumento) {
		this.notaDocumento = notaDocumento;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Long getIdProcesso() {
		return idProcesso;
	}

	public void setIdProcesso(Long idProcesso) {
		this.idProcesso = idProcesso;
	}

	public Long getIdProcessoUsuario() {
		return idProcessoUsuario;
	}

	public void setIdProcessoUsuario(Long idProcessoUsuario) {
		this.idProcessoUsuario = idProcessoUsuario;
	}
}
