package br.com.abce.advocacia.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "escritorio", schema = "advocacia", catalog = "")
public class EscritorioEntity {
    private int id;
    private Date dataCadastro;
    private Date dataAtualizacao;
    private Date dataExclusao;
    private String cnpj;
    private String razaoSocial;
    private String nomeFantasia;
    private int situacao;
    private String telefoneFixo;
    private String telefoneCelular;
    private EnderecoEntity enderecoByEnderecoId;
    private Collection<UsuarioEscritorioEntity> usuarioEscritoriosById;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "data_cadastro", nullable = true)
    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    @Basic
    @Column(name = "data_atualizacao", nullable = true)
    public Date getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    @Basic
    @Column(name = "data_exclusao", nullable = true)
    public Date getDataExclusao() {
        return dataExclusao;
    }

    public void setDataExclusao(Date dataExclusao) {
        this.dataExclusao = dataExclusao;
    }

    @Basic
    @Column(name = "cnpj", nullable = false, length = 20)
    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    @Basic
    @Column(name = "razao_social", nullable = false, length = 100)
    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    @Basic
    @Column(name = "nome_fantasia", nullable = true, length = 100)
    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    @Basic
    @Column(name = "situacao", nullable = false)
    public int getSituacao() {
        return situacao;
    }

    public void setSituacao(int situacao) {
        this.situacao = situacao;
    }

    @Basic
    @Column(name = "telefone_fixo", nullable = true, length = 10)
    public String getTelefoneFixo() {
        return telefoneFixo;
    }

    public void setTelefoneFixo(String telefoneFixo) {
        this.telefoneFixo = telefoneFixo;
    }

    @Basic
    @Column(name = "telefone_celular", nullable = true, length = 11)
    public String getTelefoneCelular() {
        return telefoneCelular;
    }

    public void setTelefoneCelular(String telefoneCelular) {
        this.telefoneCelular = telefoneCelular;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EscritorioEntity that = (EscritorioEntity) o;
        return id == that.id &&
                situacao == that.situacao &&
                Objects.equals(dataCadastro, that.dataCadastro) &&
                Objects.equals(dataAtualizacao, that.dataAtualizacao) &&
                Objects.equals(dataExclusao, that.dataExclusao) &&
                Objects.equals(cnpj, that.cnpj) &&
                Objects.equals(razaoSocial, that.razaoSocial) &&
                Objects.equals(nomeFantasia, that.nomeFantasia) &&
                Objects.equals(telefoneFixo, that.telefoneFixo) &&
                Objects.equals(telefoneCelular, that.telefoneCelular);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dataCadastro, dataAtualizacao, dataExclusao, cnpj, razaoSocial, nomeFantasia, situacao, telefoneFixo, telefoneCelular);
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id", referencedColumnName = "id", nullable = false)
    public EnderecoEntity getEnderecoByEnderecoId() {
        return enderecoByEnderecoId;
    }

    public void setEnderecoByEnderecoId(EnderecoEntity enderecoByEnderecoId) {
        this.enderecoByEnderecoId = enderecoByEnderecoId;
    }

    @OneToMany(mappedBy = "escritorioByEscritorioId")
    public Collection<UsuarioEscritorioEntity> getUsuarioEscritoriosById() {
        return usuarioEscritoriosById;
    }

    public void setUsuarioEscritoriosById(Collection<UsuarioEscritorioEntity> usuarioEscritoriosById) {
        this.usuarioEscritoriosById = usuarioEscritoriosById;
    }
}
