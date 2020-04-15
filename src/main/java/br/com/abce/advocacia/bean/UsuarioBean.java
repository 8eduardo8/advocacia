package br.com.abce.advocacia.bean;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@ApiModel
public class UsuarioBean extends UsuarioResumidoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String login;
	private String email;
	private String senha;

	private String sexo;
	private String sobrenome;

	private String telefoneFixo;
	private String telefoneCelular;

	private EnderecoBean enderecoBean = new EnderecoBean();

	private boolean recuperarSenha;
	private String senhaTemporaria;

	private boolean ativo;

	private Date dataCadastro;
	private Date dataAtualizacao;
	private Date dataExclusao;

	private List<String> listaProcessoId;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public boolean isRecuperarSenha() {
		return recuperarSenha;
	}

	public void setRecuperarSenha(boolean recuperarSenha) {
		this.recuperarSenha = recuperarSenha;
	}

	public String getSenhaTemporaria() {
		return senhaTemporaria;
	}

	public void setSenhaTemporaria(String senhaTemporaria) {
		this.senhaTemporaria = senhaTemporaria;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
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

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public List<String> getListaProcessoId() {
		return listaProcessoId;
	}

	public void setListaProcessoId(List<String> listaProcessoId) {
		this.listaProcessoId = listaProcessoId;
	}

	public String getTelefoneFixo() {
		return telefoneFixo;
	}

	public void setTelefoneFixo(String telefoneFixo) {
		this.telefoneFixo = telefoneFixo;
	}

	public String getTelefoneCelular() {
		return telefoneCelular;
	}

	public void setTelefoneCelular(String telefoneCelular) {
		this.telefoneCelular = telefoneCelular;
	}

	public EnderecoBean getEnderecoBean() {
		return enderecoBean;
	}

	public void setEnderecoBean(EnderecoBean enderecoBean) {
		this.enderecoBean = enderecoBean;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		UsuarioBean that = (UsuarioBean) o;
		return getPerfil() == that.getPerfil() &&
				isRecuperarSenha() == that.isRecuperarSenha() &&
				isAtivo() == that.isAtivo() &&
				Objects.equals(getId(), that.getId()) &&
				Objects.equals(getLogin(), that.getLogin()) &&
				Objects.equals(getNome(), that.getNome()) &&
				Objects.equals(getEmail(), that.getEmail()) &&
				Objects.equals(getSenha(), that.getSenha()) &&
				Objects.equals(getCpf(), that.getCpf()) &&
				Objects.equals(getSexo(), that.getSexo()) &&
				Objects.equals(getSobrenome(), that.getSobrenome()) &&
				Objects.equals(getTelefoneFixo(), that.getTelefoneFixo()) &&
				Objects.equals(getTelefoneCelular(), that.getTelefoneCelular()) &&
				Objects.equals(getSenhaTemporaria(), that.getSenhaTemporaria()) &&
				Objects.equals(getDataCadastro(), that.getDataCadastro()) &&
				Objects.equals(getDataAtualizacao(), that.getDataAtualizacao()) &&
				Objects.equals(getDataExclusao(), that.getDataExclusao()) &&
				Objects.equals(getListaProcessoId(), that.getListaProcessoId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getLogin(), getPerfil(), getNome(), getEmail(), getSenha(), getCpf(), getSexo(), getSobrenome(), getTelefoneFixo(), getTelefoneCelular(), getEnderecoBean(), isRecuperarSenha(), getSenhaTemporaria(), isAtivo(), getDataCadastro(), getDataAtualizacao(), getDataExclusao(), getListaProcessoId());
	}
}
