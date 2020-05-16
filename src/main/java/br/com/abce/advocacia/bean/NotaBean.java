package br.com.abce.advocacia.bean;

import br.com.abce.advocacia.util.Consts;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;

import java.beans.Transient;
import java.io.Serializable;
import java.util.Date;

@ApiModel
public class NotaBean implements Serializable {

	private Long id;
	private Integer tipo;
	private Long idUsuario;
	private Long idProcesso;
	private Long idProcessoUsuario;

	private Date dataCadastro;
	private Date dataAtualizacao;
	private Date dataExclusao;

	private NotaDocumento notaDocumento;
	private NotaMensagem notaMensagem;

	private UsuarioResumidoBean usuarioResumidoBean;

	public NotaBean() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
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

	public UsuarioResumidoBean getUsuarioResumidoBean() {
		return usuarioResumidoBean;
	}

	public void setUsuarioResumidoBean(UsuarioResumidoBean usuarioResumidoBean) {
		this.usuarioResumidoBean = usuarioResumidoBean;
	}

	@Transient
	@JsonIgnoreProperties
	public boolean mensagem() {
		return this.tipo == Consts.TIPO_MENSAGEM;
	}

	@Transient
	@JsonIgnoreProperties
	public boolean andamento() {
		return this.tipo == Consts.TIPO_ANDAMENTO_PROCESSO;
	}

	@Transient
	@JsonIgnoreProperties
	public boolean documentoProcesso() {
		return this.tipo == Consts.TIPO_DOCUMENTO;
	}
}
